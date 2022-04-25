package com.sistema.Endpoint;

import com.sistema.Controller.GrupoController;
import com.sistema.LogicaNegocio.Grupo;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
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
@Path("/grupos")
@PermitAll
public class Grupos {

    GrupoController control = GrupoController.getInstance();
//http://localhost:8088/Matricula/api/grupos/listar?ciclo=10000&codigo=EIF201
    @GET
    @Path("listar")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Grupo> getGrupos(@DefaultValue("") @QueryParam("ciclo") String ciclo,@DefaultValue("") @QueryParam("codigo") String codigo) {
        try {
            return control.gruposPorCicloCurso(ciclo, codigo);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarGrupo(Grupo p) {
        try {
            control.agregarGrupo(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
@GET
    @Path("profesor")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Grupo> GruposPorProfesor(@DefaultValue("") @QueryParam("ced") String profesor) {
        try {
            return control.gruposPorProfesor(profesor);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void Actualizar(Grupo p) {
        try {
         control.actualizarGrupo(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

  @GET
    @Path("buscar")
    @Produces({MediaType.APPLICATION_JSON})
    public Grupo buscar(@DefaultValue("") @QueryParam("id") String id) {
        try {
            return control.grupoid(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

}
