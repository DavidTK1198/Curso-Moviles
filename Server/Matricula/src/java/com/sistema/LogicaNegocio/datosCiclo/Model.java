package com.sistema.LogicaNegocio.datosCiclo;

import com.sistema.LogicaNegocio.Ciclo;

public class Model {
    private static Model uniqueInstance;
    Ciclo current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    public Model() {
        this.current = new Ciclo();
    }

    public Ciclo getCurrent() {
        return current;
    }

    public void setCurrent(Ciclo current) {
        this.current = current;
    }
}
