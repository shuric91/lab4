package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

public class BeansDAOFactory extends DAOFactory {
    
    public EntityDAO getEntityDAO(String entityType) {    
        if(EntityDAO.EMPLOYEE_ENTITY.equals(entityType)) {
            return new BeanEmployeeDAO();
        } else if(EntityDAO.DEPARTMENT_ENTITY.equals(entityType)) {
            return new BeanDepartmentDAO();
        } else {
            return null;
        }
    }
}
