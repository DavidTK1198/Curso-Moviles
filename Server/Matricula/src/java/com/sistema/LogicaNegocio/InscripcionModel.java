/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.LogicaNegocio;

/**
 *
 * @author USER
 */
public class InscripcionModel {
        private static InscripcionModel uniqueInstance;
    Inscripcion current;

    public static InscripcionModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new InscripcionModel();

        }
        return uniqueInstance;
    }

    public InscripcionModel() {
        this.current = new Inscripcion();
    }

    public Inscripcion getCurrent() {
        return current;
    }

    public void setCurrent(Inscripcion current) {
        this.current = current;
    }
}
