package com.sistema.Endpoint;

import com.sistema.Controller.CicloController;
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
@Path("/ciclos")
@PermitAll
public class Ciclos {

    CicloController control = CicloController.getInstance();

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

    // http://localhost:8088/Matricula/api/ciclos/cicloActivar?annio=2022
    @GET
    @Path("cicloAnnio")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public List<Ciclo> buscarPorAnnio(@DefaultValue("") @QueryParam("annio") String annio) {
        try {
            return control.cicloPorAnnio(Integer.parseInt(annio));
        } catch (Exception ex) {
            throw new NotFoundException();
        }

    }

    @PUT
    @Path("cicloActivar")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public void activar(Ciclo p) {
        try {
            control.activarCiclo(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
    
     @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public void agregar(Ciclo p) {
        try {
            control.agregar(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @PUT
    @Path("cicloDesActivar")
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public void desactivar(Ciclo p) {
        try {
            control.desactivarCiclo(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }

    @GET
    @Path("cicloCodigo")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public Ciclo buscarPorCodigo(@DefaultValue("") @QueryParam("codigo") String codigo) {
        try {
            return control.cicloPorCodigo(Integer.parseInt(codigo));
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("activo")
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public Ciclo obtenerActivo(@DefaultValue("") @QueryParam("codigo") String codigo) {
        try {
            return control.cicloActivo();
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    
    
    @PermitAll
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void EliminarCiclo(@DefaultValue("")
            @QueryParam("id") String id
    ) {
        try {
            control.eliminarCiclo(id);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
