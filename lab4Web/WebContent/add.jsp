<%@page import="ua.edu.sumdu.j2ee.Gorbatenko.lab4.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/index.css">
<title>Add</title>
</head>
<body background="styles/background.jpg">
<br />
       <p>
<div id="menu">
    <%@include file="header.jsp" %>
</div>

 <div class="main">
    <div class="viewer">
    <%@include file="/menu.jsp" %>
    <br />
<%
	List<Packet> entity = (List<Packet>) request.getAttribute("entity");
	String type = (String) request.getAttribute("entitytype");
	String deptno = (String) request.getParameter("deptID");
	String mgr = (String) request.getParameter("parentID");
	
	if (entity != null && type != null) {
%>
<form method="post">
<table>
	<%
		if ("emp".equals(type)) { 
			if(!"NULL".equals(mgr)) { 
				out.println("<tr><td>Employee manager</td>" +
					"<td><input type=\"text\" value=\"" + mgr + "\" readonly /></td></tr>");
			} else {
				List<Employee> emps = (List<Employee>) request.getAttribute("mgr");
				if(emps != null) {
					out.println("<tr><td>Employee manager</td>");					
					out.println("<td><select name=\"pID\">");
					for(Employee emp: emps) {
						out.println("<option value=\"" + emp.getEmpno() + "\">" + emp.getEname() + "</option>");
					}
					out.println("</select></td></tr>");
				}
			}
		}
		for (Packet pack : entity) {
	%>
	<tr>
		<td><%=pack.getTitle()%>&nbsp&nbsp&nbsp&nbsp</td>
		<td><input type="text" <%="name=\"" + pack.getName() + "\""%>>
		<p />
		</td>
	</tr>
	<%
		}
	%>
</table>
<br />
<input type="hidden" name="acttype" value="add"> <input
	type="hidden" name="entity" value="<%=type%>"> <%
 	if (deptno != null) {
 		out.println("<input type=\"hidden\" name=\"deptno\" value=\"" + deptno + "\">");
 	}
	if(mgr != null) {
		out.println("<input type=\"hidden\" name=\"pID\" value=\"" + mgr + "\">");
	} else {
		List<Packet> mgrList = (List<Packet>)request.getAttribute("managers");
	}
 %>

	<input
	type="submit" value="Save changes" /> <input type="button"
	value="Cancel" onClick="history.back();" /></form>
<%
	}
%>
</div>
</div>
</body>
</html>