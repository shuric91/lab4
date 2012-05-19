package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.SourceException;

/**
 *
 * @author Shuric
 */
public abstract class DAOFactory {
    
    public abstract EntityDAO getEntityDAO(String type);
    
    public static DAOFactory getDAOFactory(String type) throws SourceException {
        if("oracle_db".equals(type)) {
            return new OracleDAOFactory();
        } else if("entity_beans".equals(type)) {
            return new BeansDAOFactory();
        } else {
            return null;
        }
    }
}
