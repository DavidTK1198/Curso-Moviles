package com.sistema.LogicaNegocio.datosInscripcion;

import com.sistema.LogicaNegocio.Inscripcion;

public class Model {
    private static Model uniqueInstance;
    Inscripcion current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    public Model() {
        this.current = new Inscripcion();
    }

    public Inscripcion getCurrent() {
        return current;
    }

    public void setCurrent(Inscripcion current) {
        this.current = current;
    }
}
