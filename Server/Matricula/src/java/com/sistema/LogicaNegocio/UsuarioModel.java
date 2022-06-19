package com.sistema.LogicaNegocio;
import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioLogueo;
import com.sistema.AccesoDatos.ServicioUsuario;
import java.util.List;


public class UsuarioModel {

    private static UsuarioModel uniqueInstance;
    private final ServicioLogueo login;
        private final ServicioUsuario usuario_dba;
    private Usuario usuario;
        private List<Usuario> usuarios;
    
    public static UsuarioModel instance(){
        if (uniqueInstance == null){
            uniqueInstance = new UsuarioModel();
            
        }
        return uniqueInstance;
    }
    private UsuarioModel() {
        this.login = ServicioLogueo.getInstance();
        this.usuario_dba=ServicioUsuario.getInstance();
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
    
    public List<Usuario> listarUsuarios() throws GlobalException, NoDataException{
        usuarios=(List<Usuario>) usuario_dba.listarUsuarios();
        return this.usuarios;
    }

    public void actualizarUsuario() throws GlobalException, NoDataException {
        this.usuario_dba.actualizarUsuario(usuario);
    }
    
    
    
}
