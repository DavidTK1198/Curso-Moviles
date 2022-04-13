package com.sistema.Endpoint;
import com.sistema.Controller.ProfesorController;
import com.sistema.LogicaNegocio.Profesor;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author DavidTK1198
 */
@Path("/profesores")
@PermitAll
public class Profesores {
           ProfesorController control= ProfesorController.getInstance();
    @GET
    @Path("listar")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Profesor> getProfesors() {
        try {
           return control.todosLosProfesors();
        } catch (Exception ex) {
            throw new NotFoundException(); 
        }
    }
}
