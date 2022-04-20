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

    private CursoModel() {
        this.current = new Curso();
        this.cursos = new ArrayList<>();
        this.curso_DBA = ServicioCurso.getInstance();
    }

    public Curso getCurrent() {
        return current;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public void setCurrent(Curso current) {
        this.current = current;
    }
    
    public void todosLosCursos() throws GlobalException, NoDataException{
        cursos= (List<Curso>) curso_DBA.listarCurso("todos","");
    }

    public void buscarporCodigo() throws GlobalException, NoDataException {
        current= curso_DBA.buscarCurso(current.getCodigo(),"codigo");
    }

    public void buscarporCarrera() throws GlobalException, NoDataException {
       cursos= (List<Curso>) curso_DBA.listarCurso("carrera",current.getCarrera().getCodigo());
    }

    public void buscarporNombre() throws GlobalException, NoDataException {
        current =curso_DBA.buscarCurso(current.getNombre(),"nombre");
    }

    public void eliminar() throws GlobalException, NoDataException{
       curso_DBA.eliminarCursos(current.getCodigo());
    }

    public void agregarCurso() throws GlobalException, NoDataException {
       this.curso_DBA.insertarCurso(current);
    }

    public void actualizar() throws GlobalException, NoDataException {
        this.curso_DBA.modificarCurso(current);
    }
}
