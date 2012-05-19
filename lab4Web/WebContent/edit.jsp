<%@ page import="ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/index.css">
<title>Edit</title>
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
	<% List<Packet> entity = (List<Packet>)request.getAttribute("entity");
	   String type = (String) request.getAttribute("entitytype");
	   Integer id = (Integer) request.getAttribute("id");
		if(entity != null && type != null && id != null) { %>
		
		<form method="post">
			<table> 
			<%for(Packet pack: entity) { %>
				<tr>
				<td><%= pack.getTitle() %>&nbsp&nbsp&nbsp&nbsp</td>
				<td>
				<input type="text" <%= "value=\"" + pack.getValue() +"\"" %> <%= "name=\"" + pack.getName() + "\"" %>> <p />
				</td>
				</tr>
			<% } %>
			
			</table>
			<br />
			<input type="hidden" name="acttype" value="update">
			<input type="hidden" name="entity" value="<%= type %>">
			<input type="hidden" name="id" value="<%= id %>">
			<input type="submit" value="Save changes" />	
			<input type="button" value="Cancel" onClick="history.back();" />
		</form>
		<% } %>
</div>
</div>
</body>
</html>