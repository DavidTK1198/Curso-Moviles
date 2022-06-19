package com.sistema.LogicaNegocio;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioProfesor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class ProfesorModel {

    private static ProfesorModel uniqueInstance;
    private Profesor current;
    private final ServicioProfesor profesor_DBA;
    private List<Profesor> profesores;

    public static ProfesorModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ProfesorModel();

        }
        return uniqueInstance;
    }

    private ProfesorModel() {
        this.current = new Profesor();
        this.profesores = new ArrayList<>();
        this.profesor_DBA = ServicioProfesor.getInstance();
    }

    public Profesor getCurrent() {
        return current;
    }

    public void setCurrent(Profesor current) {
        this.current = current;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }

    public void todosLosProfesors() throws GlobalException, NoDataException {
        profesores = (List<Profesor>) profesor_DBA.listarProfesor();
    }

    public void buscarporCedula() throws GlobalException, NoDataException {
        current = profesor_DBA.buscarProfesor(current.getCedula(), "cedula");
    }

    public void buscarporNombre() throws GlobalException, NoDataException {
        current = profesor_DBA.buscarProfesor(current.getNombre(), "nombre");
    }
   public void insertarProfesor(Profesor p) throws GlobalException, NoDataException {
       profesor_DBA.insertarProfesor(current);
    }

    public void modificarProfesor(Profesor p) throws GlobalException, NoDataException {
       profesor_DBA.modificarProfesor(current);
    }
    
    public void eliminarProfesor() throws GlobalException, NoDataException{
        profesor_DBA.eliminarProfesors(current.getCedula());
    }
}
