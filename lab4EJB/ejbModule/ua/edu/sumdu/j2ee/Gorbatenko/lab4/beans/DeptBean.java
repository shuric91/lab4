package ua.edu.sumdu.j2ee.Gorbatenko.lab4.beans;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.DBConnector;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.*;


public class DeptBean implements EntityBean {

    private static final long serialVersionUID = 1L;
    private Integer deptno;
    private String dname;
    private String loc;
    
    private EntityContext context;

    /**
     * @param dname
     *            the dname to set
     */
    public void setDname(String dname) {
        this.dname = dname;
    }

    /**
     * @return the dname
     */
    public String getDname() {
        return dname;
    }

    /**
     * @param deptno
     *            the deptno to set
     */
    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    /**
     * @return the deptno
     */
    public Integer getDeptno() {
        return deptno;
    }

    /**
     * @param loc
     *            the loc to set
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     * @return the loc
     */
    public String getLoc() {
        return loc;
    }

    @Override
    public void ejbLoad() throws EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        
        try {
            stmt = connection.prepareStatement("SELECT * FROM dept WHERE deptno = ?");
            stmt.setInt(1, deptno);
            res = stmt.executeQuery();
            
            if(res.next()) {
                dname = res.getString("dname");
                loc = res.getString("loc");
            }
        } catch(SQLException sqle) {
            throw new EJBException("Can not find Department into database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException sqle) {
                throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
            }
            try {
                if(res != null)
                    res.close();
            } catch(SQLException sqle) {
                throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
            }
        }
    }

    @Override
    public void ejbStore() throws EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        StringBuilder sql = new StringBuilder("UPDATE dept SET ");
        sql.append("dname = ?, loc = ? ").
        append("WHERE deptno = ?");
             
        try {
               stmt = connection.prepareStatement(sql.toString());
            
               stmt.setString(1, dname);
               stmt.setString(2, loc);

               stmt.setInt(3, deptno);
                
               stmt.execute();
        } catch(SQLException sqle) {
            throw new EJBException("Can not update Department into database:" + sqle.getMessage(), sqle);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException sqle) {
                    throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
                }
            }
            if(res != null) {
                try {
                    res.close();
                } catch(SQLException sqle) {
                    throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
                }
            }
        }
    }

    @Override
    public void ejbActivate() throws EJBException {
        deptno = (Integer)context.getPrimaryKey();
    }

    @Override
    public void ejbPassivate() throws EJBException {
        deptno = null;
    }

    @Override
    public void ejbRemove() throws RemoveException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        System.out.println(deptno);
        try {
            stmt = connection.prepareStatement("DELETE FROM dept WHERE deptno = ?");
            stmt.setInt(1, deptno);
            stmt.executeUpdate();
        } catch(SQLException sqle) {
            throw new EJBException("Can not delete Department from database: " + sqle.getMessage(), sqle);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException sqle) {
                    throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
                }
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

    public Integer ejbCreate(String dName, String location)
            throws CreateException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;
        String sql = "INSERT INTO dept VALUES(dept_id.nextval, ?, ?)";
        
        try {
            if(dName != null && location != null) {
                stmt = connection.prepareStatement(sql.toString());
                
                stmt.setString(1, dName);
                stmt.setString(2, location);
    
                if(stmt.execute()) {
                    stmt = connection.prepareStatement("select dept_id.curval into id from dual");
                    res = stmt.executeQuery();
                    if(res.next()) {
                        deptno = res.getInt(1);
                    }
                }
            } else {
                throw new WrongDataException("Parameters can not be empty");
            }
        }catch(SQLException sqle) {
            throw new EJBException("Can not insert Department into database: " + sqle.getMessage(), sqle);
        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch(SQLException sqle) {
                    throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
                }
            }
        }
        this.setDname(dName);
        this.setLoc(location);
        return deptno;
    }
    
    public void ejbPostCreate(String dName, String location) {
        
    }
    
    public Integer ejbFindByPrimaryKey(Integer deptno) throws FinderException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            stmt = connection.prepareStatement("SELECT * FROM dept WHERE deptno = ?");
            stmt.setInt(1, deptno);
            res = stmt.executeQuery();
            
            if(res.next()) {
                return deptno;
            } else {
                throw new FinderException("Can not find Department with deptno " + deptno.toString());
            }
                
        } catch(SQLException sqle) {
            throw new EJBException("Can not find Department into database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException sqle) {
                throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
            }
            try {
                if(res != null)
                    res.close();
            } catch(SQLException sqle) {
                throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
            }
        }
      }
    
    public java.util.Collection<Integer> ejbFindAll() throws FinderException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        Statement stmt = null;
        ResultSet res = null;
        List<Integer> depList = new ArrayList<Integer>();
        
        try {
            stmt = connection.createStatement();
            res = stmt.executeQuery("SELECT * FROM dept");
            
            while(res.next()) {
                depList.add(res.getInt("deptno"));
            }
        } catch(SQLException sqle) {
            throw new EJBException("Can not select Departments from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException sqle) {
                throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
            }
            try {
                if(res != null)
                    res.close();
            } catch(SQLException sqle) {
                throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
            }
        }
        return depList;
    }
    
    public java.util.Collection<Integer> ejbFindByNameAndLocation(String dname, String location) throws FinderException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        List<Integer> depts = new ArrayList<Integer>();
        PreparedStatement stmt = null;
        ResultSet res = null;
        StringBuilder sql = new StringBuilder("SELECT deptno FROM dept ");
                
        sql.append("where ");
        if(dname != null) {
            sql.append("dname like upper(?)");
            if(location != null) {
                sql.append(" and ");
            }
        }
        if(location != null) {
            sql.append("loc like upper(?)");
        }
        
        try {
            stmt = connection.prepareStatement(sql.toString());
            
            if(dname != null) {
                stmt.setString(1, "%" + dname + "%");
            }
            
            if(location != null) {
                stmt.setString(2, "%" + location + "%");
            }
            res = stmt.executeQuery();
            
            while(res.next()) {
                depts.add(res.getInt("deptno"));
            }
        } catch(SQLException sqle) {
            throw new EJBException("Can not search Departments from database:" + sqle.getMessage(), sqle);
        } finally {
            try {
                if(connection != null)
                    connection.close();
            } catch(SQLException sqle) {
                throw new EJBException("There are some problem with db: " + sqle.getMessage(), sqle);
            }
        }
        return depts;
    }
    
}
