package ua.edu.sumdu.j2ee.Gorbatenko.lab4.Controllers;

import java.util.*;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.model.*;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.*;

/**
 * 
 * @author Shuric
 */
public class Controller {

    private String DAOFactoryName;
    public static Controller controller = null;

    private Controller() {

    }

    public static Controller getController(String config) {
        if (controller == null) {
            controller = new Controller(config);
        }
        return controller;
    }

    public List<List<Packet>> onCompSelect() throws SourceException,
            DAOException, Exception {
        List<List<Packet>> res = new ArrayList<List<Packet>>();

        List dept = DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("department").select(-1, 0);

        List<Packet> lst = new ArrayList<Packet>();
        lst.add(new Packet("Number"));
        lst.add(new Packet("Name"));
        lst.add(new Packet("Location"));
        lst.add(new Packet(" "));

        res.add(lst);

        for (int i = 0; i < dept.size(); i++) {
            lst = new ArrayList<Packet>();
            Department d = (Department) dept.get(i);

            lst.add(new Packet(d.getDeptno() + ""));
            Packet pack = new Packet(d.getDname());
            pack.setLink(d.getDeptno() + "");
            lst.add(pack);
            lst.add(new Packet(d.getLoc()));

            res.add(lst);
        }

        return res;
    }

    private List<List<Packet>> empsToStringList(List emps) {
        List<List<Packet>> res = new ArrayList<List<Packet>>();
        List<Packet> lst = new ArrayList<Packet>();
        lst.add(new Packet("ID"));
        lst.add(new Packet("Name"));
        lst.add(new Packet("Job"));
        lst.add(new Packet("Manager"));
        lst.add(new Packet(" "));

        res.add(lst);

        for (int i = 0; i < emps.size(); i++) {
            lst = new ArrayList<Packet>();
            Employee emp = (Employee) emps.get(i);
            lst.add(new Packet(emp.getEmpno() + ""));
            Packet pack = new Packet(emp.getEname());
            pack.setLink(emp.getEmpno() + "");
            lst.add(pack);
            lst.add(new Packet(emp.getJob()));

            pack = new Packet(emp.getMgrName());
            pack.setLink(emp.getMgr() + "");
            lst.add(pack);

            res.add(lst);
        }
        return res;
    }

    public Controller(String DAOFactoryName) {
        this.DAOFactoryName = DAOFactoryName;
    }

    public List<List<Packet>> onDepSelect(int deptno) throws SourceException,
            DAOException, Exception {
        return empsToStringList(DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").select(0, deptno));
    }

    public List<List<Packet>> onEmpSelect(int mgrID) throws SourceException,
            DAOException, Exception {
        return empsToStringList(DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").select(1, mgrID));
    }

    public Department onFindDept(int id) throws SourceException, DAOException,
            Exception {
        Department dep = null;

        dep = (Department) DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("department").find(id);

        return dep;
    }

    public Employee onFindEmp(int id) throws SourceException, DAOException,
            Exception {
        Employee emp = null;

        emp = (Employee) DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").find(id);

        return emp;
    }

    public List<Employee> onFindEmpHierarchically(int id)
            throws SourceException, DAOException, Exception {
        List<Employee> emps = (List) DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").select(2, id);
        return emps;
    }

    public List<Employee> onFindAllManagers() throws SourceException,
            DAOException, Exception {
        List<Employee> emps = (List) DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").select(3, 0);
        return emps;
    }

    public boolean onDeleteDept(int id) throws SourceException, DAOException,
            Exception {
        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("department").delete(id);
    }

    public boolean onDeleteEmp(int id) throws Exception {
        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").delete(id);
    }

    public boolean onUpdateEmp(Employee oldEmp, Employee newEmp)
            throws SourceException, DAOException, Exception {
        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").update(oldEmp, newEmp);
    }

    public boolean onUpdateDept(Department oldDept, Department newDept)
            throws SourceException, DAOException, Exception {
        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("department").update(oldDept, newDept);
    }

    public int onAddEmp(Employee emp) throws SourceException, DAOException,
            Exception {
        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").insert(emp);
    }

    public int onAddDept(Department dept) throws SourceException, DAOException,
            Exception {
        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("department").insert(dept);
    }

    public List onEmpSearch(String empno, String ename) throws SourceException,
            DAOException, Exception {
        List<String> params = new ArrayList<String>();
        params.add(empno);
        params.add(ename);

        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("employee").search(params);
    }

    public List onDeptSearch(String dname, String loc) throws SourceException,
            DAOException, Exception {
        List<String> params = new ArrayList<String>();
        params.add(dname);
        params.add(loc);

        return DAOFactory.getDAOFactory(DAOFactoryName)
                .getEntityDAO("department").search(params);
    }
}
