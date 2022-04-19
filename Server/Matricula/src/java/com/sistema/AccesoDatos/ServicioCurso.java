package com.sistema.AccesoDatos;

import com.sistema.LogicaNegocio.Carrera;
import com.sistema.LogicaNegocio.Curso;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author Estudiante
 */
public class ServicioCurso extends Servicio {

    private static final String insertarCurso = "{call insertarCurso (?,?,?,?)}";
    private static final String LISTAR = "{?=call listarcurso()}";
    private static final String BUSCARID = "{?=call buscarcurso(?)}";
    private static final String BUSCARCARRERA = "{?=call buscarcursoporcarrera(?)}";
    private static final String BUSCARNOMBRE = "{?=call buscarcursopornombre(?)}";
    private static final String modificarCurso = "{call modificarCurso (?,?,?,?)}";
    private static final String eliminarCurso = "{call eliminarCurso(?)}";
    private static ServicioCurso instance = null;
    private static ServicioTransformar helper = null;

    /**
     * Creates a new instance of ServicioCurso
     */
    public ServicioCurso() {
        super();
    }

    public static ServicioCurso getInstance() {
        if (instance == null) {
            instance = new ServicioCurso();
        }
        return instance;
    }

    public Collection listarCurso(String medio, String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Curso curso = null;
        CallableStatement pstmt = null;
        try {

            switch (medio) {
                case "todos":
                    pstmt = conexion.prepareCall(LISTAR);
                    break;
                case "carrera":
                    pstmt = conexion.prepareCall(BUSCARCARRERA);
                    pstmt.setString(2, id);
                    break;
            }
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                curso = new Curso();
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombre(rs.getString("nombre"));
                curso.setCreditos(rs.getInt("creditos"));
                curso.setHsemanales(rs.getInt("hsemanales"));
                curso.setCarrera(new Carrera());
                coleccion.add(curso);
                curso.getCarrera().setNombre("Sistemas");
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null || coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
        return coleccion;
    }

    public void insertarCurso(Curso curso) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {

            pstmt = conexion.prepareCall(insertarCurso);
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getNombre());
            pstmt.setInt(3, curso.getCreditos());
            pstmt.setInt(4, curso.getHsemanales());

            boolean resultado = pstmt.execute();
            if (resultado == true) {
                throw new NoDataException("No se realizo la inserci�n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new GlobalException("Llave duplicada");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public void modificarCurso(Curso curso) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(modificarCurso);
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getNombre());
            pstmt.setInt(3, curso.getCreditos());
            pstmt.setInt(4, curso.getHsemanales());

            int resultado = pstmt.executeUpdate();

            //si es diferente de 0 es porq si afecto un registro o mas
            if (resultado != 0) {
                throw new NoDataException("No se realizo la actualización");
            } else {
                System.out.println("\nModificación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public void eliminarCursos(String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(eliminarCurso);
            pstmt.setString(1, id);

            int resultado = pstmt.executeUpdate();

            if (resultado != 0) {
                throw new NoDataException("No se realizo el borrado");
            } else {
                System.out.println("\nEliminación Satisfactoria!");
            }
        } catch (SQLException e) {
            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
    }

    public Curso buscarCurso(String id, String medio) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Curso curso = null;
        CallableStatement pstmt = null;
        try {
            switch (medio) {
                case "nombre":
                    pstmt = conexion.prepareCall(BUSCARNOMBRE);
                    break;
                case "codigo":
                    pstmt = conexion.prepareCall(BUSCARID);
                    break;
            }

            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                curso = new Curso(rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("creditos"),
                        rs.getInt("hsemanales"), new Carrera());

                coleccion.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();

            throw new GlobalException("Sentencia no valida");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                desconectar();
            } catch (SQLException e) {
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        if (coleccion == null || coleccion.size() == 0) {
            throw new NoDataException("No hay datos");
        }
        return curso;
    }

}
