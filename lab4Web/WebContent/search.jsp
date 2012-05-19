<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="styles/index.css">
<title>Search</title>
<script type="text/javascript" src="js/jquery-1.5.1.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  $("#empno").keyup(function(){
     var empno = $("#empno").val();
     var ename = $("#ename").val();
     $.ajax({
       type: "POST",
       url: "Processor",
       data: {"acttype": "search", "entity": "emp", "empno": empno, "ename": ename},
       cache: false,                                 
       success: function(response){
          $("#resSearch").html(response);
       }
       });
       return false;
   });
  
  $("#ename").keyup(function(){
	     var empno = $("#empno").val();
	     var ename = $("#ename").val();
	     $.ajax({
	       type: "POST",
	       url: "Processor",
	       data: {"acttype": "search", "entity": "emp", "empno": empno, "ename": ename},
	       cache: false,                                 
	       success: function(response){
	          $("#resSearch").html(response);
	       }
	       });
	       return false;
	   });
  
  $("#dname").keyup(function(){
	     var dname = $("#dname").val();
	     var loc = $("#loc").val();
	     $.ajax({
	       type: "POST",
	       url: "Processor",
	       data: {"acttype": "search", "entity": "dept", "dname": dname, "loc": loc},
	       cache: false,                                 
	       success: function(response){
	          $("#resSearch").html(response);
	       }
	       });
	       return false;
	   });

  $("#loc").keyup(function(){
	     var dname = $("#dname").val();
	     var loc = $("#loc").val();
	     $.ajax({
	       type: "POST",
	       url: "Processor",
	       data: {"acttype": "search", "entity": "dept", "dname": dname, "loc": loc},
	       cache: false,                                 
	       success: function(response){
	          $("#resSearch").html(response);
	       }
	       });
	       return false;
	   });
});
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
	<form name="search" method="post">
	<%if("emp".equals(request.getParameter("search"))) { %>
	Employee id      <input name="empno" type="text" id="empno">
	Employee name    <input name="ename" type="text" id="ename">
	<%} else if("dept".equals(request.getParameter("search"))){%>
	Department name       <input name="dname" type="text" id="dname">
	Department location   <input name="loc" type="text" id="loc">
	<%} %>
	<p>
	<div id="resSearch"></div>
	</div>
</div>
</body>
</html>