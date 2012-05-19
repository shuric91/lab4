package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;
import java.sql.*;
import java.util.*;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.DAOException;
/**
 *
 * @author Shuric
 */
public class OracleEmployeeDAO implements EntityDAO {
    
    Connection connection;
    
    private void getEmpFromResult(Employee emp, ResultSet rs) throws SQLException {
        emp.setComm(rs.getDouble("comm"));
        emp.setDeptno(rs.getInt("deptno"));
        emp.setEmpno(rs.getInt("empno"));
        emp.setEname(rs.getString("ename"));
        emp.setHiredate(new java.util.Date(rs.getDate("hiredate").getTime()));
        emp.setJob(rs.getString("job"));
        emp.setMgrName(((Employee)find(rs.getInt("mgr"))).getEname());
        emp.setMgr(rs.getInt("mgr"));
        emp.setSal(rs.getDouble("sal"));
    }
    
    public OracleEmployeeDAO(Connection conn) {
        connection = conn;
    }
    
    @Override
    public int insert(Object obj) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        String sql = "INSERT INTO emp VALUES(emp_id.nextval, ?, ?, ?, ?, ?, ?, ?)";
        Employee newEmp = null;
        
        
        if(!Employee.class.equals(obj.getClass())) {
            return 0;
        } else {
            newEmp = (Employee)obj;
        }
        
        try {
            stmt = connection.prepareStatement(sql.toString());
            
            stmt.setString(1, newEmp.getEname());
            stmt.setString(2, newEmp.getJob());
            stmt.setInt(3, newEmp.getMgr());
            stmt.setDate(4, new java.sql.Date(newEmp.getHiredate().getTime()));
            stmt.setDouble(5, newEmp.getSal());
            stmt.setDouble(6, newEmp.getComm());
            stmt.setInt(7, newEmp.getDeptno());

            int id = 0;
            
            if(stmt.execute()) {
                stmt = connection.prepareStatement("select emp_id.curval into id from dual");
                res = stmt.executeQuery();
                if(res.next()) {
                    id = res.getInt(1);
                }
            }
            return id;
        } catch(SQLException sqle) {
            throw new DAOException("Can not insert Employee into database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not close statement:" + sqle.getMessage(), sqle);
            }
        }
    }
    
    @Override
    public boolean delete(int id) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM emp WHERE empno = ?");
            stmt.setInt(1, id);
            return stmt.execute();
        } catch(SQLException sqle) {
            throw new DAOException("Can not delete Employee from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not close statement:" + sqle.getMessage(), sqle);
            }
        }
    }
    
    @Override
    public Object find(int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        Employee emp = new Employee();
        try {
            stmt = connection.prepareStatement("SELECT * " + 
                    "FROM emp where empno = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            
            if(res.next()) {
                getEmpFromResult(emp, res);
            }
        } catch(SQLException sqle) {
            throw new DAOException("Can not find Employee into database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not close statement:" + sqle.getMessage(), sqle);
            } finally {
                try { 
                    if(res != null)
                        res.close();
                } catch(SQLException sqle) {
                    throw new DAOException("Can not close resultset:" + sqle.getMessage(), sqle);
                }
            }
        }
        return emp;
    }
    
    
    @Override
    public boolean update(Object oldObj, Object newObj) throws SQLException {
        PreparedStatement stmt = null;
        StringBuilder sql = new StringBuilder("UPDATE emp SET ");
        sql.append("empno = ?, ename = ?, job = ?, mgr = ?, hiredate = ?, ").
        append("sal = ?, comm = ?, deptno = ? ").
        append("WHERE empno = ?");
        
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
            stmt = connection.prepareStatement(sql.toString());
            
            stmt.setInt(1, newEmp.getEmpno());
            stmt.setString(2, newEmp.getEname());
            stmt.setString(3, newEmp.getJob());
            stmt.setInt(4, newEmp.getMgr());
            stmt.setDate(5, new java.sql.Date(newEmp.getHiredate().getTime()));
            stmt.setDouble(6, newEmp.getSal());
            stmt.setDouble(7, newEmp.getComm());
            stmt.setInt(8, newEmp.getDeptno());
                
            stmt.setInt(9, oldEmp.getEmpno());
                
            return stmt.execute();
        } catch(SQLException sqle) {
            throw new DAOException("Can not update Employee into database:" + sqle.getMessage(), sqle);
        } finally {
            try { 
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not close resultset:" + sqle.getMessage(), sqle);
            }
        }
    }
    
  
    @Override
    public List select(int type, int id) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<Employee> empList = new LinkedList<Employee>();
        String query = "";
        if(type == 0) {
            query = "SELECT * FROM emp where deptno=?";
        } else if(type == 1) {
            query = "SELECT * FROM emp where mgr=?";
        } else if(type == 2) {
            query = "SELECT * FROM emp    START WITH empno = ? CONNECT BY PRIOR mgr = empno";
        } else if(type == 3) {
            query = "SELECT * FROM emp where job = 'MANAGER' or job = 'PRESIDENT'";
        }
        try {
            stmt = connection.prepareStatement(query);
            if(type != 3) {
                stmt.setInt(1, id);
            }
            res = stmt.executeQuery();
            
            while(res.next()) {
                Employee emp = new Employee();
                getEmpFromResult(emp, res);
                empList.add(emp);
            }
            
       
            
        } catch(SQLException sqle) {
            throw new DAOException("Can not select Employees from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not close statement:" + sqle.getMessage(), sqle);
            } finally {
                try { 
                    if(res != null)
                        res.close();
                } catch(SQLException sqle) {
                    throw new DAOException("Can not close resultset:" + sqle.getMessage(), sqle);
                }
            }
        }
        return empList;
    }
    
    public List search(List<String> parameters) throws SQLException {
        List<Employee> emps = new ArrayList<Employee>();
        PreparedStatement stmt = null;
        ResultSet res = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM emp ");
        String empno = parameters.get(0);
        String ename = parameters.get(1);
        
        if(parameters != null) {
            sql.append("where ");
            if(empno != null) {
                sql.append("to_char(empno) like ? ");
            }
            if(ename != null) {
                sql.append("and ename like upper(?)");
            }
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
                Employee emp = new Employee();
                getEmpFromResult(emp, res);
                emps.add(emp);
            }
        } catch(SQLException sqle) {
            throw new DAOException("Can not search Employees from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not close statement:" + sqle.getMessage(), sqle);
            } finally {
                try { 
                    if(res != null)
                        res.close();
                } catch(SQLException sqle) {
                    throw new DAOException("Can not close resultset:" + sqle.getMessage(), sqle);
                }
            }
        }
        return emps;
    }
}
