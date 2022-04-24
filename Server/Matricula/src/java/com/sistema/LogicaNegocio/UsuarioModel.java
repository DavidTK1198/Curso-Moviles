package com.sistema.LogicaNegocio;
import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioLogueo;
import java.sql.SQLException;


public class UsuarioModel {

    private static UsuarioModel uniqueInstance;
    private final ServicioLogueo login;
    private Usuario usuario;
    
    public static UsuarioModel instance(){
        if (uniqueInstance == null){
            uniqueInstance = new UsuarioModel();
            
        }
        return uniqueInstance;
    }
    private UsuarioModel() {
        this.login = ServicioLogueo.getInstance();
        this.usuario=new Usuario();
    }
    
      public void getAuthorization(Usuario us)throws NoDataException, GlobalException {
        usuario= login.loginCliente(us.getNombre(), us.getClave());
    }
    
    public Usuario getUs(){
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
}
