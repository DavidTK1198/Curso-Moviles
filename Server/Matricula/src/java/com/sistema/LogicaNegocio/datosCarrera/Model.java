package com.sistema.LogicaNegocio.datosCarrera;

import com.sistema.LogicaNegocio.Carrera;

/**
 * @author DavidTK1198
 */
public class Model {
    private static Model uniqueInstance;
    Carrera current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    public Model() {
        this.current = new Carrera();
    }

    public Carrera getCurrent() {
        return current;
    }

    public void setCurrent(Carrera current) {
        this.current = current;
    }
}
