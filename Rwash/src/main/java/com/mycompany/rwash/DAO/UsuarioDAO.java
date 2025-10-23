/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rwash.DAO;

import com.mycompany.rwash.Model.Usuario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author pedro
 */
public class UsuarioDAO {
     static String URL = "jdbc:mysql://localhost:3306/Rwash";
     static String login = "root";
     static String senha = "7777";
     
      public static boolean salvar(Usuario obj){
        Connection conexao = null;
    
        boolean retorno = false; 
        
        try {
           //1) carregar o driver mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2)fazer a conexao com o banco
            conexao = DriverManager.getConnection(URL,login,senha);
            //3)Prepara o comando sql
            PreparedStatement instrucaoSQL = conexao.prepareStatement(
            "INSERT INTO cliente(nomeCliente,emailCliente,cpfCliente) values(?,?,?)"
            );
            
            instrucaoSQL.setString(1, obj.getNomeCliente());
            instrucaoSQL.setString(2, obj.getEmailCliente());
            instrucaoSQL.setString(3, obj.getcpfCliente());
           
       //4)executar o comando
      int linhasAfetadas = instrucaoSQL.executeUpdate();
        if(linhasAfetadas>0){
            retorno =true;
        }

        }catch(ClassNotFoundException e){            
            System.out.println("Driver não encontrado"); 
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
        } finally {
         if(conexao!=null){
             try {
                 conexao.close();
             } catch (SQLException ex) {
                 System.getLogger(UsuarioDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
             }
         }
        }
        
        return retorno;   
    }

    
}
