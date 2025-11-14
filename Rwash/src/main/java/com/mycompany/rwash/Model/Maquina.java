package com.mycompany.rwash.Model;

public class Maquina {

    private int idMaquina;
    private String modeloMaquina;
    private String turbidezMaquina;
    private String capacidadeMaquina;
    private int Cliente_idCliente; // mantém o nome original

    public Maquina() {}

    public Maquina(String modeloMaquina, String turbidezMaquina, String capacidadeMaquina, int Cliente_idCliente) {
        this.modeloMaquina = modeloMaquina;
        this.turbidezMaquina = turbidezMaquina;
        this.capacidadeMaquina = capacidadeMaquina;
        this.Cliente_idCliente = Cliente_idCliente;
    }

    public Maquina(int idMaquina, String modeloMaquina, String turbidezMaquina, String capacidadeMaquina, int Cliente_idCliente) {
        this.idMaquina = idMaquina;
        this.modeloMaquina = modeloMaquina;
        this.turbidezMaquina = turbidezMaquina;
        this.capacidadeMaquina = capacidadeMaquina;
        this.Cliente_idCliente = Cliente_idCliente;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getModeloMaquina() {
        return modeloMaquina;
    }

    public void setModeloMaquina(String modeloMaquina) {
        this.modeloMaquina = modeloMaquina;
    }

    public String getTurbidezMaquina() {
        return turbidezMaquina;
    }

    public void setTurbidezMaquina(String turbidezMaquina) {
        this.turbidezMaquina = turbidezMaquina;
    }

    public String getCapacidadeMaquina() {
        return capacidadeMaquina;
    }

    public void setCapacidadeMaquina(String capacidadeMaquina) {
        this.capacidadeMaquina = capacidadeMaquina;
    }

    // Getter e Setter corretos para Cliente_idCliente
    public int getCliente_idCliente() {
        return Cliente_idCliente;
    }

    public void setCliente_idCliente(int Cliente_idCliente) {
        this.Cliente_idCliente = Cliente_idCliente;
    }
}
