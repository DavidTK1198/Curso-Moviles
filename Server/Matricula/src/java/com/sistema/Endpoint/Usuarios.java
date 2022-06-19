
package com.sistema.Endpoint;

import com.sistema.LogicaNegocio.Usuario;
import javax.annotation.security.PermitAll;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.sistema.Controller.UsuarioController;
import java.util.List;
import javax.ws.rs.GET;

@Path("/usuarios")
@PermitAll
public class Usuarios {
    UsuarioController control= UsuarioController.getInstance();
    @GET
    @Produces(MediaType.APPLICATION_JSON)    
    @PermitAll
    public List<Usuario> listarUsuarios(Usuario usuario) {  
            try {  
            return control.obtenerUsuarios();
            } catch (Exception ex) {
                throw new NotFoundException();
            }  
    }
    
    
}
