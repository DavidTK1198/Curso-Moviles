package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Ciclo;
import com.sistema.LogicaNegocio.CicloModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class CicloController {

    private static CicloController instance = null;
    private static final CicloModel model=CicloModel.instance();;
    
    
      public static CicloController getInstance() {
        if (instance == null) instance = new CicloController();
        return instance;
    }

    private CicloController() {}
    
    public List<Ciclo> todosLosCiclos() throws GlobalException, NoDataException{
        return model.todosLosCiclos();
    }
    }