/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.Endpoint;

import com.sistema.AccesoDatos.ServicioCarrera;
import com.sistema.Controller.CursoController;
import com.sistema.LogicaNegocio.Carrera;
import com.sistema.LogicaNegocio.Curso;
import com.sistema.LogicaNegocio.Inscripcion;
import javax.annotation.security.PermitAll;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;

@Path("/cursos")
@PermitAll
public class Cursos {
    CursoController control = CursoController.getInstance();
    @GET
    @Path("listar")
    @PermitAll
    @Produces({MediaType.APPLICATION_JSON})
    public List<Curso> getCursos() {
        try {
            return control.todosLosCursos();
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    //Ejemplo
    //http://localhost:8088/Matricula/api/cursos/cursoCarrera?codigo=EIF
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("cursoCarrera")
    public List<Curso> buscarPorCarrera(@DefaultValue("") @QueryParam("codigo") String codigo) {
        try {
            return control.cursoporCarrera(codigo);
        } catch (Exception ex) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("cursoNombre")
    @Produces({MediaType.APPLICATION_JSON})
    public Curso buscarPorNombre(@DefaultValue("") @QueryParam("nombre") String nombre) {
        try {
            return control.cursoPorNombre(nombre);
        } catch (Exception ex) {
            throw new NotFoundException();
        }

    }

    @GET
    @Path("cursoCodigo")
    @Produces({MediaType.APPLICATION_JSON})
    public Curso buscarPorCodigo(@DefaultValue("") @QueryParam("codigo") String codigo) {
        try {
            return control.cursoPorCodigo(codigo);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void actualizarCurso(Curso p) {
//        try {
//          control.actualizarCurso(p);
//        } catch (Exception ex) {
//            throw new NotFoundException("No se ha encontrado el curso");
//        }
//    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarCurso(Curso p) {
        try {
            control.agregarCurso(p);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
    /*
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void agregarCurso(@DefaultValue("") @QueryParam("codigo") String codigo, @DefaultValue("") @QueryParam("nombre") String nombre, @DefaultValue("") @QueryParam("hsemanales") int hsemanales, @DefaultValue("") @QueryParam("creditos") int creditos, @DefaultValue("") @QueryParam("idCarrera") String idCarrera) {
        try {
            Carrera carrera = ServicioCarrera.getInstance().buscarCarrera(idCarrera, "codigo");
            Curso c = new Curso(codigo, nombre, creditos, hsemanales, carrera);
            control.agregarCurso(c);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }*/

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void EliminarCurso(@DefaultValue("") @QueryParam("id") String id ) {
        try {
            control.eliminarCurso(id);
        } catch (Exception ex) {
            throw new NotAcceptableException();
        }
    }
}
