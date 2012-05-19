package ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;


public interface Dept extends EJBObject {
    
    /**
     * @return the deptno
     */
    public Integer getDeptno() throws RemoteException;

    /**
     * @param deptno the deptno to set
     */
    public void setDeptno(Integer deptno) throws RemoteException;

    /**
     * @return the dname
     */
    public String getDname() throws RemoteException;

    /**
     * @param dname the dname to set
     */
    public void setDname(String dname) throws RemoteException;

    /**
     * @return the loc
     */
    public String getLoc() throws RemoteException;

    /**
     * @param loc the loc to set
     */
    public void setLoc(String loc) throws RemoteException;
}
