<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error</title>
<script language="javascript">
function showDetails() {
	var el = document.getElementById('div1');
	if(el.style.visibility == 'visible') {
		el.style.visibility = 'hidden';
	} else {
		el.style.visibility = 'visible';
	}
    el.style.opacity = 1;
}
</script>
<LINK href="styles/error.css" rel="stylesheet" type="text/css">
</head>
<body>
<a href="" onClick="history.back();">Back</a>
<% 
String message = (String) request.getAttribute("message");
String details = (String) request.getAttribute("details");
%>
<h3>
<font color="#ff0000">
<% if(message != null) {%>
	<%= message %>
<%} %>
</font>
</h3>
<a href="#" onClick="showDetails();">Details</a>
<div class="details" id="div1">
<%if(details != null) {%>
<%= details %>
<%} %>
</div>
</body>
</html>