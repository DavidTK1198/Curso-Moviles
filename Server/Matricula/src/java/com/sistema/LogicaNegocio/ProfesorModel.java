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
    private List<Profesor> alumnos;

    public static ProfesorModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ProfesorModel();

        }
        return uniqueInstance;
    }

    public ProfesorModel() {
        this.current = new Profesor();
        this.alumnos = new ArrayList<>();
        this.profesor_DBA = ServicioProfesor.getInstance();
    }

    public Profesor getCurrent() {
        return current;
    }

    public void setCurrent(Profesor current) {
        this.current = current;
    }
    
    public List<Profesor> todosLosProfesors() throws GlobalException, NoDataException{
        return (List<Profesor>) profesor_DBA.listarProfesor();
    }
}
