package com.sistema.Endpoint;

import com.sistema.Controller.InscripcionController;
import com.sistema.LogicaNegocio.Inscripcion;
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
@Path("/inscripciones")
@PermitAll
public class Inscripciones {

    InscripcionController control = InscripcionController.getInstance();

    //http://localhost:8088/Matricula/api/inscripciones/alumno?ced=333
    @GET
    @Path("alumno")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Inscripcion> porAlumno(@DefaultValue("") @QueryParam("ced") String alumno) {
        try {
            return control.listarPorAlumno(alumno);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("grupo")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Inscripcion> porGrupo(@DefaultValue("") @QueryParam("id") String grupo) {
        try {
            return this.control.listarPorGrupo(grupo);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public void actualizarNota(Inscripcion p) {
        try {
            control.asignarNota(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarInscripcion(Inscripcion p) {
        try {
            control.agregarInscripcion(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void EliminarInscripcion(@DefaultValue("") @QueryParam("id") String id ) {
        try {
            control.eliminarInscripcion(id);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

}
