package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class Ciclo {

    private int annio;
    private int numero;
    private int id;
    private String fec_inicio;
    private String fec_final;
    private int estado;

    public Ciclo(int id,int annio, int numero, int estado,String fec_inicio, String fec_final) {
        this.annio = annio;
        this.numero = numero;
        this.id = id;
        this.fec_inicio = fec_inicio;
        this.fec_final = fec_final;
        this.estado=estado;
    }
    
    public Ciclo() {
        this.annio = 0;
        this.numero = 0;
        this.id = 0;
        this.fec_inicio = "";
        this.fec_final = "";
        this.estado = 0;
    }
    
    public int isEstado() {
        return  estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
    public int getAnnio() {
        return annio;
    }

    public void setAnnio(int annio) {
        this.annio = annio;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFec_inicio() {
        return fec_inicio;
    }

    public void setFec_inicio(String fec_inicio) {
        this.fec_inicio = fec_inicio;
    }

    public String getFec_final() {
        return fec_final;
    }

    public void setFec_final(String fec_final) {
        this.fec_final = fec_final;
    }

}
