package com.sistema.LogicaNegocio.datosCurso;

import com.sistema.LogicaNegocio.Curso;

/**
 * @author DavidTK1198
 */
public class Model {
    private static Model uniqueInstance;
    Curso current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    public Model() {
        this.current = new Curso();
    }

    public Curso getCurrent() {
        return current;
    }

    public void setCurrent(Curso current) {
        this.current = current;
    }
}
