package com.mycompany.rwash.Model;

public class Usuario {
    private int idCliente;
    private String nomeCliente;
    private String emailCliente;
    private String cpfCliente;
    private String senhaCliente;
    private boolean statusCompra; // true = já comprou, false = não comprou

    // Construtores
    public Usuario() {
        
        
    }

    public void setModeloMaquina(String modelo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setTurbidezMaquina(String eficiencia) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void getCapacidadeMaquina(String capacidade) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
public class Sessao {
        public static int idClienteLogado;
    }

    
    public Usuario(String nomeCliente, String emailCliente, String senhaCliente) {
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.senhaCliente = senhaCliente;
    }

    public Usuario(int idCliente, String nomeCliente, String emailCliente, String cpfCliente, String senhaCliente) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.cpfCliente = cpfCliente;
        this.senhaCliente = senhaCliente;
    }

    public Usuario(String nomeCliente, String emailCliente, String cpfCliente, String senhaCliente) {
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.cpfCliente = cpfCliente;
        this.senhaCliente = senhaCliente;
    }

    public Usuario(String emailCliente, String senhaCliente) {
        this.emailCliente = emailCliente;
        this.senhaCliente = senhaCliente;
    }

    // Getters e Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }
    
  

    public boolean isStatusCompra() {
        return statusCompra;
    }

    public void setStatusCompra(boolean statusCompra) {
        this.statusCompra = statusCompra;
    }

    
}
