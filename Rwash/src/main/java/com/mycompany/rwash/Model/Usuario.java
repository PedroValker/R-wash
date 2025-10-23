/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rwash.Model;

/**
 *
 * @author pedro
 */
public class Usuario {
    private int idCliente;
    private String nomeCliente;
    private String emailCliente;
    private int cpfCliente;
    private int senhaCliente;
    public Object getIdCliente;


    public Usuario(int idCliente, String nomeCliente, String emailCliente, int cpfCliente, int senhaCliente) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.cpfCliente = cpfCliente;
        this.senhaCliente = senhaCliente;
    }

    public Usuario(String nomeCliente, String emailCliente, int cpfCliente, int senhaCliente) {
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.cpfCliente = cpfCliente;
        this.senhaCliente = senhaCliente;
    }

    public Usuario() {
    }

    
    
    public int getIdCliente() {
        return idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public int getCpfCliente() {
        return cpfCliente;
    }

      public int getsenhaCliente() {
        return senhaCliente;
    }

    
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public void setCpfCliente(int cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

     public void setsenhaCliente(int senhaCliente) {
        this.senhaCliente = senhaCliente;
    }
     
    public String getcpfCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
