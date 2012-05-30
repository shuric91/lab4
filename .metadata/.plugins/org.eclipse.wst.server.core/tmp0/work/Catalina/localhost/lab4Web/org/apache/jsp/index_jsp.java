package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet;
import java.util.*;
import ua.edu.sumdu.j2ee.Gorbatenko.lab4.utils.Packet;
import java.util.List;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.List<java.lang.String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<java.lang.String>(2);
    _jspx_dependants.add("/header.jsp");
    _jspx_dependants.add("/menu.jsp");
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.List<java.lang.String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>JSP Page</title>\n");
      out.write("     \t<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/index.css\">\n");
      out.write("     \t<script language=\"javascript\">\n");
      out.write("     \t\tfunction confirmDelete(text, link) {\n");
      out.write("         \t\tif('emp' == text) {\n");
      out.write("             \t\ttext = 'Employee';\n");
      out.write("         \t\t} else if('dept' == text) {\n");
      out.write("         \t\t\ttext = 'Department';\n");
      out.write("         \t\t}\n");
      out.write("     \t\t\tif (confirm(\"Are you sure delete this \" + text + \"?\")) {\n");
      out.write("     \t\t\t\twindow.location = link;\t  \n");
      out.write("     \t\t\t}\n");
      out.write("         \t}\n");
      out.write("     \t</script>\n");
      out.write("    </head>\n");
      out.write("<body background=\"styles/background.jpg\">\n");
      out.write("<br />\n");
      out.write("       <p>\n");
      out.write("<div id=\"menu\">\n");
      out.write("    ");
      out.write("    <p>\r\n");
      out.write("    <div class=\"menu\">\r\n");
      out.write("    <table>\r\n");
      out.write("    \t<tr>\r\n");
      out.write("    \t\t<td><a id=\"menu\" href=\"index.jsp\"><span>HOME</span></a></td>\r\n");
      out.write("    \t\t<td><a id=\"menu\" href=\"search.jsp?search=emp\"><span>Employees</span></a></td>\r\n");
      out.write("    \t\t<td><a id=\"menu\" href=\"search.jsp?search=dept\"><span>Departments</span></a></td>\r\n");
      out.write("    \t</tr>\r\n");
      out.write("    </table>\r\n");
      out.write("    </p>");
      out.write("\n");
      out.write("</div>\n");
      out.write("\n");
      out.write(" <div class=\"main\">\n");
      out.write("    <div class=\"viewer\">\n");
      out.write("        ");
      out.write("\r\n");
      out.write("\r\n");
 List<Packet> links = (List<Packet>) request.getAttribute("links");
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
	}
      out.write("\r\n");
      out.write("\r\n");
      out.write("<br />");
      out.write("\n");
      out.write("        <br />\n");
      out.write("        <br />\n");
      out.write("       ");

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
       	  	
      out.write("\n");
      out.write("       \t  \t<table border=\"0\">\n");
      out.write("       \t  \t\t");
 	for(Packet att: parent) { 
      out.write("\n");
      out.write("       \t  \t\t<tr>\n");
      out.write("       \t  \t\t\t<td width=\"60%\"><b>");
      out.print( att.getTitle() );
      out.write("</b> </td>\n");
      out.write("       \t  \t\t\t<td>");
      out.print( att.getValue() );
      out.write(" </td>\n");
      out.write("       \t  \t\t</tr>\n");
      out.write("       \t  \t\t");
} 
      out.write("\n");
      out.write("       \t\t\t\n");
      out.write("       \t  \t</table>\n");
      out.write("       \t  \t<table>\n");
      out.write("       \t  \t<tr>\n");
      out.write("       \t  \t\t<form>\n");
      out.write("       \t  \t\t\t<input type=\"hidden\" name=\"edit");
      out.print( parentEntity );
      out.write("\" value=\"");
      out.print( ((Packet)parent.get(0)).getValue() );
      out.write("\" />\n");
      out.write("       \t  \t\t\t<td><input type=\"submit\" value=\"Edit\" /></td>\n");
      out.write("       \t  \t\t</form>\n");
      out.write("       \t  \t");
 }
      out.write("\n");
      out.write("       \t  \t");
 if(entity != null) {
      out.write("\n");
      out.write("       \t  \t<form>\n");
      out.write("       \t  \t\t");
if(parent != null) {
      out.write("\n");
      out.write("       \t  \t\t<input type=\"hidden\" name=\"deptID\" value=\"");
      out.print( dept );
      out.write("\" />\n");
      out.write("       \t  \t\t<input type=\"hidden\" name=\"parentID\" value=\"");
      out.print( mgr );
      out.write("\" />\n");
      out.write("       \t  \t\t");
} 
      out.write("\n");
      out.write("       \t  \t\t<td><input type=\"submit\" name=\"add");
      out.print( entity );
      out.write("\" value=\"Add new ");
      out.print( entity );
      out.write("\" /></td>\n");
      out.write("       \t  \t</form>\n");
      out.write("       \t  \t</tr>\n");
      out.write("       \t  \t</table>\n");
      out.write("       \t  \t");
 } 
      out.write("\n");
      out.write("       \t  \t<br />\n");
      out.write("       <p />\n");
      out.write("                \t");
  
                    List<List<Packet> > res = (List<List<Packet> >)request.
                            getAttribute("result");
                	                	
                    if(res != null && entity != null) {
                    
      out.write("\n");
      out.write("                    <table cellpadding=\"10\" border=\"1\">\n");
      out.write("                        \t");
 for(int k = 0; k < res.size(); k++) { 
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
                                	if(k != 0) { 
      out.write("\n");
      out.write("                                \t<td class=\"wide\"><a href=\"#\" onClick=\"confirmDelete('");
      out.print( entity);
      out.write("', 'Processor?delete");
      out.print( entity + "=" + lst.get(0).getTitle());
      out.write("')\">\n");
      out.write("                                \t\tDelete</a></td>\n");
      out.write("                                \t");
 }
      out.write("\n");
      out.write("                                </tr>\n");
      out.write("                                ");
 }
      out.write("\n");
      out.write("                            <!--</span>\n");
      out.write("                        </li>-->\n");
      out.write("                \t\t\t\n");
      out.write("                    </table>\n");
      out.write("                ");
    	} else { 
      out.write("\n");
      out.write("                        <br /><a href=\"Processor?showcompany\">Company</a>\n");
      out.write("                ");
  	} 
      out.write("\n");
      out.write("         \n");
      out.write("         </div>\n");
      out.write("    </div>    \n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
