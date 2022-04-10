package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class ProfesorModel {

    private static ProfesorModel uniqueInstance;
    Profesor current;

    public static ProfesorModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ProfesorModel();

        }
        return uniqueInstance;
    }

    public ProfesorModel() {
        this.current = new Profesor();
    }

    public Profesor getCurrent() {
        return current;
    }

    public void setCurrent(Profesor current) {
        this.current = current;
    }
}
