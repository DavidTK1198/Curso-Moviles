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
    private List<Ciclo> cursos;

    public static CicloModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CicloModel();

        }
        return uniqueInstance;
    }

    public CicloModel() {
        this.current = new Ciclo();
        this.cursos = new ArrayList<>();
        this.ciclo_DBA = ServicioCiclo.getInstance();
    }

    public Ciclo getCurrent() {
        return current;
    }

    public void setCurrent(Ciclo current) {
        this.current = current;
    }
    
    public List<Ciclo> todosLosCiclos() throws GlobalException, NoDataException{
        return (List<Ciclo>) ciclo_DBA.listarCiclo();
    }
}
