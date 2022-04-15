package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Curso;
import com.sistema.LogicaNegocio.CursoModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class CursoController {

    private static CursoController instance = null;
    private static final CursoModel model = CursoModel.instance();

    public static CursoController getInstance() {
        if (instance == null) {
            instance = new CursoController();
        }
        return instance;
    }

    private CursoController() {
    }

    public List<Curso> todosLosCursos() throws GlobalException, NoDataException {

        model.todosLosCursos();
        return model.getCursos();
    }

    public List<Curso> cursoporCarrera(String codigo) throws GlobalException, NoDataException {
        model.getCurrent().getCarrera().setCodigo(codigo);
        model.buscarporCarrera();
        return model.getCursos();
    }

    public Curso cursoPorCodigo(String codigo) throws NoDataException, GlobalException {
        model.getCurrent().setCodigo(codigo);
        model.buscarporCodigo();
        return model.getCurrent();
    }

    public Curso cursoPorNombre(String nombre) throws NoDataException, GlobalException {
        model.getCurrent().setNombre(nombre);
        model.buscarporNombre();
        return model.getCurrent();
    }
}
