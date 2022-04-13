package com.sistema.Endpoint;
import com.sistema.Controller.CicloController;
import com.sistema.LogicaNegocio.Ciclo;
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
@Path("/ciclos")
@PermitAll
public class Ciclos {
      CicloController control= CicloController.getInstance();
    @GET
    @Path("listar")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Ciclo> getCiclos() {
        try {
           return control.todosLosCiclos();
        } catch (Exception ex) {
            throw new NotFoundException(); 
        }
    }
}
