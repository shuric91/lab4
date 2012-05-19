package ua.edu.sumdu.j2ee.Gorbatenko.lab4.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.*;
/**
 *
 * @author Shuric
 */
public class OracleDepartmentDAO implements EntityDAO {
    private Connection connection;
    
    private void getDeptFromResult(Department dept, ResultSet rs) throws SQLException {
        dept.setDeptno(rs.getInt("deptno"));
        dept.setDname(rs.getString("dname"));
        dept.setLoc(rs.getString("loc"));
    }
    
    public OracleDepartmentDAO(Connection conn) {
        connection = conn;
    }
    
    @Override
    public int insert(Object obj) throws DAOException {
        PreparedStatement stmt = null;
        ResultSet res = null;
        String sql = "INSERT INTO dept VALUES(dept_id.nextval, ?, ?)";
        Department newDept = null;
        
        
        if(!Department.class.equals(obj.getClass())) {
            return 0;
        } else {
            newDept = (Department)obj;
        }
        
        try {
            stmt = connection.prepareStatement(sql.toString());
            
            stmt.setString(1, newDept.getDname());
            stmt.setString(2, newDept.getLoc());

            int id = 0;
            
            if(stmt.execute()) {
                stmt = connection.prepareStatement("select dept_id.curval into id from dual");
                res = stmt.executeQuery();
                if(res.next()) {
                    id = res.getInt(1);
                }
            }
            return id;
        } catch(SQLException sqle) {
            throw new DAOException("Can not insert Department into database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not search close statement:" + sqle.getMessage(), sqle);
            } 
        }
    }
    
    @Override
    public boolean delete(int id) {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("DELETE FROM dept WHERE deptno = ?");
            stmt.setInt(1, id);
            return stmt.execute();
        } catch(SQLException sqle) {
            throw new DAOException("Can not delete Department from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not search close statement:" + sqle.getMessage(), sqle);
            } 
        }
    }
    
    @Override
    public Department find(int id) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        Department dep = new Department();
        try {
            stmt = connection.prepareStatement("SELECT * FROM dept WHERE deptno = ?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            
            if(res.next()) {
                getDeptFromResult(dep, res);
            }
        } catch(SQLException sqle) {
            throw new DAOException("Can not find Department into database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not search close statement:" + sqle.getMessage(), sqle);
            } finally {
                try { 
                    if(res != null)
                        res.close();
                } catch(SQLException sqle) {
                    throw new DAOException("Can not search close resultset:" + sqle.getMessage(), sqle);
                }
            }
        }
        return dep;
    }
    
    @Override
    public boolean update(Object oldObj, Object newObj) {
        PreparedStatement stmt = null;
        ResultSet res = null;
        StringBuilder sql = new StringBuilder("UPDATE dept SET ");
        sql.append("deptno = ?, dname = ?, loc = ? ").
        append("WHERE deptno = ?");
        
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
               stmt = connection.prepareStatement(sql.toString());
            
               stmt.setInt(1, newDept.getDeptno());
               stmt.setString(2, newDept.getDname());
               stmt.setString(3, newDept.getLoc());
                
               stmt.setInt(4, oldDept.getDeptno());
                
               return stmt.execute();
        } catch(SQLException sqle) {
            throw new DAOException("Can not update Department into database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not search close statement:" + sqle.getMessage(), sqle);
            } finally {
                try { 
                    if(res != null)
                        res.close();
                } catch(SQLException sqle) {
                    throw new DAOException("Can not search close resultset:" + sqle.getMessage(), sqle);
                }
            }
        }
    }
    
    @Override
    public List select(int type, int id) {
        Statement stmt = null;
        ResultSet res = null;
        List<Department> depList = new ArrayList<Department>();
        try {
            stmt = connection.createStatement();
            res = stmt.executeQuery("SELECT * FROM dept");
            
            while(res.next()) {
                Department dept = new Department();
                getDeptFromResult(dept, res);
                depList.add(dept);
            }
        } catch(SQLException sqle) {
            throw new DAOException("Can not select Departments from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not search close statement:" + sqle.getMessage(), sqle);
            } finally {
                try { 
                    if(res != null)
                        res.close();
                } catch(SQLException sqle) {
                    throw new DAOException("Can not search close resultset:" + sqle.getMessage(), sqle);
                }
            }
        }
        return depList;
    }
    
    public List search(List<String> parameters) {
        List<Department> depts = new ArrayList<Department>();
        PreparedStatement stmt = null;
        ResultSet res = null;
        StringBuilder sql = new StringBuilder("SELECT * FROM dept ");

        String dname = parameters.get(0);
        String loc = parameters.get(1);
        
        if(parameters != null) {
            sql.append("where ");
            if(dname != null) {
                sql.append("dname like upper(?)");
            }
            if(loc != null) {
                sql.append("and loc like upper(?)");
            }
        }
        
        try {
            stmt = connection.prepareStatement(sql.toString());
            
            if(dname != null) {
                stmt.setString(1, "%" + dname + "%");
            }
            
            if(loc != null) {
                stmt.setString(2, "%" + loc + "%");
            }
            res = stmt.executeQuery();
            
            while(res.next()) {
                Department dept = new Department();
                getDeptFromResult(dept, res);
                depts.add(dept);
            }
        } catch(SQLException sqle) {
            throw new DAOException("Can not search Departments from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException sqle) {
                throw new DAOException("Can not search close statement:" + sqle.getMessage(), sqle);
            } finally {
                try { 
                    if(res != null)
                        res.close();
                } catch(SQLException sqle) {
                    throw new DAOException("Can not search close resultset:" + sqle.getMessage(), sqle);
                }
            }
        }        
        return depts;
    }
}
