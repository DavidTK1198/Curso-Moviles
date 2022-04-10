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
public class CicloModel {
        private static CicloModel uniqueInstance;
    Ciclo current;

    public static CicloModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CicloModel();

        }
        return uniqueInstance;
    }

    public CicloModel() {
        this.current = new Ciclo();
    }

    public Ciclo getCurrent() {
        return current;
    }

    public void setCurrent(Ciclo current) {
        this.current = current;
    }
}
