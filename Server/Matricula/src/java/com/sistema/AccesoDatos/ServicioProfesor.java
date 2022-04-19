package com.sistema.AccesoDatos;

import com.sistema.LogicaNegocio.Profesor;
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
public class ServicioProfesor extends Servicio {

    private static final String insertarProfesor = "{call insertarProfesor (?,?,?,?)}";
    private static final String LISTAR = "{?=call listarprofesor()}";
    private static final String BUSCARID = "{?=call buscarprofesor(?)}";
    private static final String BUSCARNOMBRE = "{?=call buscarprofesorpornombre(?)}";
    private static final String modificarProfesor = "{call modificarProfesor (?,?,?,?)}";
    private static final String eliminarProfesor = "{call eliminarProfesor(?)}";
    private static ServicioProfesor instance = null;

    /**
     * Creates a new instance of ServicioProfesor
     */
    public ServicioProfesor() {
        super();
    }

    public static ServicioProfesor getInstance() {
        if (instance == null) {
            instance = new ServicioProfesor();
        }
        return instance;
    }

    public Collection listarProfesor() throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Profesor profesor = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                profesor = new Profesor(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                coleccion.add(profesor);
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

    public void insertarProfesor(Profesor profesor) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {

            pstmt = conexion.prepareCall(insertarProfesor);
            pstmt.setString(1, profesor.getCedula());
            pstmt.setString(2, profesor.getNombre());
            pstmt.setString(3, profesor.getTeléfono());
            pstmt.setString(4, profesor.getEmail());

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

    public void modificarProfesor(Profesor profesor) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(modificarProfesor);
            pstmt.setString(1, profesor.getCedula());
            pstmt.setString(2, profesor.getNombre());
            pstmt.setString(3, profesor.getTeléfono());
            pstmt.setString(4, profesor.getEmail());
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

    public void eliminarProfesors(String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(eliminarProfesor);
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

    public Profesor buscarProfesor(String id,String medio) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Profesor profesor = null;
        CallableStatement pstmt = null;
        try {

            switch (medio) {
                case "nombre":
                    pstmt = conexion.prepareCall(BUSCARNOMBRE);
                    break;
                case "cedula":
                    pstmt = conexion.prepareCall(BUSCARID);
                    break;
            }
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                profesor = new Profesor(rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"));
                coleccion.add(profesor);
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
        return profesor;
    }

}
