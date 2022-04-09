package com.sistema.LogicaNegocio;

public class Inscripcion {
    private Alumno alumno;
    private String nota;
    private Grupo grupo;

    public Inscripcion(Alumno alumno, String nota, Grupo grupo) {
        this.alumno = alumno;
        this.nota = nota;
        this.grupo = grupo;
    }

    public Inscripcion() {
        alumno = new Alumno();
        nota = "";
        grupo = new Grupo();
    }

    public Alumno getEstudiante() {
        return alumno;
    }

    public void setAlumno(Alumno estudiante) {
        this.alumno = alumno;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
