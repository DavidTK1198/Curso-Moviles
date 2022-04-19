package com.sistema.AccesoDatos;

import com.sistema.LogicaNegocio.Ciclo;
import com.sistema.LogicaNegocio.Curso;
import com.sistema.LogicaNegocio.Grupo;
import com.sistema.LogicaNegocio.Profesor;
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
public class ServicioGrupo extends Servicio {

    private static final String insertarGrupo = "{call insertarGrupo (?,?,?,?,?,?,?)}";
    private static final String LISTAR = "{?=call listargrupo(?,?)}";
    private static final String BUSCARID = "{?=call buscargrupo(?,?,?)}";
    private static final String modificarGrupo = "{call modificarGrupo (?,?,?,?,?,?,?)}";
    private static final String eliminarGrupo = "{call eliminarGrupo(?)}";
    private static ServicioGrupo instance = null;
    private static ServicioTransformar helper = null;

    /**
     * Creates a new instance of ServicioGrupo
     */
    public ServicioGrupo() {
        super();
        helper = ServicioTransformar.getInstance();
    }

    public static ServicioGrupo getInstance() {
        if (instance == null) {
            instance = new ServicioGrupo();
        }
        return instance;
    }

    public Collection listarGrupo(int ciclo, String curso) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Grupo grupo = null;
        CallableStatement pstmt = null;
        try {
            pstmt = conexion.prepareCall(LISTAR);
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setInt(3, ciclo);
            pstmt.setString(2, curso);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                grupo = new Grupo(rs.getInt("cupo"),
                        rs.getInt("disponible"),
                        rs.getInt("numgrupo"),
                        rs.getString("horario"),
                        new Ciclo(), helper.obtenerProfesor(rs), new Curso()
                );
                grupo.setIdEntidad(rs.getInt("identidad"));
                coleccion.add(grupo);
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

    public void insertarGrupo(Grupo grupo) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {

            pstmt = conexion.prepareCall(insertarGrupo);
            pstmt.setInt(1, grupo.getNumero());
            pstmt.setInt(2, grupo.getCupo());
            pstmt.setInt(3, grupo.getDisponible());
            pstmt.setString(4, grupo.getCurso().getCodigo());
            pstmt.setString(5, grupo.getHorario());
            pstmt.setInt(6, grupo.getCiclo().getId());
            pstmt.setString(7, grupo.getProfesor().getCedula());
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

    public void modificarGrupo(Grupo grupo) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(modificarGrupo);
            pstmt.setInt(1, grupo.getNumero());
            pstmt.setInt(2, grupo.getCupo());
            pstmt.setInt(3, grupo.getDisponible());
            pstmt.setString(4, grupo.getCurso().getCodigo());
            pstmt.setString(5, grupo.getHorario());
            pstmt.setInt(6, grupo.getCiclo().getId());
            pstmt.setString(7, grupo.getProfesor().getCedula());
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

    public void eliminarGrupos(String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(eliminarGrupo);
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

    public Grupo buscarGrupo(String id) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Grupo grupo = null;
        CallableStatement pstmt = null;
        try {

            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.setString(2, id);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
//                grupo = new Grupo(rs.getString("codigo"),
//                        rs.getString("nombre"),
//                        rs.getString("titulo"));
//                coleccion.add(grupo);
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
        return grupo;
    }

}
