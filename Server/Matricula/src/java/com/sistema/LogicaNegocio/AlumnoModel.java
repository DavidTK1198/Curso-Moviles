package com.sistema.LogicaNegocio;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioAlumno;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class AlumnoModel {

    private static AlumnoModel uniqueInstance;
    private Alumno current;
    private final ServicioAlumno alumno_DBA;
    private List<Alumno> alumnos;

    public static AlumnoModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AlumnoModel();

        }
        return uniqueInstance;
    }

    private AlumnoModel() {
        this.current = new Alumno();
        this.alumnos = new ArrayList<>();
        this.alumno_DBA = ServicioAlumno.getInstance();
    }

    public Alumno getCurrent() {
        return current;
    }

    public void setCurrent(Alumno current) {
        this.current = current;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public void todosLosAlumnos() throws GlobalException, NoDataException {
        alumnos = (List<Alumno>) alumno_DBA.listarAlumno("todos","");
    }

    public void buscarporCedula() throws GlobalException, NoDataException {
        current = alumno_DBA.buscarAlumno(current.getCedula(), "cedula");
    }

    public void buscarporNombre() throws GlobalException, NoDataException {
        current = alumno_DBA.buscarAlumno(current.getNombre(), "nombre");
    }

    public void buscarporCarrera() throws GlobalException, NoDataException {
        alumnos = (List<Alumno>) alumno_DBA.listarAlumno("carrera", current.getCarrera().getCodigo());
    }
}
