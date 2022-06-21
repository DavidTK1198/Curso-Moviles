/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.LogicaNegocio;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioCiclo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class CicloModel {

    private static CicloModel uniqueInstance;
    private Ciclo current;
    private final ServicioCiclo ciclo_DBA;
    private List<Ciclo> ciclos;

    public static CicloModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CicloModel();

        }
        return uniqueInstance;
    }

    private CicloModel() {
        this.current = new Ciclo();
        this.ciclos = new ArrayList<>();
        this.ciclo_DBA = ServicioCiclo.getInstance();
    }

    public Ciclo getCurrent() {
        return current;
    }

    public void setCurrent(Ciclo current) {
        this.current = current;
    }

    public List<Ciclo> getCiclos() {
        return ciclos;
    }

    public void setCiclos(List<Ciclo> ciclos) {
        this.ciclos = ciclos;
    }

    public void todosLosCiclos() throws GlobalException, NoDataException {

        ciclos = (List<Ciclo>) ciclo_DBA.listarCiclo("todos",0);
    }
    

    
    public void buscarId() throws GlobalException, NoDataException{
        current= ciclo_DBA.buscarCiclo(current.getId(),"normal");
    }
    
     public void buscarAnnio() throws GlobalException, NoDataException{
       ciclos = (List<Ciclo>) ciclo_DBA.listarCiclo("annio",current.getAnnio());
    }

    public void activarCiclo() throws GlobalException, NoDataException {
        ciclo_DBA.modificarCiclo(current,"activar");
    }
    
    public void desactivarCiclo() throws GlobalException, NoDataException {
        ciclo_DBA.modificarCiclo(current,"desactivar");
    }

    public void cicloActivo() throws GlobalException, NoDataException {
        current= ciclo_DBA.buscarCiclo(0,"activo");
    }

    public void agregar() throws GlobalException, NoDataException {
        this.ciclo_DBA.insertarCiclo(current);
    }

    public void eliminarCiclo() throws GlobalException, NoDataException {
        this.ciclo_DBA.eliminarCiclos(Integer.toString(current.getId()));
    }
}
