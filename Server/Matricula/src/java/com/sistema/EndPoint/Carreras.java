package com.sistema.Endpoint;

import com.sistema.Controller.CarreraController;
import com.sistema.Controller.CursoController;
import com.sistema.LogicaNegocio.Carrera;
import com.sistema.LogicaNegocio.Carrera;
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
@Path("/carreras")
@PermitAll
public class Carreras {

    CarreraController control = CarreraController.getInstance();

    @GET
    @Path("listar")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Carrera> getCarrera() {
        try {
            return control.todasLasCarreras();
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("carreraNombre")
    @Produces({MediaType.APPLICATION_JSON})
    public Carrera buscarPorNombre(@DefaultValue("") @QueryParam("nombre") String nombre) {
        try {
            return control.carreraPorNombre(nombre);
        } catch (Exception ex) {
            throw new NotFoundException();
        }

    }

    @GET
    @Path("carreraCodigo")
    @Produces({MediaType.APPLICATION_JSON})
    public Carrera buscarPorCodigo(@DefaultValue("") @QueryParam("codigo") String codigo) {
        try {
            return control.carreraPorCodigo(codigo);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
}
