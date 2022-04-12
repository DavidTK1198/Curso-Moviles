
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
    private static final AlumnoModel model=AlumnoModel.instance();;
    
    
      public static AlumnoController getInstance() {
        if (instance == null) instance = new AlumnoController();
        return instance;
    }

    private AlumnoController() {}
    
    public List<Alumno> todosLosAlumnos() throws GlobalException, NoDataException{
        return model.todosLosAlumnos();
    }
    }