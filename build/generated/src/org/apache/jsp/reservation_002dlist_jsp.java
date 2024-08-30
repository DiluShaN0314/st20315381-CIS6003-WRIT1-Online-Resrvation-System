package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import model.Reservation;

public final class reservation_002dlist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>Reservation List</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h2>Reservation List</h2>\n");
      out.write("    <a href=\"reservation?action=new\">Add New Reservation</a>\n");
      out.write("    <table>\n");
      out.write("        <thead>\n");
      out.write("            <tr>\n");
      out.write("                <th>ID</th>\n");
      out.write("                <th>User ID</th>\n");
      out.write("                <th>Table ID</th>\n");
      out.write("                <th>Status</th>\n");
      out.write("                <th>Reservation Date</th>\n");
      out.write("                <th>Actions</th>\n");
      out.write("            </tr>\n");
      out.write("        </thead>\n");
      out.write("        <tbody>\n");
      out.write("            ");

                List<Reservation> listReservation = (List<Reservation>) request.getAttribute("listReservation");
                if (listReservation != null) {
                    for (Reservation reservation : listReservation) {
            
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td>");
      out.print( reservation.getId() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( reservation.getUserId() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( reservation.getTableId() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( reservation.getStatus() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( reservation.getReservationDate() );
      out.write("</td>\n");
      out.write("                <td>\n");
      out.write("                    <a href=\"reservation?action=edit&id=");
      out.print( reservation.getId() );
      out.write("\">Edit</a>\n");
      out.write("                    <a href=\"reservation?action=delete&id=");
      out.print( reservation.getId() );
      out.write("\" onclick=\"return confirm('Are you sure?');\">Delete</a>\n");
      out.write("                </td>\n");
      out.write("            </tr>\n");
      out.write("            ");

                    }
                }
            
      out.write("\n");
      out.write("        </tbody>\n");
      out.write("    </table>\n");
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
