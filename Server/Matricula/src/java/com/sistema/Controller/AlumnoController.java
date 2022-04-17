package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Alumno;
import com.sistema.LogicaNegocio.AlumnoModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class AlumnoController {

    private static AlumnoController instance = null;
    private static final AlumnoModel model = AlumnoModel.instance();

    public static AlumnoController getInstance() {
        if (instance == null) {
            instance = new AlumnoController();
        }
        return instance;
    }

    private AlumnoController() {
    }

    public List<Alumno> todosLosAlumnos() throws GlobalException, NoDataException {
        model.todosLosAlumnos();
        return model.getAlumnos();
    }

    public Alumno alumnoPorCedula(String codigo) throws NoDataException, GlobalException {
        model.getCurrent().setCédula(codigo);
        model.buscarporCedula();
        return model.getCurrent();
    }

    public Alumno alumnoPorNombre(String nombre) throws NoDataException, GlobalException {
        model.getCurrent().setNombre(nombre);
        model.buscarporNombre();
        return model.getCurrent();
    }
       public List<Alumno> AlumnosporCarrera(String codigo) throws GlobalException, NoDataException {
        model.getCurrent().getCarrera().setCodigo(codigo);
        model.buscarporCarrera();
        return model.getAlumnos();
    }
}
