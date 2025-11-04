package com.mycompany.rwash.DAO;

import com.mycompany.rwash.Model.Usuario;
import java.sql.*;
import java.security.MessageDigest;

public class UsuarioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/r-wash";
    private static final String LOGIN = "root";
    private static final String SENHA = "7777";

    private static Connection getConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, LOGIN, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(senha.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --- LOGIN ---
    public static int autenticar(String email, String senha) {
        int idCliente = 0;
        String sql = "SELECT idCliente FROM cliente WHERE emailCliente = ? AND senhaCliente = ?";
        try (Connection con = getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, gerarHash(senha));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) idCliente = rs.getInt("idCliente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCliente;
    }

    // --- CADASTRO ---
  public static boolean salvar(Usuario obj) {
    boolean retorno = false;
    Connection conexao = null;

    try {
        conexao = getConexao();
        if (conexao == null) {
            System.out.println("❌ Falha ao obter conexão com o banco!");
            return false;
        }

        String sql = "INSERT INTO cliente (nomeCliente, emailCliente, senhaCliente) VALUES (?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setString(1, obj.getNomeCliente());
        stmt.setString(2, obj.getEmailCliente());
        stmt.setString(3, gerarHash(obj.getSenhaCliente()));

        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas > 0) {
            retorno = true;
            System.out.println("✅ Cliente cadastrado com sucesso!");
        } else {
            System.out.println("⚠️ Nenhuma linha afetada — nada foi inserido!");
        }

        stmt.close();
    } catch (SQLException e) {
        System.out.println("❌ Erro SQL ao salvar cliente: " + e.getMessage());
        e.printStackTrace();
    } finally {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar conexão: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    return retorno;
}


    // --- BUSCA POR ID ---
    public static Usuario buscarPorId(int idCliente) {
        Usuario usuario = null;
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        try (Connection con = getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdCliente(rs.getInt("idCliente"));
                usuario.setNomeCliente(rs.getString("nomeCliente"));
                usuario.setEmailCliente(rs.getString("emailCliente"));
                usuario.setSenhaCliente(rs.getString("senhaCliente"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // --- VERIFICAÇÃO DE COMPRA ---
    public static boolean clienteJaComprou(int idCliente) {
        boolean comprou = false;
        String sql = "SELECT COUNT(*) FROM compra WHERE idCliente = ? AND statusCompra = TRUE"; 
        // ⚠️ verifique se o nome da tabela é 'compra' (singular) e o campo é 'status_compra'

        try (Connection con = getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) comprou = rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comprou;
    }
}
