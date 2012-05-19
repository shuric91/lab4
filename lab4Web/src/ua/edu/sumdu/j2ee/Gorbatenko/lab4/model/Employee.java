package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import java.util.Date;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.WrongDataException;
/**
 *
 * @author Shuric
 */
public class Employee {
    private int empno;
    private String ename;
    private String job;
    private int mgr;
    private String mgrName;
    private Date hiredate;
    private double sal;
    private double comm;
    private int deptno;

    /**
     * @return the empno
     */
    public int getEmpno() {
        return empno;
    }

    /**
     * @param empno the empno to set
     */
    public void setEmpno(int empno) {
        this.empno = empno;
    }

    /**
     * @return the ename
     */
    public String getEname() {
        return ename;
    }

    /**
     * @param ename the ename to set
     */
    public void setEname(String ename) throws WrongDataException {
        if(ename == null) {
            throw new WrongDataException("Employee name can not be empty");
        }
        this.ename = ename;
    }

    /**
     * @return the job
     */
    public String getJob() {
        return job;
    }

    /**
     * @param job the job to set
     */
    public void setJob(String job) throws WrongDataException {
        if(job == null) {
            throw new WrongDataException("Employee job can not be empty");
        }
        this.job = job;
    }

    /**
     * @return the mgr
     */
    public int getMgr() {
        return mgr;
    }

    /**
     * @param mgr the mgr to set
     */
    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    /**
     * @return the hiredate
     */
    public Date getHiredate() {
        return hiredate;
    }

    /**
     * @param hiredate the hiredate to set
     */
    public void setHiredate(Date hiredate) throws WrongDataException {
        if(hiredate == null) {
            throw new WrongDataException("Employee hiredate can not be empty");
        }
        this.hiredate = hiredate;
    }

    /**
     * @return the sal
     */
    public double getSal() {
        return sal;
    }

    /**
     * @param sal the sal to set
     */
    public void setSal(double sal) {
        this.sal = sal;
    }

    /**
     * @return the comm
     */
    public double getComm() {
        return comm;
    }

    /**
     * @param comm the comm to set
     */
    public void setComm(double comm) {
        this.comm = comm;
    }

    /**
     * @return the deptno
     */
    public int getDeptno() {
        return deptno;
    }

    /**
     * @param deptno the deptno to set
     */
    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    /**
     * @param mgrName the mgrName to set
     */
    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    /**
     * @return the mgrName
     */
    public String getMgrName() {
        return mgrName;
    }
}
