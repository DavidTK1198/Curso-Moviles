
package com.sistema.Endpoint;

import com.sistema.LogicaNegocio.Usuario;
import javax.annotation.security.PermitAll;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.sistema.Controller.UsuarioController;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

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
    
     @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void actualizarUsuario(Usuario p) {
        try {
            control.actualizarUsuario(p);
        } catch (Exception ex) {
            throw new NotFoundException("No se ha encontrado el curso");
        }
    }
}
