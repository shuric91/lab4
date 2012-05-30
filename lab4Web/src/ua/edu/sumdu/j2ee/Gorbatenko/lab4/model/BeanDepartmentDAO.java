package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.DAOException;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans.DeptHome;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans.Dept;

public class BeanDepartmentDAO implements EntityDAO {

    private DeptHome dept;
    
    public static final String BEAN_PROBLEM = "There are some problems caused by DeptBean: ";
    
    private Department remoteToDep(Dept remote) throws RemoteException {
        Department dep = new Department();
        dep.setDeptno(remote.getDeptno());
        dep.setDname(remote.getDname());
        dep.setLoc(remote.getLoc());
        return dep;
    }
    
    public BeanDepartmentDAO() throws DAOException {
        try {
            Properties prop = new Properties();
            prop.load(getClass().getResourceAsStream("/res/jndi.properties"));

            Context context = (Context) new InitialContext(prop);
            Object obj = context.lookup("DeptBean");
            dept = (DeptHome)  PortableRemoteObject.narrow(obj, DeptHome.class);
        } catch (NamingException ne) {
            throw new DAOException("Can not lookup Dept Bean: " + ne.getMessage(), ne);
        } catch (IOException ioe) {
            throw new DAOException("Can not load property file " + ioe.getMessage(), ioe);
        }
    }
    
    @Override
    public int insert(Object obj) throws DAOException, Exception {
        if(!Department.class.equals(obj.getClass())) {
            return 0;
        } else {
            try {
                Department department = (Department)obj;
                dept.create(department.getDname(), department.getLoc());
            } catch (EJBException ejbe) {
                throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
            } catch (CreateException ce) {
                throw new DAOException("There are some problems with creating Department: " + ce.getMessage(), ce);
            }
        }
        return 0;
    }

    @Override
    public boolean delete(int id) throws DAOException, Exception {
        try {
            dept.remove(id);
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (RemoveException re) {
            throw new DAOException("There are some problems with removing Department: " + re.getMessage(), re);
        }
        return true;
    }

    @Override
    public Object find(int id) throws DAOException, Exception {
        Department department;
        try {
            Dept deptRemote = dept.findByPrimaryKey(id);
            department = remoteToDep(deptRemote);
        } catch(EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (FinderException fe) {
            throw new DAOException("There are some problems with finding Department: " + fe.getMessage(), fe);
        }
        return department;
    }

    @Override
    public boolean update(Object oldObj, Object newObj) throws DAOException,
            Exception {
        Department oldDept = null;
        Department newDept = null;
        
        if(!Department.class.equals(oldObj.getClass()) &&
                !Department.class.equals(newObj.getClass())) {
            return false;
        } else {
            oldDept = (Department) oldObj;
            newDept = (Department) newObj;
        }
        
        try {
            Dept oldDeptRemote = dept.findByPrimaryKey(oldDept.getDeptno());
            oldDeptRemote.setDname(newDept.getDname());
            oldDeptRemote.setLoc(newDept.getLoc());
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        }
        
        return true;
    }

    @Override
    public List select(int type, int id) throws DAOException, Exception {
        Collection<Dept> deptRemoteList;
        List<Department> deptList = new ArrayList<Department>();
        try {
            deptRemoteList = dept.findAll();
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (FinderException fe) {
            throw new DAOException("There are some problems with finding Departments: " + fe.getMessage(), fe);
        }
        for(Dept deptRemote: deptRemoteList) {
            deptList.add(remoteToDep(deptRemote));
        }
        return deptList;
    }

    @Override
    public List search(List<String> parameters) throws DAOException,
            Exception {
        Collection<Dept> deptRemoteList;
        List<Department> deptList;
        try {
            deptRemoteList = dept.findByNameAndLocation(parameters.get(0), parameters.get(1));
            deptList = new ArrayList<Department>();
        } catch (EJBException ejbe) {
            throw new DAOException(BEAN_PROBLEM + ejbe.getMessage(), ejbe);
        } catch (FinderException fe) {
            throw new DAOException("There are some problems with finding Departments: " + fe.getMessage(), fe);
        }
        
        for(Dept deptRemote: deptRemoteList) {
            deptList.add(remoteToDep(deptRemote));
        }
        return deptList;
    }
}
