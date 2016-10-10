
package modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public Connection getConexion()
    {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(Conexion.class.getName());
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:bd_exergia.db");
        } catch (SQLException e) {
            System.out.println(Conexion.class.getName());
        }
        
        return conn;
    }
}
