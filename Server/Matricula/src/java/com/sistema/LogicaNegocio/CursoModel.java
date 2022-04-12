package com.sistema.LogicaNegocio;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.AccesoDatos.ServicioCurso;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class CursoModel {

    private static CursoModel uniqueInstance;
    private Curso current;
    private final ServicioCurso curso_DBA;
    private List<Curso> cursos;

    public static CursoModel instance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CursoModel();

        }
        return uniqueInstance;
    }

    public CursoModel() {
        this.current = new Curso();
        this.cursos = new ArrayList<>();
        this.curso_DBA = ServicioCurso.getInstance();
    }

    public Curso getCurrent() {
        return current;
    }

    public void setCurrent(Curso current) {
        this.current = current;
    }
    
    public List<Curso> todosLosCursos() throws GlobalException, NoDataException{
        return (List<Curso>) curso_DBA.listarCurso();
    }
}
