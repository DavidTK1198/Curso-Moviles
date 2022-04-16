package com.sistema.Endpoint;

import com.sistema.Controller.ProfesorController;
import com.sistema.LogicaNegocio.Profesor;
import com.sistema.LogicaNegocio.Profesor;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author DavidTK1198
 */
@Path("/profesores")
@PermitAll
public class Profesores {

    ProfesorController control = ProfesorController.getInstance();

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

    @GET
    @Path("profesorNombre")
    @Produces({MediaType.APPLICATION_JSON})
    public Profesor buscarPorNombre(@DefaultValue("") @QueryParam("nombre") String nombre) {
        try {
            return control.profesorPorNombre(nombre);
        } catch (Exception ex) {
            throw new NotFoundException();
        }

    }

    //ejemplo
//http://localhost:8088/Matricula/api/profesores/profesorCedula?cedula=100000002
    @GET
    @Path("profesorCedula")
    @Produces({MediaType.APPLICATION_JSON})
    public Profesor buscarPorcedula(@DefaultValue("") @QueryParam("cedula") String cedula) {
        try {
            return control.profesorPorCedula(cedula);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
}
