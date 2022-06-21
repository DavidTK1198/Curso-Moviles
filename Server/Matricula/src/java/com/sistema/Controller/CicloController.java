package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Ciclo;
import com.sistema.LogicaNegocio.CicloModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DavidTK1198
 */
public class CicloController {

    private static CicloController instance = null;
    private static final CicloModel model = CicloModel.instance();

    public static CicloController getInstance() {
        if (instance == null) {
            instance = new CicloController();
        }
        return instance;
    }

    private CicloController() {
    }

    public List<Ciclo> todosLosCiclos() throws GlobalException, NoDataException {
        model.todosLosCiclos();
        return model.getCiclos();
    }

    public Ciclo cicloPorCodigo(int codigo) throws GlobalException, NoDataException {
        model.getCurrent().setId(codigo);
        model.buscarId();
        return model.getCurrent();
    }

    public List<Ciclo> cicloPorAnnio(int annio) throws GlobalException, NoDataException {
        model.getCurrent().setAnnio(annio);
        model.buscarAnnio();
        return model.getCiclos();
    }

    public void activarCiclo(Ciclo p) throws GlobalException, NoDataException {
        model.setCurrent(p);
        model.activarCiclo();
    }

    public void desactivarCiclo(Ciclo p) throws GlobalException, NoDataException {
        model.setCurrent(p);
        model.desactivarCiclo();
    }

    public Ciclo cicloActivo() throws GlobalException, NoDataException {
        model.cicloActivo();
        return model.getCurrent();
    }

    public void agregar(Ciclo p) throws GlobalException, NoDataException {
       model.setCurrent(p);
       model.agregar();
    }

    public void eliminarCiclo(String id) throws GlobalException, NoDataException {
        model.getCurrent().setId(Integer.parseInt(id));
            model.eliminarCiclo();

    }

}
