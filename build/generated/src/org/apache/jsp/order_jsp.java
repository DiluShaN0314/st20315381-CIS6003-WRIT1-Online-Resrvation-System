package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Map;
import model.User;
import java.util.List;
import model.Menu;
import model.Table;
import model.Order;

public final class order_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>Create Order</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/order.css\">\n");
      out.write("    <style>\n");
      out.write("        .item-row {\n");
      out.write("            display: flex;\n");
      out.write("            align-items: center;\n");
      out.write("            margin-bottom: 10px;\n");
      out.write("            padding: 10px;\n");
      out.write("            border: 1px solid #ddd;\n");
      out.write("            border-radius: 4px;\n");
      out.write("            box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .item-row img {\n");
      out.write("            width: 50px; /* Mini size */\n");
      out.write("            height: auto;\n");
      out.write("            margin-right: 10px;\n");
      out.write("            border-radius: 4px;\n");
      out.write("        }\n");
      out.write("        \n");
      out.write("        .item-row span {\n");
      out.write("            flex-grow: 1;\n");
      out.write("        }\n");
      out.write("    </style>\n");
      out.write("    <script>\n");
      out.write("        function calculateTotal() {\n");
      out.write("            var total = 0;\n");
      out.write("            var items = document.querySelectorAll(\".item-row\");\n");
      out.write("            items.forEach(function(item) {\n");
      out.write("                var quantity = item.querySelector(\".quantity\").value;\n");
      out.write("                var price = parseFloat(item.querySelector(\".price\").textContent.replace('$', ''));\n");
      out.write("                total += quantity * price;\n");
      out.write("            });\n");
      out.write("            document.getElementById(\"total\").textContent = '$' + total.toFixed(2);\n");
      out.write("        }\n");
      out.write("\n");
      out.write("        function toggleTableSelection(orderType) {\n");
      out.write("            document.getElementById(\"table-selection\").style.display = orderType === 'Dine-In' ? 'block' : 'none';\n");
      out.write("        }\n");
      out.write("    </script>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    ");
 User user = (session != null) ? (User) session.getAttribute("user") : null; 
        System.out.println("user id order : " +user.getId());
    
      out.write("\n");
      out.write("    <h2>Create Order</h2>\n");
      out.write("    <form action=\"OrderController\" method=\"post\">        \n");
      out.write("        <input type=\"hidden\" name=\"id\" value=\"");
      out.print( request.getAttribute("order") != null ? ((Order) request.getAttribute("order")).getId() : "" );
      out.write("\">\n");
      out.write("        <input type=\"hidden\" name=\"userID\" value=\"");
      out.print( user.getId() );
      out.write("\">\n");
      out.write("        <h3>Select Items</h3>\n");
      out.write("        <div id=\"menu-items\">\n");
      out.write("            ");

                List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
                if (menuList != null) {
                    for (Menu menu : menuList) {
                        String imagePath = menu.getImagePath();
                        if (imagePath != null && !imagePath.isEmpty()) {
                            imagePath = "uploads/" + imagePath;
                        } else {
                            imagePath = "path/to/default/image.jpg"; // Placeholder image if none exists
                        }
            
      out.write("\n");
      out.write("            <div class=\"item-row\">\n");
      out.write("                <img src=\"");
      out.print( imagePath );
      out.write("\" alt=\"");
      out.print( menu.getName() );
      out.write("\">\n");
      out.write("                <span>");
      out.print( menu.getName() );
      out.write(" - $");
      out.print( menu.getPrice() );
      out.write("</span>\n");
      out.write("                <input type=\"number\" name=\"quantities[");
      out.print( menu.getId() );
      out.write("]\" class=\"quantity\" \n");
      out.write("                    value=\"");
      out.print( (request.getAttribute("order") != null && ((Order) request.getAttribute("order")).getItemQuantities().get(menu.getId()) != null) ? 
                               ((Order) request.getAttribute("order")).getItemQuantities().get(menu.getId()) : 0 );
      out.write("\" \n");
      out.write("                    min=\"0\" onchange=\"calculateTotal()\">\n");
      out.write("                <span class=\"price\">$");
      out.print( menu.getPrice() );
      out.write("</span>\n");
      out.write("            </div>\n");
      out.write("            ");

                    }
                }
            
      out.write("\n");
      out.write("        </div>\n");
      out.write("        <h3>Order Type</h3>\n");
      out.write("        <select name=\"orderType\" onchange=\"toggleTableSelection(this.value)\">\n");
      out.write("            <option value=\"\">Select Order Type</option>\n");
      out.write("            <option value=\"Delivery\"\n");
      out.write("                    ");
      out.print( request.getAttribute("order") != null && 
                        "Delivery".equals(((Order) request.getAttribute("order")).getOrderType()) ? "selected" : "" );
      out.write(" >\n");
      out.write("                Delivery\n");
      out.write("           </option>\n");
      out.write("            <option value=\"Dine-In\"\n");
      out.write("                     ");
      out.print( request.getAttribute("order") != null && 
                        "Dine-In".equals(((Order) request.getAttribute("order")).getOrderType()) ? "selected" : "" );
      out.write(">\n");
      out.write("                Dine-In\n");
      out.write("            </option>\n");
      out.write("            <option value=\"Takeaway\"\n");
      out.write("                    ");
      out.print( request.getAttribute("order") != null && 
                        "Takeaway".equals(((Order) request.getAttribute("order")).getOrderType()) ? "selected" : "" );
      out.write(">\n");
      out.write("                Take-Away\n");
      out.write("            </option>\n");
      out.write("        </select>\n");
      out.write("        \n");
      out.write("        <div id=\"table-selection\" style=\"display:none;\">\n");
      out.write("            <h3>Select Table</h3>\n");
      out.write("            <select name=\"tableId\">\n");
      out.write("                <option value=\"\">Select a Table</option>\n");
      out.write("                ");

                    List<Table> tableList = (List<Table>) request.getAttribute("tableList");
                    if (tableList != null) {
                        Order order = (Order) request.getAttribute("order");
                        int tableID = (order != null) ? order.getTableId(): null; // Extract role as int
                        for (Table table : tableList) {
                
      out.write("\n");
      out.write("                <option value=\"");
      out.print( table.getId() );
      out.write('"');
      out.write(' ');
      out.print( (table.getId() == tableID) ? "selected" : "");
      out.write(">\n");
      out.write("                    ");
      out.print( table.getId() );
      out.write(" - Capacity: ");
      out.print( table.getCapacity() );
      out.write("\n");
      out.write("                </option>\n");
      out.write("                ");

                        }
                    }
                
      out.write("\n");
      out.write("            </select>\n");
      out.write("        </div>\n");
      out.write("        \n");
      out.write("        <h3>Total: <span id=\"total\">$0.00</span></h3>\n");
      out.write("        \n");
      out.write("        <input type=\"submit\" name=\"action\"  value=\"");
      out.print( request.getAttribute("order") != null ? "update" : "placeOrder" );
      out.write("\">\n");
      out.write("    </form>\n");
      out.write("</body>\n");
      out.write("<script>\n");
      out.write("    calculateTotal();\n");
      out.write("    toggleTableSelection(document.getElementsByName('orderType')[0].value);\n");
      out.write(" </script>\n");
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
