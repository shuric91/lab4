package ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils;

import java.sql.*;
import java.util.Locale;

import javax.ejb.EntityContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.SourceException;

public class DBConnector {
    
    
    private static Connection connection = null;
    private static DataSource ds = null;
    
    private DBConnector() {
        
    }
    
    public static Connection getConnection() throws SourceException {
        try {
            if(connection == null) {
                Locale.setDefault(Locale.ENGLISH);
                Context cont = (Context) new InitialContext();
                DataSource ds = (DataSource) cont.lookup("java:OracleDS");
                connection = ds.getConnection();
            }
        } catch (SQLException sqle) {
            throw new SourceException("There are some problems with connection to database" + sqle.getMessage(), sqle);
        } catch(NamingException ne) {
            throw new SourceException("Cannot find Datasource for database" + ne.getMessage(), ne);
        }
        return connection;
    }
    
    public static Connection getConnection(EntityContext context) {
        try {
            if(ds == null) {
                ds = (DataSource) context.lookup("java:OracleDS");
            }
            return ds.getConnection();
        
        } catch (SQLException sqle) {
            throw new SourceException("There are some problems with connection to database" + sqle.getMessage(), sqle);
        }
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
    
    public static void closeConnection(Connection con) throws SourceException {
        try {
            if(con != null)
                con.close();
        } catch (SQLException sqle) {
            throw new SourceException("There are some problem with closing connection: " + sqle.getMessage(), sqle);
        }
    }
    
    public static void closeConnection(Statement stmt, Connection con) throws SourceException {
        try {
            if(stmt != null)
                stmt.close();
        } catch (SQLException sqle) {
            throw new SourceException("There are some problem with closing statement: " + sqle.getMessage(), sqle);
        } finally {
            closeConnection(con);
        }
    }
    
    public static void closeConnection(ResultSet rs, Statement stmt, Connection con) throws SourceException {
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException sqle) {
            throw new SourceException("There are some problem with closing resulSet: " + sqle.getMessage(), sqle);
        } finally {
            closeConnection(stmt, con);
        }
    }
}
