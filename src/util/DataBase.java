/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Raul
 */
public class DataBase {
    public Connection getConexion()
    {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println(DataBase.class.getName());
        }
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:bd_exergia.db");
        } catch (SQLException e) {
            System.out.println(DataBase.class.getName());
        }
        
        return conn;
    }
}
