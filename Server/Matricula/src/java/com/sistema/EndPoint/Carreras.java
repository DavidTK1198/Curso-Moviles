package com.sistema.Endpoint;



import com.sistema.LogicaNegocio.Carrera;
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
@Path("/carreras")
@PermitAll
public class Carreras {
        @GET
    @Path("listar")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Carrera> getCarrera() {
        try {
           return null; //control.todosLosAlumnos();
        } catch (Exception ex) {
            throw new NotFoundException(); 
        }
    }
}
