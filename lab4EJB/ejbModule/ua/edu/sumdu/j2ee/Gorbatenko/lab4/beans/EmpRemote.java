package ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBObject;

public interface EmpRemote extends EJBObject {
    /**
     * @return the empno
     */
    public int getEmpno() throws RemoteException;

    /**
     * @param empno the empno to set
     */
    public void setEmpno(int empno) throws RemoteException;

    /**
     * @return the ename
     */
    public String getEname() throws RemoteException;

    /**
     * @param ename the ename to set
     */
    public void setEname(String ename) throws RemoteException;

    /**
     * @return the job
     */
    public String getJob() throws RemoteException;

    /**
     * @param job the job to set
     */
    public void setJob(String job) throws RemoteException;

    /**
     * @return the mgr
     */
    public int getMgr() throws RemoteException;

    /**
     * @param mgr the mgr to set
     */
    public void setMgr(int mgr) throws RemoteException;

    /**
     * @return the hiredate
     */
    public Date getHiredate() throws RemoteException;

    /**
     * @param hiredate the hiredate to set
     */
    public void setHiredate(Date hiredate) throws RemoteException;

    /**
     * @return the sal
     */
    public double getSal() throws RemoteException;

    /**
     * @param sal the sal to set
     */
    public void setSal(double sal) throws RemoteException;

    /**
     * @return the comm
     */
    public double getComm() throws RemoteException;

    /**
     * @param comm the comm to set
     */
    public void setComm(double comm) throws RemoteException;

    /**
     * @return the deptno
     */
    public int getDeptno() throws RemoteException;

    /**
     * @param deptno the deptno to set
     */
    public void setDeptno(int deptno) throws RemoteException;

}
