package com.sistema.AccesoDatos;

/**
 *
 * @author DavidTK1198
 */
import com.sistema.LogicaNegocio.Ciclo;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author Estudiante
 */
public class ServicioCiclo extends Servicio {

    private static final String insertarCiclo = "{call insertarCiclo (?,?,?,?,?)}";
    private static final String LISTAR = "{?=call listarciclo()}";
    private static final String BUSCARID = "{?=call buscarciclo(?)}";
    private static final String BUSCARANNIO = "{?=call buscarCicloAnnio(?)}";
    private static final String MODIFICARCICLO = "{call modificarCiclo (?,?,?,?,?,?)}";
    private static final String ELIMINARCICLO = "{call eliminarCiclo(?)}";
    private static final String REVISARACTIVO = "{?=call revisaractivo()}";
    private static final String ACTIVO = "{?=call cicloactivo()}";
    private static ServicioCiclo instance = null;

    /**
     * Creates a new instance of ServicioCiclo
     */
    public ServicioCiclo() {
        super();
    }

    public static ServicioCiclo getInstance() {
        if (instance == null) {
            instance = new ServicioCiclo();
        }
        return instance;
    }

    public Collection listarCiclo(String medio, int valor) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            throw new GlobalException("No se ha localizado el Driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }

        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Ciclo ciclo = null;
        CallableStatement pstmt = null;
        try {

            switch (medio) {
                case "annio":
                    pstmt = conexion.prepareCall(BUSCARANNIO);
                    pstmt.setInt(2, valor);
                    break;
                case "todos":
                    pstmt = conexion.prepareCall(LISTAR);
                    break;
            }

            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                ciclo = new Ciclo(rs.getInt("id"),
                        rs.getInt("annio"),
                        rs.getInt("numero"),
                        rs.getInt("estado"),
                        rs.getString("fec_inicio"),
                        rs.getString("fec_final"));

                coleccion.add(ciclo);
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

    public void insertarCiclo(Ciclo ciclo) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        CallableStatement pstmt = null;

        try {

            pstmt = conexion.prepareCall(insertarCiclo);
            pstmt.setInt(1, ciclo.getAnnio());
            pstmt.setInt(2, ciclo.getNumero());
            pstmt.setInt(3, ciclo.isEstado());
            pstmt.setString(4, ciclo.getFec_inicio());
            pstmt.setString(5, ciclo.getFec_final());

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

    public void modificarCiclo(Ciclo ciclo, String opc) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CallableStatement castmt = null;
        int estado = ciclo.isEstado();
        try {
            if ("activar".equals(opc)) {
                castmt = conexion.prepareCall(REVISARACTIVO);
                castmt.registerOutParameter(1, OracleTypes.CURSOR);
                castmt.execute();
                int respuesta = 0;
                rs = (ResultSet) castmt.getObject(1);
                while (rs.next()) {
                    respuesta = rs.getInt("esta");
                }
                if (respuesta > 0) {
                    throw new GlobalException("Ya existe un ciclo activo");
                }
            }
            castmt = conexion.prepareCall(BUSCARID);
            castmt.registerOutParameter(1, OracleTypes.CURSOR);
            castmt.setInt(2, ciclo.getId());
            castmt.execute();
            rs = (ResultSet) castmt.getObject(1);
            while (rs.next()) {
                ciclo = new Ciclo(rs.getInt("id"),
                        rs.getInt("annio"),
                        rs.getInt("numero"),
                        rs.getInt("estado"),
                        rs.getString("fec_inicio"),
                        rs.getString("fec_final"));
            }
            pstmt = conexion.prepareStatement(MODIFICARCICLO);
            pstmt.setInt(1, ciclo.getId());
            pstmt.setInt(2, ciclo.getAnnio());
            pstmt.setInt(3, ciclo.getNumero());
            pstmt.setInt(4, estado);
            pstmt.setString(5, ciclo.getFec_inicio());
            pstmt.setString(6, ciclo.getFec_final());
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

    public void eliminarCiclos(String id) throws GlobalException, NoDataException {
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        PreparedStatement pstmt = null;
        try {
            pstmt = conexion.prepareStatement(ELIMINARCICLO);
            pstmt.setString(1, id);

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

    public Ciclo buscarCiclo(int id, String medio) throws GlobalException, NoDataException {

        try {
            conectar();
        } catch (ClassNotFoundException e) {
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        ArrayList coleccion = new ArrayList();
        Ciclo ciclo = null;
        CallableStatement pstmt = null;
        try {

            switch (medio) {
                case "normal":
                    pstmt = conexion.prepareCall(BUSCARID);
                    pstmt.setInt(2, id);
                    break;
                case "activo":
                    pstmt = conexion.prepareCall(ACTIVO);
                    break;
            }

            pstmt.registerOutParameter(1, OracleTypes.CURSOR);
            pstmt.execute();
            rs = (ResultSet) pstmt.getObject(1);
            while (rs.next()) {
                ciclo = new Ciclo(rs.getInt("id"),
                        rs.getInt("annio"),
                        rs.getInt("numero"),
                        rs.getInt("estado"),
                        rs.getString("fec_inicio"),
                        rs.getString("fec_final"));
                coleccion.add(ciclo);
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
        return ciclo;
    }

}
