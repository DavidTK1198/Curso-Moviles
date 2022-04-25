package com.sistema.AccesoDatos;

import com.sistema.LogicaNegocio.Alumno;
import com.sistema.LogicaNegocio.Carrera;
import com.sistema.LogicaNegocio.Ciclo;
import com.sistema.LogicaNegocio.Curso;
import com.sistema.LogicaNegocio.Grupo;
import com.sistema.LogicaNegocio.Profesor;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DavidTK1198
 */
public class ServicioTransformar {

    private static ServicioTransformar instance = null;

    private ServicioTransformar() {

    }

    public static ServicioTransformar getInstance() {
        if (instance == null) {
            instance = new ServicioTransformar();
        }
        return instance;
    }

    public Carrera Obtenercarrera(ResultSet rs) throws SQLException {
        Carrera carrera = new Carrera(rs.getString("car_codigo"),
                rs.getString("car_name"),
                rs.getString("titulo"));
        return carrera;
    }

    Profesor obtenerProfesor(ResultSet rs) throws SQLException {
        return new Profesor(rs.getString("cedula"), rs.getString("nombre"), rs.getString("telefono"),
                rs.getString("email"));
    }

    Grupo obtenerGrupo(ResultSet rs) throws SQLException {
        Grupo grupo;
        grupo = new Grupo(rs.getInt("cupo"),
                rs.getInt("disponible"),
                rs.getInt("numgrupo"),
                rs.getString("horario"),
                new Ciclo(), new Profesor(), ObtenerCurso(rs)
        );
        grupo.setIdEntidad(rs.getInt("identidadg"));
        return grupo;
    }

    Alumno obtenerAlumno(ResultSet rs) throws SQLException {
        return new Alumno(rs.getString("cedula"),
                rs.getString("nombre"),
                rs.getString("telefono"),
                rs.getString("email"),
                rs.getString("fec_nac"),
                new Carrera()
        );
    }

    public Curso ObtenerCurso(ResultSet rs) throws SQLException {
          Curso curso = new Curso(rs.getString("codigo"),
                        rs.getString("nomcur"),
                        rs.getInt("creditos"),
                        rs.getInt("hsemanales"), new Carrera());
          return curso;
    }
}
