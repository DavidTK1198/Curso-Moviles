package com.sistema.LogicaNegocio;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioGrupo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class GrupoModel {

    private static GrupoModel uniqueInstance;
    private Grupo current;
    private final ServicioGrupo grupo_DBA;
    private List<Grupo> grupos;

    public static GrupoModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new GrupoModel();

        }
        return uniqueInstance;
    }

    private GrupoModel() {
        this.current = new Grupo();
        this.grupos = new ArrayList<>();
        this.grupo_DBA = ServicioGrupo.getInstance();
    }

    public Grupo getCurrent() {
        return current;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public void setCurrent(Grupo current) {
        this.current = current;
    }
    
    public void insertarGrupo() throws GlobalException, NoDataException{
        grupo_DBA.insertarGrupo(current);
    }

    public void gruposPorCicloCurso() throws GlobalException, NoDataException {
        grupos = (List<Grupo>) grupo_DBA.listarGrupo(current.getCiclo().getId(),current.getCurso().getCodigo(),"curso");
    }

    public void modificarGrupo() throws GlobalException, NoDataException {
       grupo_DBA.modificarGrupo(current);
    }

    public void buscarGrupo() throws GlobalException, NoDataException {
        this.grupo_DBA.buscarGrupo(current.getIdEntidad());
    }

    public void grupoPorProfesor() throws GlobalException, NoDataException {
        this.grupos=(List<Grupo>) grupo_DBA.listarGrupo(0, current.getProfesor().getCedula(), "profesor");
    }

    public void grupoPorCiclo() throws GlobalException, NoDataException {
        this.grupos=(List<Grupo>) grupo_DBA.listarGrupo(current.getCiclo().getId(), "", "ciclo");
    }

    public void eliminar() throws GlobalException, NoDataException {
       this.grupo_DBA.eliminarGrupos(Integer.toString(current.getIdEntidad()));
    }


}
