package ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans;

import java.rmi.RemoteException;

import javax.ejb.*;

public interface DeptHome extends EJBHome {

    public Dept create(String dName, String location)
            throws RemoteException, CreateException;

    public Dept findByPrimaryKey(Integer deptno) throws RemoteException,
            FinderException;

    public java.util.Collection<Dept> findAll() throws RemoteException,
            FinderException;

    public java.util.Collection<Dept> findByNameAndLocation(String dname, String location)
            throws RemoteException, FinderException;
}
