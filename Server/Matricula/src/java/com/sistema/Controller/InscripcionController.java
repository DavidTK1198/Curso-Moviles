package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Inscripcion;
import com.sistema.LogicaNegocio.InscripcionModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class InscripcionController {

    private static InscripcionController instance = null;
    private static final InscripcionModel model =InscripcionModel.instance();

    public static InscripcionController getInstance() {
        if (instance == null) {
            instance = new InscripcionController();
        }
        return instance;
    }

    private InscripcionController() {
    }

    public List<Inscripcion> todosLosInscripcions() throws GlobalException, NoDataException {

        model.todasLasInscripciones();
        return model.getInscripciones();
    }


}
