/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.LogicaNegocio;

/**
 *
 * @author USER
 */
public class Inscripcion {
    private Alumno estudiante;
    private String nota;
    private Grupo grupo;
    private int idEntidad;
    
    public Inscripcion(Alumno estudiante,String nota, Grupo grupo){
        this.estudiante = estudiante;
        this.nota = nota;
        this.grupo = grupo;
    }
    
    public Inscripcion(){
        estudiante=new Alumno();
        nota = "";
        grupo = new Grupo();
        idEntidad=0;
    }

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }
    
    public Alumno getEstudiante(){
        return estudiante;
    }
    
    public void setEstudiante(Alumno estudiante){
        this.estudiante = estudiante;
    }
    
    public String getNota(){
        return nota;
    }
    
    public void setNota(String nota){
        this.nota = nota;
    }
    
    public Grupo getGrupo(){
        return grupo;
    }
    
    public void setGrupo(Grupo grupo){
        this.grupo = grupo;
    }
}
