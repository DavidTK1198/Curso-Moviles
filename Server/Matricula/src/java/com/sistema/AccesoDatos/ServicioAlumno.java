package com.sistema.AccesoDatos;

import com.sistema.LogicaNegocio.Alumno;
import com.sistema.LogicaNegocio.Carrera;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author Estudiante
 */
public class ServicioAlumno extends Servicio {

    private static final String insertarAlumno = "{call insertarAlumno (?,?,?,?)}";
    private static final String LISTAR = "{?=call listarAlumno()}";
    private static final String BUSCARID = "{?=call buscaralumno(?)}";
    private static final String modificarAlumno = "{call modificarAlumno (?,?,?,?)}";
    private static final String eliminarAlumno = "{call eliminarAlumno(?)}";
    private static ServicioAlumno instance = null;

    /**
     * Creates a new instance of ServicioAlumno
     */
    public ServicioAlumno() {
        super();
    }

    public static ServicioAlumno getInstance() {
        if (instance == null) {
            instance = new ServicioAlumno();
        }
        return instance;
    }

    public Collection listarAlumno() throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Alumno alumno = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                alumno = new Alumno(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("fec_nac"),
                        carreraTransform(rs)
                );
                coleccion.add(alumno);
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

    public void insertarAlumno(Alumno alumno) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {

            pstmt = conexion.prepareCall(insertarAlumno);
            pstmt.setString(1, alumno.getCédula());
            pstmt.setString(2, alumno.getNombre());
            pstmt.setString(3, alumno.getTeléfono());
            pstmt.setString(4, alumno.getEmail());

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

    public void modificarAlumno(Alumno alumno) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(modificarAlumno);
            pstmt.setString(1, alumno.getCédula());
            pstmt.setString(2, alumno.getNombre());
            pstmt.setString(3, alumno.getTeléfono());
            pstmt.setString(4, alumno.getEmail());
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

    public void eliminarAlumnos(String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(eliminarAlumno);
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

    public Alumno buscarAlumno(String id) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Alumno alumno = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(BUSCARID);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
               alumno = new Alumno(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("fec_nac"),
                        carreraTransform(rs));
                coleccion.add(alumno);
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
        return alumno;
    }
    
    private Carrera carreraTransform(ResultSet rs) throws SQLException{
       Carrera carrera = new Carrera(rs.getString("codigo"),
                        rs.getString("car_name"),
                        rs.getString("titulo"));
        return carrera;
    }

}
