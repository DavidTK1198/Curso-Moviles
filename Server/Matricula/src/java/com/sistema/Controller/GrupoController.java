package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Grupo;
import com.sistema.LogicaNegocio.GrupoModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class GrupoController {

    private static GrupoController instance = null;
    private static final GrupoModel model =GrupoModel.instance();

    public static GrupoController getInstance() {
        if (instance == null) {
            instance = new GrupoController();
        }
        return instance;
    }

    private GrupoController() {
    }

    public List<Grupo> gruposPorCicloCurso(String ciclo,String codigo) throws GlobalException, NoDataException {
        model.getCurrent().getCiclo().setId(Integer.parseInt(ciclo));
        model.getCurrent().getCurso().setCodigo(codigo);
        model.gruposPorCicloCurso();
        return model.getGrupos();
    }

    public void agregarGrupo(Grupo p) throws GlobalException, NoDataException {
        model.setCurrent(p);
        model.insertarGrupo();
    }

    public void actualizarGrupo(Grupo p) throws GlobalException, NoDataException {
        model.setCurrent(p);
        model.modificarGrupo();
    }

 


}
