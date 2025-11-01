package com.mycompany.rwash.DAO;

import com.mycompany.rwash.Model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;

public class UsuarioDAO {

    // Dados de conexão com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/r-wash";
    private static final String LOGIN = "root";
    private static final String SENHA = "7777";

    /**
     * Retorna uma conexão válida com o banco.
     */
    private static Connection getConexao() {
        Connection conexao = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(URL, LOGIN, SENHA);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
            e.printStackTrace();
        }
        return conexao;
    }

    /**
     * Gera hash SHA-256 para a senha.
     */
    public static String gerarHash(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(senha.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Autentica um usuário pelo email e senha.
     * @param email O email do cliente
     * @param senha A senha do cliente
     * @return idCliente se login OK, ou 0 se falhar
     */
    public static int autenticar(String email, String senha) {
        int idCliente = 0;
        try {
            Connection con = getConexao();
            String senhaHash = gerarHash(senha); // hash da senha
            String sql = "SELECT idCliente FROM cliente WHERE emailCliente = ? AND senhaCliente = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senhaHash);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                idCliente = rs.getInt("idCliente");
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idCliente;
    }

    /**
     * Salva um novo cliente no banco.
     * @param obj Objeto Usuario a ser salvo
     * @return true se inserido, false se falhar
     */
    public static boolean salvar(Usuario obj) {
        boolean retorno = false;
        Connection conexao = null;

        try {
            conexao = getConexao();
            String sql = "INSERT INTO cliente(nomeCliente, emailCliente, senhaCliente) VALUES (?, ?, ?)";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, obj.getNomeCliente());
            stmt.setString(2, obj.getEmailCliente());
            stmt.setString(3, gerarHash(obj.getSenhaCliente())); // salva o hash da senha

            int linhasAfetadas = stmt.executeUpdate();
            retorno = linhasAfetadas > 0;

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erro ao executar SQL: " + e.getMessage());
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

    /**
     * Busca um cliente pelo ID.
     * @param idCliente ID do cliente
     * @return Objeto Usuario ou null se não encontrado
     */
    public static Usuario buscarPorId(int idCliente) {
        Usuario usuario = null;
        try {
            Connection con = getConexao();
            String sql = "SELECT * FROM cliente WHERE idCliente = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdCliente(rs.getInt("idCliente"));
                usuario.setNomeCliente(rs.getString("nomeCliente"));
                usuario.setEmailCliente(rs.getString("emailCliente"));
                usuario.setSenhaCliente(rs.getString("senhaCliente")); // hash armazenado
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
