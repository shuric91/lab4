<%@page import="ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet"%>
<%@page import="java.util.List"%>
<% List<Packet> links = (List<Packet>) request.getAttribute("links");
 	if(links != null) {
   		int i = 0;
   		for(Packet pack: links) {
   			if(pack.getLink() != null) {
   			 	out.println("<a href=\"" + pack.getLink() + "\">" + pack.getTitle() + "</a>");
   			} else {
   				out.println(pack.getTitle());
   			}
   			if(i != (links.size() - 1)) {
   				out.println(">");
   			} 
   			i++; 
   		}
	}%>

<br />