package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Profesor;
import com.sistema.LogicaNegocio.ProfesorModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class ProfesorController {

    private static ProfesorController instance = null;
    private static final ProfesorModel model = ProfesorModel.instance();

    public static ProfesorController getInstance() {
        if (instance == null) {
            instance = new ProfesorController();
        }
        return instance;
    }

    private ProfesorController() {
    }

    public List<Profesor> todosLosProfesors() throws GlobalException, NoDataException {
        model.todosLosProfesors();
        return model.getProfesores();
    }

    public Profesor profesorPorCedula(String codigo) throws NoDataException, GlobalException {
        model.getCurrent().setCedula(codigo);
        model.buscarporCedula();
        return model.getCurrent();
    }

    public Profesor profesorPorNombre(String nombre) throws NoDataException, GlobalException {
        model.getCurrent().setNombre(nombre);
        model.buscarporNombre();
        return model.getCurrent();
    }
    
        public void agregarProfesor(Profesor p) throws GlobalException, NoDataException {
        model.setCurrent(p);
        model.insertarProfesor(p);
    }

    public void modificarProfesor(Profesor p)  throws GlobalException, NoDataException{
         model.setCurrent(p);
        model.modificarProfesor(p);
    }

    public void eliminarProfesor(String p) throws GlobalException, NoDataException {
       model.setCurrent(new Profesor(p,"","",""));
       model.eliminarProfesor();
    }
}
