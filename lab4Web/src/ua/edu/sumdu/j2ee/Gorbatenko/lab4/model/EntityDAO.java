package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import java.util.List;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.DAOException;;

/**
 *
 * @author Shuric
 */
public interface EntityDAO {
    
    public static final String EMPLOYEE_ENTITY = "employee";
    public static final String DEPARTMENT_ENTITY = "department";
    
    public int insert(Object obj) throws DAOException, Exception;
    
    public boolean delete(int id) throws DAOException, Exception;
    
    public Object find(int id) throws DAOException, Exception;
    
    public boolean update(Object oldObj, Object newObj) throws DAOException, Exception;
    
    public java.util.List<Object> select(int type, int id) throws DAOException, Exception;
    
    public List<Object> search(List<String> parameters) throws DAOException, Exception;
}
