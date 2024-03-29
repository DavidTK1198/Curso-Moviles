package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Alumno;
import com.sistema.LogicaNegocio.AlumnoModel;
import com.sistema.LogicaNegocio.Carrera;
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
        model.getCurrent().setCedula(codigo);
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

    public void agregar(Alumno p) throws GlobalException, NoDataException {
       model.setCurrent(p);
       model.agregar();
    }

    public void eliminar(String id) throws GlobalException, NoDataException {
        model.setCurrent(new Alumno(id,"","","","",new Carrera()));
        model.eliminar();
    }

    public void actualizar(Alumno p) throws GlobalException, NoDataException {
       model.setCurrent(p);
       model.actualizar();
    }
}
