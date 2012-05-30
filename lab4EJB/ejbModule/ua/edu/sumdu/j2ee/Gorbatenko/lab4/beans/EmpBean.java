package ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;


import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.SourceException;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.DBConnector;

public class EmpBean implements EntityBean {

    private Integer empno;
    private String ename;
    private String job;
    private int mgr;
    private String mgrName;
    private java.util.Date hiredate;
    private double sal;
    private double comm;
    private int deptno;
    
    private EntityContext context;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String INSERTION_QUERY = "INSERT INTO emp VALUES(emp_id.nextval, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_EMPNO_QUERY = "SELECT * FROM emp WHERE  empno=?";
    private static final String SELECT_QUERY = "SELECT * FROM emp";
    private static final String SELECT_BY_DEPTNO_QUERY = "SELECT empno FROM emp where deptno=?";
    private static final String SELECT_BY_MANAGER_QUERY = "SELECT * FROM emp where mgr=?";
    private static final String SELECT_HIRERARCHY_QUERY = "SELECT * FROM emp START WITH empno = ? CONNECT BY NOCYCLE PRIOR mgr = empno";
    private static final String SELECT_ALL_MANAGERS_QUERY = "SELECT * FROM emp where job = 'MANAGER' or job = 'PRESIDENT'";
    private static final String SELECT_SEQUENCE_CURVAL = "select emp_id.curval into id from dual";
    private static final String DELETION_QUERY = "DELETE FROM emp WHERE empno = ?";
    private static final String UPDATING_QUERY = "UPDATE emp SET ename = ?, job = ?, mgr = ?, hiredate = ?, sal = ?, comm = ?, deptno = ? WHERE empno = ?";
    
    private static final String INSERTION_PROBLEM = "Can not insert Employee into database: ";
    private static final String SELECTION_PROBLEM = "Can not select Employees from database: ";
    private static final String DELETION_PROBLEM = "Can not delete Employee from database: ";
    private static final String UPDATING_PROBLEM = "Can not update Employee into database: ";
    private static final String FINDING_EMPNO_PROBLEM = "Can not find Employee with empno ";
    private static final String FINDING_PROBLEM = "Can not find Employee in database: ";
    private static final String SEARCHING_PROBLEM = "Can not search Employees from database:";
    private static final String DB_PROBLEM = "There are some problem with db: ";
    /**
     * @param ename
     * the ename to set
     */
    public void setEname(String ename) {
        this.ename = ename;
    }

    /**
     * @return the ename
     */
    public String getEname() {
        return ename;
    }

    /**
     * @param empno
     *            the empno to set
     */
    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    /**
     * @return the empno
     */
    public Integer getEmpno() {
        return empno;
    }

    /**
     * @param job
     *            the job to set
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * @return the job
     */
    public String getJob() {
        return job;
    }

    /**
     * @param mgr
     *            the mgr to set
     */
    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    /**
     * @return the mgr
     */
    public int getMgr() {
        return mgr;
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

    /**
     * @param hiredate
     *            the hiredate to set
     */
    public void setHiredate(java.util.Date hiredate) {
        this.hiredate = hiredate;
    }

    /**
     * @return the hiredate
     */
    public java.util.Date getHiredate() {
        return hiredate;
    }

    /**
     * @param sal
     *            the sal to set
     */
    public void setSal(double sal) {
        this.sal = sal;
    }

    /**
     * @return the sal
     */
    public double getSal() {
        return sal;
    }

    /**
     * @param comm
     *            the comm to set
     */
    public void setComm(double comm) {
        this.comm = comm;
    }

    /**
     * @return the comm
     */
    public double getComm() {
        return comm;
    }
    

    /**
     * @param deptno the deptno to set
     */
    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    /**
     * @return the deptno
     */
    public int getDeptno() {
        return deptno;
    }

    public Integer ejbCreate(String ename, String job, Integer mgr, java.util.Date hiredate, Double salgrade, 
            Double commission, Integer deptno) throws CreateException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            stmt = connection.prepareStatement(INSERTION_QUERY);
            
            stmt.setString(1, ename);
            stmt.setString(2, job);
            stmt.setInt(3, mgr);
            stmt.setDate(4, new java.sql.Date(hiredate.getTime()));
            stmt.setDouble(5, salgrade);
            stmt.setDouble(6, commission);
            stmt.setInt(7, deptno);

            int id = 0;
            
            if(stmt.execute()) {
                stmt = connection.prepareStatement(SELECT_SEQUENCE_CURVAL);
                res = stmt.executeQuery();
                if(res.next()) {
                    id = res.getInt(1);
                }
            }
            
            setComm(commission);
            setDeptno(deptno);
            setEmpno(id);
            setEname(ename);
            setHiredate(hiredate);
            setJob(job);
            setMgr(mgr);
            setSal(salgrade);
            return id;
        } catch(SQLException sqle) {
            throw new EJBException(INSERTION_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
    }

    public void ejbPostCreate(String ename, String job, Integer mgr, java.util.Date hiredate, Double salgrade, 
            Double commission, Integer deptno) throws  CreateException {

    }

    @Override
    public void ejbActivate() throws EJBException {
        empno = (Integer) context.getPrimaryKey();
    }

    @Override
    public void ejbPassivate() throws EJBException {
        empno = null;
    }

    @Override
    public void ejbLoad() throws EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        
        try {
            stmt = connection.prepareStatement(SELECT_BY_EMPNO_QUERY);
            stmt.setInt(1, empno);
            
            res = stmt.executeQuery();
            
            if(res.next()) {
                ename = res.getString("ename");
                job = res.getString("job");
                mgr = res.getInt("mgr");
                hiredate = res.getDate("hiredate");
                sal = res.getDouble("sal");
                comm = res.getDouble("comm");
                setDeptno(res.getInt("deptno"));
            }
            
            if(mgr != 0) {
                stmt = connection.prepareStatement(SELECT_BY_EMPNO_QUERY);
                stmt.setInt(1, mgr);
                res = stmt.executeQuery();
                if(res.next()) {
                    setMgrName(res.getString("ename"));
                } else {
                    setMgrName("-");
                }
            } else {
                setMgr(7839);
                setMgrName("-");
            }
            
        } catch(SQLException sqle) {
            throw new EJBException(SELECTION_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
    }

    @Override
    public void ejbRemove() throws RemoveException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(DELETION_QUERY);
            stmt.setInt(1, empno);
            stmt.execute();
        } catch(SQLException sqle) {
            throw new EJBException(DELETION_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
    }

    @Override
    public void ejbStore() throws EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        
        try {
            stmt = connection.prepareStatement(UPDATING_QUERY);
            
            stmt.setString(1, getEname());
            stmt.setString(2, getJob());
            stmt.setInt(3, getMgr());
            stmt.setDate(4, new java.sql.Date(getHiredate().getTime()));
            stmt.setDouble(5, getSal());
            stmt.setDouble(6, getComm());
            stmt.setInt(7, getDeptno());
            
            stmt.setInt(8, getEmpno());
            
            stmt.execute();
        } catch(SQLException sqle) {
            throw new EJBException(UPDATING_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
    }

    @Override
    public void setEntityContext(EntityContext entityContext) throws EJBException {
        this.context = entityContext;
    }

    @Override
    public void unsetEntityContext() throws EJBException {
        this.context = null;
    }
    
    public Integer ejbFindByPrimaryKey(Integer empno) throws EJBException, FinderException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        try {
            stmt = connection.prepareStatement(SELECT_BY_EMPNO_QUERY);
            stmt.setInt(1, empno);
            res = stmt.executeQuery();
            
            if(res.next()) {
                return empno;
            } else {
                throw new FinderException(FINDING_EMPNO_PROBLEM  + empno);
            }
        } catch(SQLException sqle) {
            throw new EJBException(FINDING_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
    }
    
    public List<Integer> ejbFindAll() throws EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<Integer> emps = new ArrayList<Integer>();
        
        try {
            stmt = connection.prepareStatement(SELECT_QUERY);
            res = stmt.executeQuery();
            
            while(res.next()) {
                emps.add(res.getInt("empno"));
            }
        } catch(SQLException sqle) {
            throw new EJBException(FINDING_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
        
        return emps;
    }
    
    public List<Integer> ejbFindByDeptno(Integer deptno) throws EJBException, FinderException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<Integer> emps = new ArrayList<Integer>();
        
        try {
            stmt = connection.prepareStatement(SELECT_BY_DEPTNO_QUERY);
            stmt.setInt(1, deptno);
            res = stmt.executeQuery();
            
            while(res.next()) {
                emps.add(res.getInt("empno"));
            }
        } catch(SQLException sqle) {
            throw new EJBException(FINDING_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
        
        return emps;
    }
    
    public List<Integer> ejbFindByManager(Integer mgrID) throws EJBException, FinderException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<Integer> emps = new ArrayList<Integer>();
        
        try {
            stmt = connection.prepareStatement(SELECT_BY_MANAGER_QUERY);
            stmt.setInt(1, mgrID);
            res = stmt.executeQuery();
            
            while(res.next()) {
                emps.add(res.getInt("empno"));
            }
        } catch(SQLException sqle) {
            throw new EJBException(FINDING_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
        
        return emps;
    }
    
    public java.util.List<Integer> ejbFindHierarchically(Integer empno) throws EJBException, FinderException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<Integer> empList = new ArrayList<Integer>();
        
        try {
            stmt = connection.prepareStatement(SELECT_HIRERARCHY_QUERY);
            stmt.setInt(1, empno);
            
            res = stmt.executeQuery();
            
            while(res.next()) {
                empList.add(res.getInt("empno"));
            }
            
        } catch(SQLException sqle) {
            throw new EJBException(SELECTION_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
        return empList;
    }
    
    public java.util.List<Integer> ejbFindAllManagers() throws EJBException, FinderException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<Integer> empList = new LinkedList<Integer>();
        
        try {
            stmt = connection.prepareStatement(SELECT_ALL_MANAGERS_QUERY);
            
            res = stmt.executeQuery();
            
            while(res.next()) {
                empList.add(res.getInt("empno"));
            }
            
        } catch(SQLException sqle) {
            throw new EJBException(SELECTION_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
        return empList;
    }
    
    public java.util.List<Integer> ejbFindByNameAndID(String empno, String ename) throws EJBException, FinderException {
        Connection connection = DBConnector.getConnection(context);
        List<Integer> emps = new ArrayList<Integer>();
        PreparedStatement stmt = null;
        ResultSet res = null;
        StringBuilder sql = new StringBuilder("SELECT empno FROM emp ");
        
        sql.append("where ");
        if(empno != null) {
            sql.append("to_char(empno) like ? ");
            if(ename != null) {
                sql.append(" and ");
            }
        }
        if(ename != null) {
            sql.append("ename like upper(?)");
        }
        
        try {
            stmt = connection.prepareStatement(sql.toString());
            
            if(empno != null) {
                stmt.setString(1, "%" + empno + "%");
            }            
            if(ename != null) {
                stmt.setString(2, "%" + ename + "%");
            }
            
            res = stmt.executeQuery();
            
            while(res.next()) {
                emps.add(res.getInt("empno"));
            }
        } catch(SQLException sqle) {
            throw new EJBException(SEARCHING_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
        return emps;
    }
}
