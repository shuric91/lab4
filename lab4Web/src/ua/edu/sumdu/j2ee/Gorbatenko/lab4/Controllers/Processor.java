package ua.edu.sumdu.j2ee.Gorbatenko.lab4.Controllers;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.model.Department;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.model.Employee;

import org.apache.log4j.*;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.DAOConfigurator;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.LoggerConfigurator;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet;

import ua.edu.sumdu.j2ee.Gorbatenko.lab4.exceptions.*;

/**
 * Servlet implementation class Processor
 */
//@WebServlet("/Processor")
public class Processor extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static Logger log = LoggerConfigurator.configure(Processor.class.getName());
    private DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    private void logException(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        request.setAttribute("message", e.getMessage());
        request.setAttribute("details", exceptionToString(e.getStackTrace()));
    }

    private String exceptionToString(StackTraceElement[] arg) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i].toString() + "<br />");
        }

        return sb.toString();
    }

    private List<Packet> getLinksForDepartment(Department dep) {
        List<Packet> links = new ArrayList<Packet>();
        Packet pack = new Packet("Company");
        pack.setLink("Processor?showcompany");
        links.add(pack);

        pack = new Packet(dep.getDname());
        pack.setLink("Processor?showdept=" + dep.getDeptno());
        links.add(pack);

        return links;
    }

    private List<Packet> getLinksForEmployee(Department dep,
            LinkedList<Employee> emps, Employee emp) {
        List<Packet> links = new ArrayList<Packet>();
        Packet pack = new Packet("Company");
        pack.setLink("Processor?showcompany");
        links.add(pack);

        pack = new Packet(dep.getDname());
        pack.setLink("Processor?showdept=" + emp.getDeptno());
        links.add(pack);

        Iterator<Employee> iter = emps.descendingIterator();
        while (iter.hasNext()) {
            Employee employee = (Employee) iter.next();
            pack = new Packet(employee.getEname());
            pack.setLink("Processor?showemp=" + employee.getEmpno()
                    + "&deptID=" + emp.getDeptno());
            links.add(pack);
        }
        return links;
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        BasicConfigurator.configure();
        response.setContentType("text/html;charset=UTF-8");

        List<Packet> links;

        try {
            Controller controller = Controller.getController(DAOConfigurator
                    .getDAOFactoryName(getServletContext().getRealPath(
                            "res/DAOFactory.conf")));

            if (request.getParameter("showcompany") != null) {
                request.setAttribute("result", controller.onCompSelect());
                request.setAttribute("entity", "dept");

                links = new ArrayList<Packet>();
                Packet pack = new Packet("Company");
                pack.setLink("Processor?showcompany");
                links.add(pack);
                request.setAttribute("links", links);

                this.getServletContext().getRequestDispatcher("/")
                        .forward(request, response);
            } else if (request.getParameter("showdept") != null) {
                request.setAttribute("result", controller.onDepSelect(Integer
                        .parseInt(request.getParameter("showdept"))));
                request.setAttribute("entity", "emp");

                Department dep = controller.onFindDept(Integer.parseInt(request
                        .getParameter("showdept")));

                request.setAttribute("links", getLinksForDepartment(dep));

                List<Packet> parent = new ArrayList<Packet>();
                parent.add(new Packet("ID", "", dep.getDeptno() + ""));
                parent.add(new Packet("Name", "", dep.getDname() + ""));
                parent.add(new Packet("Location", "", dep.getLoc() + ""));

                request.setAttribute("parent", parent);
                request.setAttribute("deptID", dep.getDeptno() + "");
                request.setAttribute("parentEntity", "dept");
                request.setAttribute("parentID", "NULL");

                this.getServletContext().getRequestDispatcher("/")
                        .forward(request, response);
            } else if (request.getParameter("showemp") != null) {
                Employee emp = controller.onFindEmp(Integer.parseInt(request
                        .getParameter("showemp")));

                Department dep = controller.onFindDept(emp.getDeptno());

                LinkedList<Employee> emps = (LinkedList<Employee>) controller
                        .onFindEmpHierarchically(Integer.parseInt(request
                                .getParameter("showemp")));

                request.setAttribute("links",
                        getLinksForEmployee(dep, emps, emp));

                request.setAttribute("result", controller.onEmpSelect(Integer
                        .parseInt(request.getParameter("showemp"))));
                request.setAttribute("entity", "emp");

                List<Packet> parent = new ArrayList<Packet>();
                parent.add(new Packet("ID", "", emp.getEmpno() + ""));
                parent.add(new Packet("Name", "", emp.getEname()));
                parent.add(new Packet("Job", "", emp.getJob()));
                parent.add(new Packet("Manager", "", emp.getMgr() + ""));
                parent.add(new Packet("Hiredate", "", format.format(emp
                        .getHiredate())));
                parent.add(new Packet("Salgrade", "", emp.getSal() + ""));
                parent.add(new Packet("Commission", "", emp.getComm() + ""));

                request.setAttribute("parent", parent);
                request.setAttribute("deptID", request.getParameter("deptID"));
                request.setAttribute("parentID", emp.getEmpno() + "");
                request.setAttribute("parentEntity", "emp");

                this.getServletContext().getRequestDispatcher("/")
                        .forward(request, response);
            } else if (request.getParameter("editdept") != null) {
                Department dep = controller.onFindDept(Integer.parseInt(request
                        .getParameter("editdept")));

                links = getLinksForDepartment(dep);
                links.add(new Packet("EDITING"));

                request.setAttribute("links", links);

                List<Packet> entity = new ArrayList<Packet>();

                entity.add(new Packet("Department name", "dname", dep
                        .getDname()));
                entity.add(new Packet("Department location", "loc", dep
                        .getLoc()));
                request.setAttribute("entity", entity);
                request.setAttribute("entitytype", "dept");
                request.setAttribute("id", dep.getDeptno());

                this.getServletContext().getRequestDispatcher("/edit.jsp")
                        .forward(request, response);
            } else if (request.getParameter("editemp") != null) {
                Employee emp = controller.onFindEmp(Integer.parseInt(request
                        .getParameter("editemp")));

                Department dep = controller.onFindDept(emp.getDeptno());

                LinkedList<Employee> emps = (LinkedList<Employee>) controller
                        .onFindEmpHierarchically(emp.getEmpno());

                links = getLinksForEmployee(dep, emps, emp);
                links.add(new Packet("EDITING"));

                request.setAttribute("links", links);

                List<Packet> entity = new ArrayList<Packet>();

                entity.add(new Packet("Employee name", "ename", emp.getEname()));
                entity.add(new Packet("Employee manager", "mgr", emp.getMgr()
                        + ""));
                entity.add(new Packet("Employee department", "deptno", emp
                        .getDeptno() + ""));
                entity.add(new Packet("Employee job", "job", emp.getJob()));
                entity.add(new Packet("Employee hiredate", "hiredate", format
                        .format(emp.getHiredate())));
                entity.add(new Packet("Employee salgrade", "sal", emp.getSal()
                        + ""));
                entity.add(new Packet("Employee commision", "comm", emp
                        .getComm() + ""));
                request.setAttribute("entity", entity);
                request.setAttribute("entitytype", "emp");
                request.setAttribute("id", emp.getEmpno());

                this.getServletContext().getRequestDispatcher("/edit.jsp")
                        .forward(request, response);
            } else if (request.getParameter("adddept") != null) {
                links = new ArrayList<Packet>();
                Packet pack = new Packet("Company");
                pack.setLink("Processor?showcompany");
                links.add(pack);
                links.add(new Packet("ADDING NEW DEPARTMENT"));

                request.setAttribute("links", links);

                List<Packet> entity = new ArrayList<Packet>();
                entity.add(new Packet("Department name", "dname"));
                entity.add(new Packet("Department location", "loc"));
                request.setAttribute("entity", entity);
                request.setAttribute("entitytype", "dept");

                this.getServletContext().getRequestDispatcher("/add.jsp")
                        .forward(request, response);
            } else if (request.getParameter("addemp") != null) {
                String sid = request.getParameter("parentID");

                Employee emp = null;
                if (!sid.equals("NULL")) {
                    request.setAttribute("mgr", sid);
                    emp = controller.onFindEmp(Integer.parseInt(sid));
                } else {
                    request.setAttribute("mgr", controller.onFindAllManagers());
                }

                String deptID = request.getParameter("deptID");
                Department dep = null;
                if (deptID != null) {
                    dep = controller.onFindDept(Integer.parseInt(deptID));
                }

                LinkedList<Employee> emps = null;
                if (emp != null) {
                    emps = (LinkedList<Employee>) controller
                            .onFindEmpHierarchically(emp.getEmpno());
                }

                if (emp == null || emps == null) {
                    links = getLinksForDepartment(dep);
                } else {
                    links = getLinksForEmployee(dep, emps, emp);
                }
                links.add(new Packet("ADDING NEW EMPLOYEE"));

                request.setAttribute("links", links);

                List<Packet> entity = new ArrayList<Packet>();
                entity.add(new Packet("Employee name", "ename"));
                entity.add(new Packet("Employee job", "job"));
                entity.add(new Packet("Employee salgrade", "sal"));
                entity.add(new Packet("Employee commision", "comm"));
                request.setAttribute("entity", entity);
                request.setAttribute("entitytype", "emp");

                this.getServletContext().getRequestDispatcher("/add.jsp")
                        .forward(request, response);
            } else if (request.getParameter("deletedept") != null) {
                controller.onDeleteDept(Integer.parseInt(request
                        .getParameter("deletedept")));

                response.sendRedirect("Processor?showcompany");
            } else if (request.getParameter("deleteemp") != null) {
                int empno = Integer.parseInt(request.getParameter("deleteemp"));
                Employee emp = controller.onFindEmp(empno);
                controller.onDeleteEmp(empno);

                response.sendRedirect("Processor?showdept=" + emp.getDeptno());
            }
        } catch (SourceException se) {
            logException(request, se);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (DAOException daoe) {
            logException(request, daoe);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (WrongDataException wde) {
            logException(request, wde);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            logException(request, e);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        BasicConfigurator.configure();
        response.setContentType("text/html;charset=UTF-8");
        Controller controller = Controller.getController(DAOConfigurator
                    .getDAOFactoryName(getServletContext().getRealPath(
                            "res/DAOFactory.conf")));
        try {
            if ("update".equals(request.getParameter("acttype"))) {
                if ("emp".equals(request.getParameter("entity"))) {
                    if (request.getParameter("id") != null) {
                        try {
                            Integer id = Integer.parseInt(request
                                    .getParameter("id"));

                            Employee oldEmp = (Employee) controller
                                    .onFindEmp(id);
                            Employee newEmp = new Employee();

                            newEmp.setEmpno(Integer.parseInt(request
                                    .getParameter("empno")));
                            newEmp.setEname(request.getParameter("ename"));
                            newEmp.setMgr(Integer.parseInt(request
                                    .getParameter("mgr")));
                            newEmp.setDeptno(Integer.parseInt(request
                                    .getParameter("deptno")));
                            newEmp.setJob(request.getParameter("job"));
                            newEmp.setHiredate(format.parse(request
                                    .getParameter("hiredate")));
                            newEmp.setSal(Double.parseDouble(request
                                    .getParameter("sal")));
                            newEmp.setComm(Double.parseDouble(request
                                    .getParameter("comm")));

                            controller.onUpdateEmp(oldEmp, newEmp);
                        } catch (ParseException pe) {
                            log.error(pe.getMessage(), pe);
                            request.setAttribute("message",
                                    "Wrong date format. Must be dd.mm.yyyy. :"
                                            + pe.getMessage());
                            request.setAttribute("details",
                                    exceptionToString(pe.getStackTrace()));
                            this.getServletContext()
                                    .getRequestDispatcher("/error.jsp")
                                    .forward(request, response);
                        }
                    }
                } else if ("dept".equals(request.getParameter("entity"))) {
                    if (request.getParameter("id") != null) {
                        Integer id = Integer.parseInt(request
                                .getParameter("id"));

                        Department oldDept = (Department) controller
                                .onFindDept(id);
                        Department newDept = new Department();

                        newDept.setDeptno(Integer.parseInt(request
                                .getParameter("id")));
                        newDept.setDname(request.getParameter("dname"));
                        newDept.setLoc((request.getParameter("loc")));

                        controller.onUpdateDept(oldDept, newDept);
                    }
                }

                response.sendRedirect("Processor?showcompany");

            } else if ("add".equals(request.getParameter("acttype"))) {
                if ("emp".equals(request.getParameter("entity"))) {
                    Employee newEmp = new Employee();

                    newEmp.setEname(request.getParameter("ename"));
                    newEmp.setMgr(Integer.parseInt(request.getParameter("pID")));
                    newEmp.setDeptno(Integer.parseInt(request
                            .getParameter("deptno")));
                    newEmp.setJob(request.getParameter("job"));
                    newEmp.setHiredate(new java.util.Date());
                    newEmp.setSal(Double.parseDouble(request
                            .getParameter("sal")));
                    newEmp.setComm(Double.parseDouble(request
                            .getParameter("comm")));

                    controller.onAddEmp(newEmp);
                    response.sendRedirect("Processor?showdept="
                            + request.getParameter("deptno"));
                } else if ("dept".equals(request.getParameter("entity"))) {
                    Department newDept = new Department();
                    System.out.println(request.getParameter("dname"));
                    System.out.println(request.getParameter("loc"));
                    newDept.setDname(request.getParameter("dname"));
                    newDept.setLoc(request.getParameter("loc"));

                    controller.onAddDept(newDept);

                    response.sendRedirect("Processor?showcompany");
                }

            } else if ("search".equals(request.getParameter("acttype"))) {
                if ("emp".equals(request.getParameter("entity"))) {
                    String empno = (String) request.getParameter("empno");
                    String ename = (String) request.getParameter("ename");
                    List<Employee> emps = (List<Employee>) controller
                            .onEmpSearch(empno, ename);

                    StringBuilder sb = new StringBuilder("<table border=\"1\">");

                    for (Employee emp : emps) {
                        sb.append("<tr><td>").append(emp.getEmpno())
                                .append("</td>");
                        sb.append("<td><a href=\"Processor?showemp=")
                                .append(emp.getEmpno()).append("\">")
                                .append(emp.getEname())
                                .append("</a></td></tr>");
                    }

                    sb.append("</table>");
                    response.getOutputStream().println(sb.toString());

                } else if ("dept".equals(request.getParameter("entity"))) {
                    String dname = (String) request.getParameter("dname");
                    String loc = (String) request.getParameter("loc");
                    List<Department> depts = (List<Department>) controller
                            .onDeptSearch(dname, loc);

                    StringBuilder sb = new StringBuilder("<table border=\"1\">");

                    for (Department dept : depts) {
                        sb.append("<tr><td>").append(dept.getDeptno())
                                .append("</td>");
                        sb.append("<td><a href=\"Processor?showdept=")
                                .append(dept.getDeptno()).append("\">")
                                .append(dept.getDname()).append("</a></td>");
                        sb.append("<td>").append(dept.getLoc())
                                .append("</td></tr>");
                    }

                    sb.append("</table>");
                    response.getOutputStream().println(sb.toString());
                }
            }
        } catch (SourceException se) {
            logException(request, se);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (DAOException daoe) {
            logException(request, daoe);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (WrongDataException wde) {
            logException(request, wde);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            logException(request, e);
            this.getServletContext().getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }

}
