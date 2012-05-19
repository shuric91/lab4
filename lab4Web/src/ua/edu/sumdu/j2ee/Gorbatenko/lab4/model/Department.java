package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.WrongDataException;

/**
 *
 * @author Shuric
 */
public class Department {
    
    private int deptno;
    private String dname;
    private String loc;

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
     * @return the dname
     */
    public String getDname() {
        return dname;
    }

    /**
     * @param dname the dname to set
     */
    public void setDname(String dname) throws WrongDataException {
        if(dname == null) {
            throw new WrongDataException("Department name can not be empty");
        }
        this.dname = dname;
    }

    /**
     * @return the loc
     */
    public String getLoc() {
        return loc;
    }

    /**
     * @param loc the loc to set
     */
    public void setLoc(String loc) throws WrongDataException {
        if(dname == null) {
            throw new WrongDataException("Department location can not be null");
        }
        this.loc = loc;
    }
}
