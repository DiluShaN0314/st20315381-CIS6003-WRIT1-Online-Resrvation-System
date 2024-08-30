package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import model.Reservation;

public final class reservation_002dform_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>Reservation Form</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"reservation-form.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h2>Reservation Form</h2>\n");
      out.write("    <form action=\"reservation\" method=\"post\">\n");
      out.write("        <input type=\"hidden\" name=\"id\" value=\"");
      out.print( request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getId() : "" );
      out.write("\">\n");
      out.write("        <label>User ID:</label>\n");
      out.write("        <input type=\"text\" name=\"user_id\" value=\"");
      out.print( request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getUserId() : "" );
      out.write("\" required>\n");
      out.write("        <br>\n");
      out.write("        <label>Table ID:</label>\n");
      out.write("        <input type=\"text\" name=\"table_id\" value=\"");
      out.print( request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getTableId() : "" );
      out.write("\" required>\n");
      out.write("        <br>\n");
      out.write("        <label>Status:</label>\n");
      out.write("        <input type=\"text\" name=\"status\" value=\"");
      out.print( request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getStatus() : "" );
      out.write("\" required>\n");
      out.write("        <br>\n");
      out.write("        <label>Reservation Date:</label>\n");
      out.write("        <input type=\"date\" name=\"reservation_date\" value=\"");
      out.print( request.getAttribute("reservation") != null ? ((Reservation) request.getAttribute("reservation")).getReservationDate() : "" );
      out.write("\" required>\n");
      out.write("        <br>\n");
      out.write("        <input type=\"submit\" name=\"action\" value=\"");
      out.print( request.getAttribute("reservation") != null ? "update" : "insert" );
      out.write("\">\n");
      out.write("    </form>\n");
      out.write("    <a href=\"reservation\">Back to List</a>\n");
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
