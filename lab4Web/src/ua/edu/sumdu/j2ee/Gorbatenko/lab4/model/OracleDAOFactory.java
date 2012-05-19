package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.SourceException;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.DBConnector;


/**
 *
 * @author Shuric
 */
public class OracleDAOFactory extends DAOFactory {
    private java.sql.Connection conn;
    
    public OracleDAOFactory() throws SourceException {
        conn = DBConnector.getConnection();
    }
    
    public EntityDAO getEntityDAO(String entityType) {    
        if(EntityDAO.EMPLOYEE_ENTITY.equals(entityType)) {
            return new OracleEmployeeDAO(conn);
        } else if(EntityDAO.DEPARTMENT_ENTITY.equals(entityType)) {
            return new OracleDepartmentDAO(conn);
        } else {
            return null;
        }
    }
}
