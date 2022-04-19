package com.sistema.LogicaNegocio;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author DavidTK1198
 */
public class Grupo {
    private int numero;
    private Ciclo ciclo;
    private String horario;
    private List<Alumno> estudiantes;
    private Profesor profesor;
    private Curso curso;
    private int idEntidad;

    public int getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(int idEntidad) {
        this.idEntidad = idEntidad;
    }
    private int cupo;
    private int disponible;

    public Grupo(int cupo,int disponible,int numero,String horario,Ciclo ciclo, Profesor profesor, Curso curso) {
        this.numero = numero;////
        this.horario = horario;
        this.profesor = profesor;
        this.curso = curso;
        estudiantes = new ArrayList();
        this.ciclo=ciclo;
        this.cupo=cupo;
        this.disponible=disponible;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public Grupo() {
        numero = 0;
        horario = "";
        estudiantes = new ArrayList();
        profesor = new Profesor();
        curso = new Curso();
        ciclo=new Ciclo();
        this.cupo=0;
        this.disponible=0;
        this.idEntidad=0;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public List getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Alumno> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

}
