/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 *
 *
 * @author Radinugroho
 *
 */
public class DatabaseConnection {

    private static Connection Connect;

    /**
     *
     * @return @throws java.lang.Exception
     */
    public static Connection getConnection() throws Exception {
        String pathAuthentication;
        String getUsername;
        String getPassword;
        String integratedSecurity;
        String username;
        String password;

        File connectionPath = new File("src\\lib\\Authentication.ini");

        try (BufferedReader data = new BufferedReader(new FileReader(connectionPath))) {
            pathAuthentication = data.readLine();
            getUsername = data.readLine();
            getPassword = data.readLine();
            username = getUsername.substring(getUsername.indexOf("=") + 1, getUsername.length()).trim();
            password = getPassword.substring(getPassword.indexOf("=") + 1, getPassword.length()).trim();
        }

        if (username == "") {
            integratedSecurity = "true";
        } else {
            integratedSecurity = "false";
        }

        try {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driver);
            //System.out.println("Founded Class Driver");

            try {
                if (username == "") {
                    Connect = DriverManager.getConnection(pathAuthentication + ";integratedSecurity=" + integratedSecurity);
                    //System.out.println("Succesfully Database Connection : Windows Authentication");
                } else {
                    Connect = DriverManager.getConnection(pathAuthentication + ";integratedSecurity=" + integratedSecurity, username, password);
                    //System.out.println("Succesfully Database Connection : SQL Server Authentication");
                }

            } catch (SQLException se) {
                System.out.println("SQL Server Authentication : Failed Database Connection, error message:" + se);
                System.exit(0);
            }
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class Not Found, error message: " + cnfe);
            System.exit(0);
        }
        return Connect;
    }
}
