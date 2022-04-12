/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.Endpoint;
import com.sistema.Controller.CursoController;
import com.sistema.LogicaNegocio.Curso;
import javax.annotation.security.PermitAll;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.GET;

@Path("/cursos")
@PermitAll
public class Cursos {
        CursoController control= CursoController.getInstance();
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
    
}
