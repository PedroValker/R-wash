package com.mycompany.rwash.Model;

public class Endereco {

    private int idEndereco;           // nome corrigido
    private String CEP;
    private String bairro;
    private String rua;
    private int cliente_idCliente;

    // Construtor vazio (necessário para uso com DAO)
    public Endereco() {
    }

    // Construtor completo
    public Endereco(int idEndereco, String CEP, String bairro, String rua, int cliente_idCliente) {
        this.idEndereco = idEndereco;
        this.CEP = CEP;
        this.bairro = bairro;
        this.rua = rua;
        this.cliente_idCliente = cliente_idCliente;
    }

    // Construtor sem ID (para INSERT)
    public Endereco(String CEP, String bairro, String rua, int cliente_idCliente) {
        this.CEP = CEP;
        this.bairro = bairro;
        this.rua = rua;
        this.cliente_idCliente = cliente_idCliente;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getCliente_idCliente() {
        return cliente_idCliente;
    }

    public void setCliente_idCliente(int cliente_idCliente) {
        this.cliente_idCliente = cliente_idCliente;
    }
}
