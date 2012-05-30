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
    
    private static final String SELECT_BY_DEPTNO_QUERY = "SELECT * FROM dept WHERE deptno = ?";
    private static final String SELCTION_QUERY = "SELECT deptno FROM dept";
    private static final String UPDATING_QUERY = "UPDATE dept SET dname = ?, loc = ? WHERE deptno = ?";
    private static final String DELETION_QUERY = "DELETE FROM dept WHERE deptno = ?";
    private static final String INSERTION_QUERY = "INSERT INTO dept VALUES(dept_id.nextval, ?, ?)";
    private static final String SELECT_SEQUENCE_CURVAL = "select dept_id.curval into id from dual";
    
    
    private static final String INSERTION_PROBLEM = "Can not insert Department into database: ";
    private static final String SELECTION_PROBLEM = "Can not select Department from database: ";
    private static final String DELETION_PROBLEM = "Can not delete Department from database: ";
    private static final String UPDATING_PROBLEM = "Can not update Department into database: ";
    private static final String FINDING_PROBLEM = "Can not find Department in database: ";
    private static final String SEARCHING_PROBLEM = "Can not search Department from database:";
    private static final String DB_PROBLEM = "There are some problem with db: ";
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
            stmt = connection.prepareStatement(SELECT_BY_DEPTNO_QUERY);
            stmt.setInt(1, deptno);
            res = stmt.executeQuery();
            
            if(res.next()) {
                dname = res.getString("dname");
                loc = res.getString("loc");
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

    @Override
    public void ejbStore() throws EJBException {
        Connection connection = DBConnector.getConnection(context);
        PreparedStatement stmt = null;

             
        try {
               stmt = connection.prepareStatement(UPDATING_QUERY);
            
               stmt.setString(1, dname);
               stmt.setString(2, loc);

               stmt.setInt(3, deptno);
                
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
            stmt = connection.prepareStatement(DELETION_QUERY);
            stmt.setInt(1, deptno);
            stmt.executeUpdate();
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
                
        try {
            if(dName != null && location != null) {
                stmt = connection.prepareStatement(INSERTION_QUERY);
                
                stmt.setString(1, dName);
                stmt.setString(2, location);
    
                if(stmt.execute()) {
                    stmt = connection.prepareStatement(SELECT_SEQUENCE_CURVAL);
                    res = stmt.executeQuery();
                    if(res.next()) {
                        deptno = res.getInt(1);
                    }
                }
            } else {
                throw new WrongDataException("Parameters can not be empty");
            }
        }catch(SQLException sqle) {
            throw new EJBException(INSERTION_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
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
            stmt = connection.prepareStatement(SELECT_BY_DEPTNO_QUERY);
            stmt.setInt(1, deptno);
            res = stmt.executeQuery();
            
            if(res.next()) {
                return deptno;
            } else {
                throw new FinderException(FINDING_PROBLEM + deptno.toString());
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
    
    public java.util.Collection<Integer> ejbFindAll() throws FinderException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        Statement stmt = null;
        ResultSet res = null;
        List<Integer> depList = new ArrayList<Integer>();
        
        try {
            stmt = connection.createStatement();
            res = stmt.executeQuery(SELCTION_QUERY);
            
            while(res.next()) {
                depList.add(res.getInt("deptno"));
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
        return depList;
    }
    
    public java.util.Collection<Integer> ejbFindByNameAndLocation(String dname, String location) throws FinderException, EJBException {
        Connection connection = DBConnector.getConnection(context);
        List<Integer> depts = new ArrayList<Integer>();
        PreparedStatement stmt = null;
        ResultSet res = null;
        StringBuilder sql = new StringBuilder(SELCTION_QUERY);
                
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
            throw new EJBException(SEARCHING_PROBLEM + sqle.getMessage(), sqle);
        } finally {
            try {
                DBConnector.closeConnection(res, stmt, connection);
            } catch(SourceException sqle) {
                throw new EJBException(DB_PROBLEM + sqle.getMessage(), sqle);
            }
        }
        return depts;
    }
    
}
