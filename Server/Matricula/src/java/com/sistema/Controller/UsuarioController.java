package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Usuario;
import com.sistema.LogicaNegocio.UsuarioModel;

/**
 *
 * @author DavidTK1198
 */
public class UsuarioController {

    private static UsuarioController instance = null;
    private static final UsuarioModel model=null;
    
    
      public static UsuarioController getInstance() {
        if (instance == null) instance = new UsuarioController();
        return instance;
    }

    private UsuarioController() {
        UsuarioModel model = UsuarioModel.instance();
    }
      
      public boolean Login(String user, String password) throws NoDataException, GlobalException{
          model.setUsuario(new Usuario());
          model.getUs().setClave(password);
          model.getUs().setNombre(user);
          return model.getAuthorization(model.getUs());
      }
}
