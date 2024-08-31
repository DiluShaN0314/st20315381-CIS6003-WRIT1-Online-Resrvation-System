package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import model.Role;
import model.User;

public final class user_002dform_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>User Form</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"user-form.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h2>User Form</h2>\n");
      out.write("    <form action=\"UserController\" method=\"post\">\n");
      out.write("        <input type=\"hidden\" name=\"id\" value=\"");
      out.print( request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getId() : "" );
      out.write("\">\n");
      out.write("        \n");
      out.write("        <label>Name:</label>\n");
      out.write("        <input type=\"text\" name=\"name\" value=\"");
      out.print( request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getName() : "" );
      out.write("\" required>\n");
      out.write("        <br>\n");
      out.write("        \n");
      out.write("        <label>Email:</label>\n");
      out.write("        <input type=\"email\" name=\"email\" value=\"");
      out.print( request.getAttribute("user") != null ? ((User) request.getAttribute("user")).getEmail() : "" );
      out.write("\" required>\n");
      out.write("        <br>\n");
      out.write("        \n");
      out.write("        <label>Role:</label>\n");
      out.write("        <select name=\"role\" required>\n");
      out.write("            <option value=\"\">Select a role</option>\n");
      out.write("            ");
 
            List<Role> roles = (List<Role>) request.getAttribute("roles"); 
            if (roles != null) {
                User user = (User) request.getAttribute("user");
                int userRoleID = (user != null) ? user.getRoleId() : -1; // Extract role as int
                for (Role role : roles) { 
            
      out.write("\n");
      out.write("                <option value=\"");
      out.print( role.getRoleID() );
      out.write('"');
      out.write(' ');
      out.print( (role.getRoleID() == userRoleID) ? "selected" : "" );
      out.write(">\n");
      out.write("                    ");
      out.print( role.getRoleName() );
      out.write("\n");
      out.write("                </option>\n");
      out.write("            ");
 
                } 
            } 
            
      out.write("\n");
      out.write("        </select>\n");
      out.write("        <br>\n");
      out.write("        \n");
      out.write("        <input type=\"submit\" name=\"action\" value=\"");
      out.print( request.getAttribute("user") != null ? "Update" : "Insert" );
      out.write("\">\n");
      out.write("    </form>\n");
      out.write("    <a href=\"UserController\">Back to List</a>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
