
package com.sistema.LogicaNegocio.datosAlumno;

import com.sistema.LogicaNegocio.Alumno;

/**
 * @author DavidTK1198
 */
public class Model {
    private static Model uniqueInstance;
    Alumno current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    public Model() {
        this.current = new Alumno();
    }

    public Alumno getCurrent() {
        return current;
    }

    public void setCurrent(Alumno current) {
        this.current = current;
    }
}
