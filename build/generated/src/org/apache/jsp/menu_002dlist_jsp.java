package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import model.Menu;

public final class menu_002dlist_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("    <title>Menu List</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/styles.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h2>Menu List</h2>\n");
      out.write("    <a href=\"MenuController?action=new\" style=\"display: inline-block; padding: 10px 20px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px;\">Add New Menu Item</a>\n");
      out.write("    <div class=\"grid-container\">\n");
      out.write("        ");

            List<Menu> listMenu = (List<Menu>) request.getAttribute("listMenu");
            if (listMenu != null) {
                for (Menu menu : listMenu) {
                    String imagePath = menu.getImagePath();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        imagePath = "uploads/" + imagePath;
                    } else {
                        imagePath = "path/to/default/image.jpg"; // Placeholder image if none exists
                    }
        
      out.write("\n");
      out.write("        <div class=\"grid-item\">\n");
      out.write("            <img src=\"");
      out.print( imagePath );
      out.write("\" alt=\"");
      out.print( menu.getName() );
      out.write("\">\n");
      out.write("            <h3>");
      out.print( menu.getName() );
      out.write("</h3>\n");
      out.write("            <p><strong>Category:</strong> ");
      out.print( menu.getCategory() );
      out.write("</p>\n");
      out.write("            <p><strong>Description:</strong> ");
      out.print( menu.getDescription() );
      out.write("</p>\n");
      out.write("            <p><strong>Price:</strong> $");
      out.print( menu.getPrice() );
      out.write("</p>\n");
      out.write("            <a href=\"MenuController?action=edit&id=");
      out.print( menu.getId() );
      out.write("\">Edit</a>\n");
      out.write("            <a href=\"MenuController?action=delete&id=");
      out.print( menu.getId() );
      out.write("\" onclick=\"return confirm('Are you sure?');\">Delete</a>\n");
      out.write("        </div>\n");
      out.write("        ");

                }
            }
        
      out.write("\n");
      out.write("    </div>\n");
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
