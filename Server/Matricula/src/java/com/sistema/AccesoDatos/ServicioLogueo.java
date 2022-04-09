/*
 * ServicioLogueo.java
 *
 * Created on 8 de junio de 2007, 22:53
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sistema.AccesoDatos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.internal.OracleTypes;

/**
 *
 * @author Administrador
 */
public class ServicioLogueo extends Servicio {
    private static final String login = "{?=call login(?,?)}";
    private static ServicioLogueo instance = null;
   
    /** Creates a new instance of ServicioLogueo */
    public ServicioLogueo() {
        super();
    }
    
      public static ServicioLogueo getInstance() {
        if (instance == null) instance = new ServicioLogueo();
        return instance;
    }
    public boolean loginCliente(String user, String password) throws NoDataException, GlobalException{
        boolean resp=true;
        try {
            conectar();
        } catch (ClassNotFoundException e) {
            resp=false;
            throw new GlobalException("No se ha localizado el driver");
        } catch (SQLException e) {
            resp=false;
            throw new NoDataException("La base de datos no se encuentra disponible");
        }
        ResultSet rs = null;
        CallableStatement pstmt=null;  
        int respuesta=0;
        try {            
            pstmt = conexion.prepareCall(login);            
            pstmt.registerOutParameter(1, OracleTypes.CURSOR);            
            pstmt.setString(2,user);     
            pstmt.setString(3,password);            
            pstmt.execute();
            
            //********************************
            rs=(ResultSet) pstmt.getObject(1);
            while (rs.next()) {
             respuesta = rs.getInt("esta");
            }
             if(respuesta==0){
             resp=false;}
            //********************************
        } catch (SQLException e) {
          e.printStackTrace();
            resp=false;
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
                resp=false;
                throw new GlobalException("Estatutos invalidos o nulos");
            }
        }
        
        return resp;
        
    }
    
}



