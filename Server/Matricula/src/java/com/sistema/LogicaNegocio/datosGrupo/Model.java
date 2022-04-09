
package com.sistema.LogicaNegocio.datosGrupo;

import com.sistema.LogicaNegocio.Grupo;

/**
 * @author DavidTK1198
 */
public class Model {
    private static Model uniqueInstance;
    Grupo current;

    public static Model instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Model();

        }
        return uniqueInstance;
    }

    public Model() {
        this.current = new Grupo();
    }

    public Grupo getCurrent() {
        return current;
    }

    public void setCurrent(Grupo current) {
        this.current = current;
    }
}
