package ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface EmpHome extends EJBHome {

    public Emp create(String empno, String job, Integer mgr, java.util.Date hiredate, Double salgrade, 
            Double commission, Integer deptno) throws RemoteException, CreateException;
    
    public Emp findByPrimaryKey(Integer empno) throws RemoteException, FinderException;
    
    public java.util.List<Emp> findAll() throws RemoteException, FinderException;
    
    public java.util.List<Emp> findByDeptno(Integer deptno) throws RemoteException, FinderException;
    
    public java.util.List<Emp> findByManager(Integer mgrID) throws RemoteException, FinderException;
    
    public java.util.List<Emp> findHierarchically(Integer empno) throws RemoteException, FinderException;
    
    public java.util.List<Emp> findAllManagers() throws RemoteException, FinderException;
    
    public java.util.List<Emp> findByNameAndID(String empno, String ename)
            throws RemoteException, FinderException;
}
