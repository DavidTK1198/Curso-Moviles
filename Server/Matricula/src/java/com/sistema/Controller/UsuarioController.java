package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Usuario;
import com.sistema.LogicaNegocio.UsuarioModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class UsuarioController {

    private static UsuarioController instance = null;
    private static final UsuarioModel model = UsuarioModel.instance();

    public static UsuarioController getInstance() {
        if (instance == null) {
            instance = new UsuarioController();
        }
        return instance;
    }

    private UsuarioController() {
    }

    public Usuario Login(String user, String password) throws NoDataException, GlobalException {
        model.setUsuario(new Usuario());
        model.getUs().setClave(password);
        model.getUs().setNombre(user);
        model.getAuthorization(model.getUs());
        return model.getUs();
    }

    public List<Usuario> obtenerUsuarios() throws GlobalException, NoDataException {
       return model.listarUsuarios();
    }

    public void actualizarUsuario(Usuario p) throws GlobalException, NoDataException {
       model.setUsuario(p);
       model.actualizarUsuario();
    }
}
