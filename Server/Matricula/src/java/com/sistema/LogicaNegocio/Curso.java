package com.sistema.LogicaNegocio;
import java.util.ArrayList;
/**
 *
 * @author DavidTK1198
 */
public class Curso {
    private String nombre;
    private String tematica; //prueba de estebitan uwu
    private float costo;
    private String estado; //true = oferta false = no en oferta
    private ArrayList<Grupo> grupos; //numero de grupos que tiene el curso

    public Curso(String nombre, String tematica, float costo, String estado) {
        this.nombre = nombre;
        this.tematica = tematica;
        this.costo = costo;
        this.estado = estado;
        this.grupos = new ArrayList();
    }

    public Curso() {
        this.nombre = "";
        this.tematica = "";
        this.costo = 0.00f;
        this.estado = "";
        this.grupos = new ArrayList();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarGrupo(Grupo grupo){
        grupos.add(grupo);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public String getEstadoString(){
        return (this.estado.contains("1")) ? "en Oferta" : "sin Oferta";
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList getGrupos(){
        return grupos;
    }

    public void setGrupos(ArrayList<Grupo> grupos){
        this.grupos = grupos;
    }
}
