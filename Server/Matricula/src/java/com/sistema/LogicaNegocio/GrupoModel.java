package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class GrupoModel {

    private static GrupoModel uniqueInstance;
    Grupo current;

    public static GrupoModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GrupoModel();

        }
        return uniqueInstance;
    }

    public GrupoModel() {
        this.current = new Grupo();
    }

    public Grupo getCurrent() {
        return current;
    }

    public void setCurrent(Grupo current) {
        this.current = current;
    }
}
