package com.mycompany.rwash.DAO;

import com.mycompany.rwash.Model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    static String URL = "jdbc:mysql://localhost:3306/r-wash";
    static String login = "root";
    static String senha = "7777";

    public static boolean salvar(Usuario obj) {
        Connection conexao = null;
        boolean retorno = false;

        try {
            // 1) carregar o driver mysql
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2) fazer a conexao com o banco
            conexao = DriverManager.getConnection(URL, login, senha);

            // 3) Prepara o comando sql
            PreparedStatement instrucaoSQL = conexao.prepareStatement(
                "INSERT INTO cliente(nomeCliente,emailCliente,senhaCliente) VALUES (?,?,?)"
            );

            instrucaoSQL.setString(1, obj.getNomeCliente());
            instrucaoSQL.setString(2, obj.getEmailCliente());
            instrucaoSQL.setString(3, obj.getSenhaCliente());

            // 4) Executar o comando
            int linhasAfetadas = instrucaoSQL.executeUpdate();
            retorno = linhasAfetadas > 0;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao executar SQL: " + e.getMessage());
            e.printStackTrace(); // Mostra o stack completo
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
}
