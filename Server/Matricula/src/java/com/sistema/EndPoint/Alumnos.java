package com.sistema.Endpoint;



import com.sistema.Controller.AlumnoController;
import com.sistema.LogicaNegocio.Alumno;
import com.sistema.LogicaNegocio.Ciclo;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
        } catch (Exception ex) {
            throw new NotFoundException(); 
        }
    }
    
        @GET
    @Path("alumnoNombre")
    @Produces({MediaType.APPLICATION_JSON})
    public Alumno buscarPorNombre(@DefaultValue("") @QueryParam("nombre") String nombre) {
        try {
            return control.alumnoPorNombre(nombre);
        } catch (Exception ex) {
            throw new NotFoundException();
        }

    }
 
     @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public void agregar(Alumno p) {
        try {
            control.agregar(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @GET
    @Path("alumnoCedula")
    @Produces({MediaType.APPLICATION_JSON})
    public Alumno buscarPorcedula(@DefaultValue("") @QueryParam("cedula") String cedula) {
        try {
            return control.alumnoPorCedula(cedula);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    
        @GET
    @Path("alumnoCarrera")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Alumno> buscarPorCarrera(@DefaultValue("") @QueryParam("codigo") String codigo) {
        try {
            return control.AlumnosporCarrera(codigo);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    
     @PermitAll
    @DELETE
    public void Eliminar(@DefaultValue("")
            @QueryParam("ced") String id
    ) {
        try {
            control.eliminar(id);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public void actualziar(Alumno p) {
        try {
            control.actualizar(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
