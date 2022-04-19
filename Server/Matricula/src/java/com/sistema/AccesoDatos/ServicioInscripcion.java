package com.sistema.AccesoDatos;

import com.sistema.LogicaNegocio.Inscripcion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author Estudiante
 */
public class ServicioInscripcion extends Servicio {

    private static final String insertarInscripcion = "{call insertarInscripcion (?,?)}";
    private static final String BUSCARALUMNO = "{?=call listarPorAlumno(?)}";
    private static final String BUSCARGRUPO = "{?=call listarPorGrupo(?)}";
    private static final String BUSCARID = "{?=call buscarInscripcion(?)}";
    private static final String modificarInscripcion = "{call modificarInscripcion (?,?)}";
    private static final String eliminarInscripcion = "{call eliminarInscripcion(?)}";
    private static ServicioInscripcion instance = null;
    private static ServicioTransformar helper = null;

    /**
     * Creates a new instance of ServicioInscripcion
     */
    private ServicioInscripcion() {
        super();
    }

    public static ServicioInscripcion getInstance() {
        if (instance == null) {
            instance = new ServicioInscripcion();
            helper = ServicioTransformar.getInstance();
        }
        return instance;
    }

    public Collection listarInscripcion(String medio, String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Inscripcion inscripcion = null;
        CallableStatement pstmt = null;

        try {
            switch (medio) {
                case "alumno":
                    pstmt = conexion.prepareCall(BUSCARALUMNO);
                    pstmt.setString(2, id);
                    break;
                case "grupo":
                    pstmt = conexion.prepareCall(BUSCARGRUPO);
                    pstmt.setInt(2, Integer.parseInt(id));
                    break;
            }
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                inscripcion = new Inscripcion(helper.obtenerAlumno(rs),
                        rs.getInt("nota"),
                        helper.obtenerGrupo(rs));
                inscripcion.setIdEntidad(rs.getInt("identidad"));
                coleccion.add(inscripcion);
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

    public void insertarInscripcion(Inscripcion inscripcion) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {

            pstmt = conexion.prepareCall(insertarInscripcion);
            pstmt.setInt(1, inscripcion.getGrupo().getIdEntidad());
            pstmt.setString(2, inscripcion.getEstudiante().getCedula());
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

    public void modificarInscripcion(Inscripcion inscripcion) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(modificarInscripcion);
            pstmt.setInt(1, inscripcion.getIdEntidad());
            pstmt.setInt(2, inscripcion.getNota());
            int resultado = pstmt.executeUpdate();

            //si es diferente de 0 es porq si afecto un registro o mas
            if (resultado == 0) {
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

    public void eliminarInscripcion(int id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(eliminarInscripcion);
            pstmt.setInt(1, id);

            int resultado = pstmt.executeUpdate();

            if (resultado == 0) {
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

    public Inscripcion buscarInscripcion(String id, String medio) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Inscripcion inscripcion = null;
        CallableStatement pstmt = null;
        try {

            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                inscripcion = new Inscripcion(helper.obtenerAlumno(rs),
                        rs.getInt("nota"),
                        helper.obtenerGrupo(rs));
                inscripcion.setIdEntidad(rs.getInt("identidad"));
                coleccion.add(inscripcion);
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
        return inscripcion;
    }

}
