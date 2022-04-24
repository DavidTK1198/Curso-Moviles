
package com.sistema.Endpoint;

import com.sistema.LogicaNegocio.Usuario;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.sistema.Controller.UsuarioController;

@Path("/login")
@PermitAll
public class Login {
    UsuarioController control= UsuarioController.getInstance();
    @Context
    HttpServletRequest request;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Usuario login(Usuario usuario) {  
            Usuario logged=null;
            try {  
                logged= control.Login(usuario.getId(),usuario.getClave());
                request.getSession(true).setAttribute("user", logged);
                return logged;
            } catch (Exception ex) {
                throw new NotFoundException();
            }  
    }
    
    @DELETE 
    public void logout() {  
        HttpSession session = request.getSession(true);
        session.removeAttribute("user");           
        session.invalidate();
    }
    
}
