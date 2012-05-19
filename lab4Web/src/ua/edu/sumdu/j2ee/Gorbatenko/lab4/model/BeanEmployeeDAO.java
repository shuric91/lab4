package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import java.rmi.RemoteException;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.DAOException;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans.Dept;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans.Emp;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans.EmpHome;

public class BeanEmployeeDAO implements EntityDAO {

    private EmpHome emp;
    
    Context context;

    public static final String BEAN_PROBLEM = "There are some problems caused by EmpBean: ";

    private Employee remoteToEmp(Emp remote)
            throws RemoteException {
        Employee emp = new Employee();
        emp.setEmpno(remote.getEmpno());
        emp.setEname(remote.getEname());
        emp.setJob(remote.getJob());
        emp.setHiredate(remote.getHiredate());
        emp.setComm(remote.getComm());
        emp.setSal(remote.getSal());
        emp.setDeptno(remote.getDeptno());
        emp.setMgr(remote.getMgr());
        emp.setMgrName(remote.getMgrName());
        return emp;
    }

    public BeanEmployeeDAO() throws DAOException {
        try {
            java.util.Hashtable<String, String> environment = new java.util.Hashtable<String, String>();

            environment.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            environment.put(Context.URL_PKG_PREFIXES,
                    "org.jboss.naming:org.jnp.interfaces");
            environment.put(Context.PROVIDER_URL, "jnp://localhost:1099");

            context = (Context) new InitialContext(environment);
            Object obj = context.lookup("EmpBean");
            emp = (EmpHome) PortableRemoteObject.narrow(obj, EmpHome.class);
        } catch (NamingException ne) {
            throw new DAOException("Can not lookup Emp Bean" + ne.getMessage(),
                    ne);
        }
    }

    @Override
    public int insert(Object obj) throws DAOException, Exception {
        if (!Employee.class.equals(obj.getClass())) {
            return 0;
        } else {
            try {
                Employee employee = (Employee) obj;
                emp.create(employee.getEname(), employee.getJob(),
                        employee.getMgr(), employee.getHiredate(),
                        employee.getSal(), employee.getComm(),
                        employee.getDeptno());
            } catch (EJBException ejbe) {
                throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
            } catch (CreateException ce) {
                throw new DAOException(
                        "There are some problems with creating Department: "
                                + ce.getMessage(), ce);
            }
        }
        return 1;
    }

    @Override
    public boolean delete(int id) throws DAOException, Exception {
        try {
            Emp empRemote = emp.findByPrimaryKey(id);
            empRemote.remove();
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (RemoveException re) {
            throw new DAOException("There are some problems with removing Employee: " + re.getMessage(), re);
        }
        return true;
    }

    @Override
    public Object find(int id) throws DAOException, Exception {
        Employee employee = null;
        
        try {
            employee = remoteToEmp(emp.findByPrimaryKey(id));
        } catch(EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (FinderException fe) {
            throw new DAOException("There are some problems with finding Department: " + fe.getMessage(), fe);
        }

        return employee;
    }

    @Override
    public boolean update(Object oldObj, Object newObj) throws DAOException,
            Exception {
        Employee oldEmp = null;
        Employee newEmp = null;
        
        if(!Employee.class.equals(oldObj.getClass()) &&
                !Employee.class.equals(newObj.getClass())) {
            return false;
        } else {
            oldEmp = (Employee) oldObj;
            newEmp = (Employee) newObj;
        }
        
        try {
            Emp oldEmpRemote = emp.findByPrimaryKey(oldEmp.getDeptno());
            oldEmpRemote.setComm(newEmp.getComm());
            oldEmpRemote.setDeptno(newEmp.getDeptno());
            oldEmpRemote.setEmpno(newEmp.getEmpno());
            oldEmpRemote.setEname(newEmp.getEname());
            oldEmpRemote.setHiredate(newEmp.getHiredate());
            oldEmpRemote.setJob(newEmp.getJob());
            oldEmpRemote.setMgr(newEmp.getMgr());
            oldEmpRemote.setMgrName(newEmp.getMgrName());
            oldEmpRemote.setSal(newEmp.getSal());
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        }
        
        return true;
    }

    @Override
    public List select(int type, int id) throws DAOException, Exception {
        List<Emp> empRemoteList = null;
        List<Employee> empList = new LinkedList<Employee>();
        try {
            switch (type) {
            case 0:
                empRemoteList = emp.findByDeptno(id);
                break;
            case 1:
                empRemoteList = emp.findByManager(id);
                break;
            case 2:
                empRemoteList = emp.findHierarchically(id);
                break;
            case 3: 
                empRemoteList = emp.findAllManagers();
                break;
            }
            for (Emp empRemote : empRemoteList) {
                empList.add(remoteToEmp(empRemote));
            }
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (FinderException fe) {
            throw new DAOException(
                    "There are some problems with finding Departments: "
                            + fe.getMessage(), fe);
        }

        return empList;
    }

    @Override
    public List search(List<String> parameters) throws DAOException,
            Exception {
        Collection<Emp> empRemoteList;
        List<Employee> empList;
        try {
            empRemoteList = emp.findByNameAndID(parameters.get(0), parameters.get(1));
            empList = new ArrayList<Employee>();
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (FinderException fe) {
            throw new DAOException("There are some problems with finding Employees: " + fe.getMessage(), fe);
        }
        
        for(Emp empRemote: empRemoteList) {
            empList.add(remoteToEmp(empRemote));
        }
        return empList;
    }

}
