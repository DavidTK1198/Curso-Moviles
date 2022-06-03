package com.accesoDatos

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.sql.DataSource


class Servicio {
    protected var conexion: Connection? = null
    @Throws(SQLException::class, ClassNotFoundException::class)
    protected fun conectar() {
        Class.forName("oracle.jdbc.driver.OracleDriver")
        // try {
        conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "root")
        //conexion = getJdbcMydbsource();
        /* } catch (NamingException ex) {
            ex.printStackTrace();
        }*/
    }

    @Throws(SQLException::class)
    protected fun desconectar() {
        if (!conexion!!.isClosed) {
            conexion!!.close()
        }
    }

    @get:Throws(NamingException::class)
    private val jdbcMydbsource: Connection?
        private get() {
            val c: javax.naming.Context = InitialContext()
            try {
                return (c.lookup("jdbc/Mydbsource") as DataSource).connection
            } catch (ex: NamingException) {
                ex.printStackTrace()
            } catch (ex: SQLException) {
                ex.printStackTrace()
            }
            return null
        }
}