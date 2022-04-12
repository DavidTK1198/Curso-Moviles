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
    private static final CursoModel model=CursoModel.instance();;
    
    
      public static CursoController getInstance() {
        if (instance == null) instance = new CursoController();
        return instance;
    }

    private CursoController() {}
    
    public List<Curso> todosLosCursos() throws GlobalException, NoDataException{
        return model.todosLosCursos();
    }
    }