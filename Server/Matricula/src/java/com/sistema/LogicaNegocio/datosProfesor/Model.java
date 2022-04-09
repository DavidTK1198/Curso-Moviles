
package com.sistema.LogicaNegocio.datosProfesor;

import com.sistema.LogicaNegocio.Profesor;

/**
 * @author DavidTK1198
 */
public class Model {
    private static Model uniqueInstance;
    Profesor current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    public Model() {
        this.current = new Profesor();
    }

    public Profesor getCurrent() {
        return current;
    }

    public void setCurrent(Profesor current) {
        this.current = current;
    }
}
