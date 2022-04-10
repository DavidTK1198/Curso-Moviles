package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class CarreraModel {
        private static CarreraModel uniqueInstance;
    Carrera current;

    public static CarreraModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CarreraModel();

        }
        return uniqueInstance;
    }

    public CarreraModel() {
        this.current = new Carrera();
    }

    public Carrera getCurrent() {
        return current;
    }

    public void setCurrent(Carrera current) {
        this.current = current;
    }
}
