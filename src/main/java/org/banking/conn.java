package org.banking;
import java.sql.*;
public class conn {
    static Connection con; // Global Connection Object
    public static Connection getConnection() {
        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver"; //jdbc driver
            Class.forName(mysqlJDBCDriver);
            String url = "jdbc:mysql://localhost:3306/banking"; //mysql url
            String user = "root";	 //mysql username
            String pass = "2408"; //mysql passcode
            con = DriverManager.getConnection(url, user, pass);
        }
        catch (Exception e) {
            System.out.println("Connection Failed!");
        }

        return con;
    }
}