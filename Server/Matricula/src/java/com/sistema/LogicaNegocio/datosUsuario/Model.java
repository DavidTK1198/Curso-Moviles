package com.sistema.LogicaNegocio.datosUsuario;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioLogueo;
import com.sistema.LogicaNegocio.Usuario;


public class Model {

    private static Model uniqueInstance;
    private final ServicioLogueo login;
    private Usuario current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    private Model() {
        this.login = ServicioLogueo.getInstance();
        this.current = new Usuario();
    }

    public boolean getAuthorization(Usuario us) throws NoDataException, GlobalException {
        return login.loginCliente(us.getNombre(), us.getClave());
    }

    public Usuario getUsuario() {
        return current;
    }

    public void setUsuario(Usuario current) {
        this.current = current;
    }


}
