package com.sistema.LogicaNegocio.datosUsuario;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Usuario;

/**
 *
 * @author DavidTK1198
 */
public class Controller {

    private static Controller instance = null;
    private static final Model model=null;
    
    
      public static Controller getInstance() {
        if (instance == null) instance = new Controller();
        return instance;
    }

    private Controller() {
        Model model = Model.instance();
    }
      
      public boolean Login(String user, String password) throws NoDataException, GlobalException{
          model.setUsuario(new Usuario());
          model.getUsuario().setClave(password);
          model.getUsuario().setNombre(user);
          return model.getAuthorization(model.getUsuario());
      }
}
