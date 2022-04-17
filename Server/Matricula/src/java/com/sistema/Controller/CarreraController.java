package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Carrera;
import com.sistema.LogicaNegocio.CarreraModel;
import com.sistema.LogicaNegocio.Curso;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class CarreraController {

    private static CarreraController instance = null;
    private static final CarreraModel model =CarreraModel.instance();

    public static CarreraController getInstance() {
        if (instance == null) {
            instance = new CarreraController();
        }
        return instance;
    }

    private CarreraController() {
    }

    public List<Carrera> todasLasCarreras() throws GlobalException, NoDataException {

        model.todasLasCarreras();
        return model.getCarreras();
    }

    public Carrera carreraPorCodigo(String codigo) throws NoDataException, GlobalException {
        model.getCurrent().setCodigo(codigo);
        model.buscarporCodigo();
        return model.getCurrent();
    }

    public Carrera carreraPorNombre(String nombre) throws NoDataException, GlobalException {
        model.getCurrent().setNombre(nombre);
        model.buscarporNombre();
        return model.getCurrent();
    }
}
