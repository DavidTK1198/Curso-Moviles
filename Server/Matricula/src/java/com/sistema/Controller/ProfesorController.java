
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
    private static final ProfesorModel model=ProfesorModel.instance();;
    
    
      public static ProfesorController getInstance() {
        if (instance == null) instance = new ProfesorController();
        return instance;
    }

    private ProfesorController() {}
    
    public List<Profesor> todosLosProfesors() throws GlobalException, NoDataException{
        return model.todosLosProfesors();
    }
    }