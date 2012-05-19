package ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils;

import java.sql.*;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.SourceException;

public class DBConnector {
    
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SourceException {
        try {
            if(connection == null) {
                Locale.setDefault(Locale.ENGLISH);
                   
                Context cont = (Context) new InitialContext().lookup("java:/comp/env");
                DataSource ds = (DataSource) cont.lookup("jdbc/oracle");
                connection = ds.getConnection();
                connection.setAutoCommit(true);
            }
        } catch (SQLException sqle) {
            throw new SourceException("There are some problems with connection to database" + sqle.getMessage(), sqle);
        } catch(NamingException ne) {
            throw new SourceException("Cannot find Datasource for database" + ne.getMessage(), ne);
        }
        return connection;
    }
    
    public static void closeConnection() throws SourceException {
        try {
            if(connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException sqle) {
            throw new SourceException("Cannot close database connection" + sqle.getMessage(), sqle);
        }
    }
}
