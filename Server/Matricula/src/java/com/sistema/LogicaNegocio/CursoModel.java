package com.sistema.LogicaNegocio;

/**
 *
 * @author DavidTK1198
 */
public class CursoModel {

    private static CursoModel uniqueInstance;
    Curso current;

    public static CursoModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CursoModel();

        }
        return uniqueInstance;
    }

    public CursoModel() {
        this.current = new Curso();
    }

    public Curso getCurrent() {
        return current;
    }

    public void setCurrent(Curso current) {
        this.current = current;
    }
}
