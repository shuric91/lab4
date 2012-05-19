<%@page import="ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
     	<link rel="stylesheet" type="text/css" href="styles/index.css">
     	<script language="javascript">
     		function confirmDelete(text, link) {
         		if('emp' == text) {
             		text = 'Employee';
         		} else if('dept' == text) {
         			text = 'Department';
         		}
     			if (confirm("Are you sure delete this " + text + "?")) {
     				window.location = link;	  
     			}
         	}
     	</script>
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
        <br />
       <%
       		List<Packet> parent = (List<Packet>)request.getAttribute("parent");
       		String parentEntity = (String)request.getAttribute("parentEntity");
       		String entity = (String)request.getAttribute("entity");
       		String mgr = (String)request.getAttribute("parentID");
       		String dept = (String)request.getAttribute("deptID");
       		
       		String pDept = "";
       		if(mgr != null) {
       			pDept = "&deptID=" + dept;
       		}
       		
       	  	if(parent != null && parentEntity != null) { 
       	  	%>
       	  	<table border="0">
       	  		<% 	for(Packet att: parent) { %>
       	  		<tr>
       	  			<td width="60%"><b><%= att.getTitle() %></b> </td>
       	  			<td><%= att.getValue() %> </td>
       	  		</tr>
       	  		<%} %>
       			
       	  	</table>
       	  	<table>
       	  	<tr>
       	  		<form>
       	  			<input type="hidden" name="edit<%= parentEntity %>" value="<%= ((Packet)parent.get(0)).getValue() %>" />
       	  			<td><input type="submit" value="Edit" /></td>
       	  		</form>
       	  	<% }%>
       	  	<% if(entity != null) {%>
       	  	<form>
       	  		<%if(parent != null) {%>
       	  		<input type="hidden" name="deptID" value="<%= dept %>" />
       	  		<input type="hidden" name="parentID" value="<%= mgr %>" />
       	  		<%} %>
       	  		<td><input type="submit" name="add<%= entity %>" value="Add new <%= entity %>" /></td>
       	  	</form>
       	  	</tr>
       	  	</table>
       	  	<% } %>
       	  	<br />
       <p />
                	<%  
                    List<List<Packet> > res = (List<List<Packet> >)request.
                            getAttribute("result");
                	                	
                    if(res != null && entity != null) {
                    %>
                    <table cellpadding="10" border="1">
                        	<% for(int k = 0; k < res.size(); k++) { 
                        			List<Packet> lst = res.get(k);
                        			if(k == 0) { 
                                		out.println("<tr bgcolor = \"aqua\">");
                                	} else { 
                                		out.println("<tr>");
                                	}                       		
                                	int i = 0;
                                	for(Packet pack: lst) {
                             			if(pack.getLink() != null) {
                                			out.println("<td class=\"wide\"><a href=\"Processor?show" + entity + "=" 
                                					+ pack.getLink() + pDept +"\">" + pack.getTitle() + "</a></td>");
                                		} else {
                                			if(k == 0) {
                                				out.println("<th>" + pack.getTitle() + "</a></th>");
                                			} else {
                                				out.println("<td class=\"wide\">" + pack.getTitle() + "</a></td>");
                                			}
                                    	} 
                                	 	i++;
                                	}
                                	if(k != 0) { %>
                                	<td class="wide"><a href="#" onClick="confirmDelete('<%= entity%>', 'Processor?delete<%= entity + "=" + lst.get(0).getTitle()%>')">
                                		Delete</a></td>
                                	<% }%>
                                </tr>
                                <% }%>
                            <!--</span>
                        </li>-->
                			
                    </table>
                <%    	} else { %>
                        <br /><a href="Processor?showcompany">Company</a>
                <%  	} %>
         
         </div>
    </div>    
    </body>
</html>
