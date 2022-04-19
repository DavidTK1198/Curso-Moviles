package com.sistema.Controller;

import com.sistema.AccesoDatos.GlobalException;
import com.sistema.AccesoDatos.NoDataException;
import com.sistema.LogicaNegocio.Inscripcion;
import com.sistema.LogicaNegocio.InscripcionModel;
import java.util.List;

/**
 *
 * @author DavidTK1198
 */
public class InscripcionController {
    
    private static InscripcionController instance = null;
    private static final InscripcionModel model = InscripcionModel.instance();
    
    public static InscripcionController getInstance() {
        if (instance == null) {
            instance = new InscripcionController();
        }
        return instance;
    }
    
    private InscripcionController() {
    }
    
    public void agregarInscripcion(Inscripcion p) throws GlobalException, NoDataException {
        model.setCurrent(p);
        model.agregarInscripcion();
    }
    
    public void eliminarInscripcion(String id) throws GlobalException, NoDataException {
        model.getCurrent().setIdEntidad(Integer.parseInt(id));
        model.eliminarInscripcion();
    }

    public void asignarNota(Inscripcion p) throws GlobalException, NoDataException {
        model.setCurrent(p);
        model.asignarNota();
    }

    public List<Inscripcion> listarPorGrupo(String grupo) throws GlobalException, NoDataException {
         model.getCurrent().getGrupo().setIdEntidad(Integer.parseInt(grupo));
         model.InscripcionesPorGrupo();
       return model.getInscripciones();
    }

    public List<Inscripcion> listarPorAlumno(String alumno) throws GlobalException, NoDataException {
       model.getCurrent().getEstudiante().setCedula(alumno);
       model.InscripcionesPorAlmuno();
       return model.getInscripciones();
    }
    
}
