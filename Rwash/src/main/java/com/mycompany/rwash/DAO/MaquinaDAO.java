/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rwash.DAO;

import static com.mycompany.rwash.DAO.UsuarioDAO.gerarHash;
import com.mycompany.rwash.Model.Maquina;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author pedro
 */

public class MaquinaDAO {

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
    
     public static boolean salvar(Maquina obj) {
    boolean retorno = false;
    Connection conexao = null;

    try {
        conexao = getConexao();
        if (conexao == null) {
            System.out.println("Falha ao obter conexão com o banco!");
            return false;
        }

        String sql = "INSERT INTO maquina (modeloMaquina, capacidadeMaquina, turbidezMaquina, Cliente_idCliente) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        
        stmt.setString(1, obj.getModeloMaquina());
         stmt.setString(2, obj.getCapacidadeMaquina());
        stmt.setString(3, obj.getTurbidezMaquina());
        stmt.setInt(4, obj.getCliente_idCliente());

        

        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas > 0) {
            retorno = true;
            System.out.println(" Cliente cadastrado com sucesso!");
        } else {
            System.out.println(" Nenhuma linha afetada — nada foi inserido!");
        }

        stmt.close();
    } catch (SQLException e) {
        System.out.println(" Erro SQL ao salvar cliente: " + e.getMessage());
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
}
