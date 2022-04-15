package com.sistema.Endpoint;



import com.sistema.Controller.AlumnoController;
import com.sistema.LogicaNegocio.Alumno;
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
@Path("/alumnos")
@PermitAll
public class Alumnos {
       AlumnoController control= AlumnoController.getInstance();
    @GET
    @Path("listar")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Alumno> getAlumnos() {
        try {
           return control.todosLosAlumnos();
           //return "{'chafa':500}";
        } catch (Exception ex) {
            throw new NotFoundException(); 
        }
    }
}
