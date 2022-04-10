package com.sistema.LogicaNegocio;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author DavidTK1198
 */
public class Grupo {
    private int numero;
    private int cupo;
    private String horario;
    private List<Inscripcion> estudiantes;
    private Profesor profesor;
    private Curso curso;

    public Grupo(int cupo, String horario, Profesor profesor, Curso curso) {
        this.numero = 0;////
        this.cupo = cupo;
        this.horario = horario;
        this.profesor = profesor;
        this.curso = curso;
        estudiantes = new ArrayList();

    }

    public Grupo() {
        numero = 0;
        cupo = 0;
        horario = "";
        estudiantes = new ArrayList();
        profesor = new Profesor();
        curso = new Curso();
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

    public void setEstudiantes(List<Inscripcion> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public String getEspacio() {
        return Integer.toString(cupo - estudiantes.size());
    }
}
