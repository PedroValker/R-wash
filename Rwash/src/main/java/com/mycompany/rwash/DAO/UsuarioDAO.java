package com.mycompany.rwash.DAO;

import com.mycompany.rwash.Model.Usuario;
import java.sql.*;
import java.security.MessageDigest;

public class UsuarioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/r-wash";
    private static final String LOGIN = "root";
    private static final String SENHA = "7777";

    public static int idClienteLogado = 0;

    // ============================================
    //  CONEXÃO
    // ============================================
    private static Connection getConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, LOGIN, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // ============================================
    //  HASH SHA-256
    // ============================================
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

    // ============================================
    //  LOGIN
    // ============================================
    public static int autenticar(String email, String senha) {

        String sql = "SELECT idCliente FROM cliente WHERE emailCliente = ? AND senhaCliente = ?";
        int idCliente = 0;

        try (Connection con = getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, gerarHash(senha));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                idCliente = rs.getInt("idCliente");
                idClienteLogado = idCliente;
                System.out.println("Cliente logado com sucesso! ID = " + idCliente);
            } else {
                idClienteLogado = 0;
                System.out.println("Falha no login: email/senha incorretos.");
            }

        } catch (SQLException e) {
            System.out.println("Erro SQL no login: " + e.getMessage());
            e.printStackTrace();
        }

        return idCliente;
    }

    // ============================================
    //  CADASTRAR CLIENTE (AGORA SALVA CPF TAMBÉM)
    // ============================================
    public static boolean salvar(Usuario obj) {
        boolean retorno = false;
        Connection conexao = null;

        try {
            conexao = getConexao();
            if (conexao == null) {
                System.out.println("❌ Falha ao conectar ao banco!");
                return false;
            }

            String sql = "INSERT INTO cliente (nomeCliente, emailCliente, cpfCliente, senhaCliente) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, obj.getNomeCliente());
            stmt.setString(2, obj.getEmailCliente());
            stmt.setString(3, obj.getCpfCliente()); 
            stmt.setString(4, gerarHash(obj.getSenhaCliente())); 

            int linhas = stmt.executeUpdate();
            retorno = linhas > 0;

            if (retorno)
                System.out.println("✅ Cliente cadastrado no banco!");
            else
                System.out.println("⚠️ Nenhuma linha inserida.");

            stmt.close();

        } catch (SQLException e) {
            System.out.println("❌ Erro SQL ao inserir cliente: " + e.getMessage());
        } finally {
            try {
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return retorno;
    }

    // ============================================
    //  BUSCAR POR ID
    // ============================================
    public static Usuario buscarPorId(int idCliente) {

        Usuario usuario = null;
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";

        try (Connection con = getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("idCliente"),
                    rs.getString("nomeCliente"),
                    rs.getString("emailCliente"),
                    rs.getString("cpfCliente"),
                    rs.getString("senhaCliente")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // ============================================
    //  VERIFICA SE CLIENTE JÁ COMPROU
    // ============================================
    public static boolean clienteJaComprou(int idCliente) {

        String sql = "SELECT statusCompra FROM cliente WHERE idCliente = ?";
        boolean comprou = false;

        try (Connection con = getConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                comprou = rs.getBoolean("statusCompra");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar compra: " + e.getMessage());
        }

        return comprou;
    }

}
