package com.sistema.AccesoDatos;

import com.sistema.LogicaNegocio.Carrera;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author DavidTK1198
 */
public class ServicioTransformar {

    private static ServicioTransformar instance = null;

    public ServicioTransformar() {

    }

    public static ServicioTransformar getInstance() {
        if (instance == null) {
            instance = new ServicioTransformar();
        }
        return instance;
    }
    
       public Carrera Obtenercarrera(ResultSet rs) throws SQLException{
       Carrera carrera = new Carrera(rs.getString("car_codigo"),
                        rs.getString("car_name"),
                        rs.getString("titulo"));
        return carrera;
    }
}
