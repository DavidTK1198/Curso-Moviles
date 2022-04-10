
package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class AlumnoModel {
        private static AlumnoModel uniqueInstance;
    Alumno current;

    public static AlumnoModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AlumnoModel();

        }
        return uniqueInstance;
    }

    public AlumnoModel() {
        this.current = new Alumno();
    }

    public Alumno getCurrent() {
        return current;
    }

    public void setCurrent(Alumno current) {
        this.current = current;
    }
}
