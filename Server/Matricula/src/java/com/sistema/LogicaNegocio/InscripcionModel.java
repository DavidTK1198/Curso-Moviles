/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.LogicaNegocio;

import com.sistema.AccesoDatos.ServicioInscripcion;
import java.util.List;

/**
 *
 * @author USER
 */
public class InscripcionModel {

    private static InscripcionModel uniqueInstance;
    private Inscripcion current;
    private final ServicioInscripcion inscripcion_DBA;
    private List<Inscripcion> inscripciones;

    public static InscripcionModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new InscripcionModel();

        }
        return uniqueInstance;
    }

    private InscripcionModel() {
        this.current = new Inscripcion();
        this.inscripcion_DBA=ServicioInscripcion.getInstance();
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public Inscripcion getCurrent() {
        return current;
    }

    public void setCurrent(Inscripcion current) {
        this.current = current;
    }

    public void todasLasInscripciones() {
        
    }
}
