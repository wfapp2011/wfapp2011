package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;

public final class createProject_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.List<java.lang.String> _jspx_dependants;

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
      response.setContentType("text/html; charset=ISO-8859-1");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("\t<link rel=\"stylesheet\" type=\"text/css\" href=\"wfapp.css\" />\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- jQuery-UI for datepicker -->\r\n");
      out.write("\t<link type=\"text/css\" href=\"jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css\" rel=\"stylesheet\" />\t\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"jquery-ui-1.8.13.custom/js/jquery-1.5.1.min.js\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js\"></script>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t<!-- CKEditor for wikiedit -->\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"ckeditor/ckeditor.js\"></script>\r\n");
      out.write("\t<script src=\"sample.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\t\r\n");
      out.write("\t<!-- will be refactored and transferred to external css -->\r\n");
      out.write("\t<style type=\"text/css\">\r\n");
      out.write("\t\t.alignTop    { vertical-align:top }\r\n");
      out.write("\t\t.inputForm  {width: 70%; min-width:500px;}\t\t\r\n");
      out.write("\t\t.formRow\t{padding-bottom:30px; clear: both;}\r\n");
      out.write("\t\t.formTextinput {width:50%;}\t\t\r\n");
      out.write("\t\t.formTextinputPerson {width: 40%}\t\r\n");
      out.write("\t\t.formLabel {width: 15%;  text-align:left; float:left}\r\n");
      out.write("\t\t.formInput {width: 85%; float:right; }\r\n");
      out.write("\t\t.formEditorButtons {width: 100%; text-align:right; float:right}\r\n");
      out.write("\t\t.formButtons {float: right}\r\n");
      out.write("\t\t.formInputContactPerson {width: 25%; color:grey; margin-right: 1%;}\r\n");
      out.write("\t\t.formInputAddPerson {width: 54%; float:right;}\r\n");
      out.write("\t</style>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("\t$(function() {\r\n");
      out.write("\t\t$( \"#datepicker\" ).datepicker({\r\n");
      out.write("\t\t\tshowOn: \"button\",\r\n");
      out.write("\t\t\tbuttonImage: \"img/calendar.gif\",\r\n");
      out.write("\t\t\tbuttonImageOnly: true\r\n");
      out.write("\t\t});\t\r\n");
      out.write("\t\t$( \"#datepicker\" ).datepicker( \"option\", \"dateFormat\", \"yy-mm-dd\");\r\n");
      out.write("\t});\t\t\t\r\n");
      out.write("</script>\t\r\n");
      out.write("<script type=\"text/javascript\" language=\"javascript\">\r\n");
      out.write("<!--\r\n");
      out.write("\tfunction addContactPerson() {\r\n");
      out.write("\r\n");
      out.write("\t\tif(!document.getElementById) return;\r\n");
      out.write("\t\tvar field_area = document.getElementById(\"contactPersons\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//Get all input fields in the given area and find count of last field (= highest count)\r\n");
      out.write("\t\t//note: when fields are deleted, the highest count might be higher than the number of fields\r\n");
      out.write("\t\tvar all_inputs = field_area.getElementsByTagName(\"input\"); \r\n");
      out.write("\t\tvar length = all_inputs.length;\r\n");
      out.write("\t\tvar count = 1;\r\n");
      out.write("\t\tif (length > 0){\r\n");
      out.write("\t\t\tvar last = all_inputs[length-1].id;\r\n");
      out.write("\t\t\tcount = Number(last.split(\"_\")[1]) + 1;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(document.createElement) { \r\n");
      out.write("\t\t\tvar div = document.createElement(\"div\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t//Create textinput for \"name\"\r\n");
      out.write("\t\t\tvar nameInput = document.createElement(\"input\");\r\n");
      out.write("\t\t\tnameInput.setAttribute(\"type\", \"text\");\r\n");
      out.write("\t\t\tnameInput.setAttribute(\"name\", \"contactPersonName_\"+count);\r\n");
      out.write("\t\t\tnameInput.setAttribute(\"id\", \"contactPersonName_\"+count);\r\n");
      out.write("\t\t\tnameInput.setAttribute(\"value\", \"Name\");\r\n");
      out.write("\t\t\tnameInput.setAttribute(\"onclick\", \"this.value='';\");\r\n");
      out.write("\t\t\tnameInput.setAttribute(\"class\", \"formInputContactPerson\");\r\n");
      out.write("\t\t\tdiv.appendChild(nameInput);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//Create textinput for \"email\"\r\n");
      out.write("\t\t\tvar emailInput = document.createElement(\"input\");\r\n");
      out.write("\t\t\temailInput.setAttribute(\"type\", \"text\");\r\n");
      out.write("\t\t\temailInput.setAttribute(\"name\", \"contactPersonEmail_\"+count);\r\n");
      out.write("\t\t\temailInput.setAttribute(\"id\", \"contactPersonEmail_\"+count);\r\n");
      out.write("\t\t\temailInput.setAttribute(\"value\", \"E-Mail-Adresse\");\r\n");
      out.write("\t\t\temailInput.setAttribute(\"onclick\", \"this.value='';\");\r\n");
      out.write("\t\t\temailInput.setAttribute(\"class\", \"formInputContactPerson\");\r\n");
      out.write("\t\t\tdiv.appendChild(emailInput);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//Create trash to delete items\r\n");
      out.write("\t\t\tvar img = document.createElement(\"img\");\r\n");
      out.write("\t\t\timg.setAttribute(\"src\", \"img/trash.gif\");\r\n");
      out.write("\t\t\timg.setAttribute(\"onclick\", \"removeContactPerson(this.parentNode);\");\r\n");
      out.write("\t\t\tdiv.appendChild(img);\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t//add div\r\n");
      out.write("\t\t\tfield_area.appendChild(div);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//set counter to highest count\r\n");
      out.write("\t\t\tvar myCounter = document.getElementById(\"maxNumberOfContactPersons\");\r\n");
      out.write("\t\t\t//myCounter.setAttribute(\"value\", Number(myCounter.value) + 1);\r\n");
      out.write("\t\t\tmyCounter.setAttribute(\"value\", count);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t} else {\r\n");
      out.write("\t\t\tfield_area.innerHTML += \"<div> Dynamisches Erstellen von Elementen wird von Ihrem Browser nicht unterstützt. </div>\";\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("\r\n");
      out.write("\tfunction removeContactPerson(node){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//remove div\r\n");
      out.write("\t\tnode.parentNode.removeChild(node);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t//decrement counter\r\n");
      out.write("\t\t//var myCounter = document.getElementById(\"numberOfContactPersons\");\r\n");
      out.write("\t\t//myCounter.setAttribute('value', Number(myCounter.value) - 1);\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("//--></script>\r\n");
      out.write("\t\r\n");
      out.write("<title>Projektvorschlag erstellen</title>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"addContactPerson(); addContactPerson();\">\r\n");
      out.write("\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<a href=\"listAllDepartments.jsp\">Alle Themenvorschläge</a>&nbsp;|&nbsp;\r\n");
      out.write("\t\t<a href=\"listOwnDepartment.jsp\">eigene Themenvorschläge</a> &nbsp;|&nbsp; \r\n");
      out.write("\t\t<a href=\"createProject.jsp\">neuer Vorschlag</a>\r\n");
      out.write("\t</p>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t<h2>Neues Projekt erstellen</h2>\r\n");
      out.write("\r\n");
      out.write("\t<form name=\"input\" method=\"get\" action=\"createProject\">\t\t\r\n");
      out.write("\t<div class=\"inputForm\">\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Projektname:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\">\r\n");
      out.write("\t\t\t\t<input  class=\"formTextinput\" type=\"text\" name=\"projectName\" />\r\n");
      out.write("\t\t\t</span>\r\n");
      out.write("\t\t</div> \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Beschreibung: </span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"> \r\n");
      out.write("\t\t\t\t<textarea id=\"projectDescription\" name=\"projectDescription\"></textarea>\r\n");
      out.write("\t\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\t\tCKEDITOR.replace( 'projectDescription' );\r\n");
      out.write("\t\t\t\t</script>\r\n");
      out.write("\t\t\t</span>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Keywords:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"><input class=\"formTextinput\" type=\"text\" name=\"keywords\"/></span> \r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Partnername:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"><input class=\"formTextinput\" type=\"text\" name=\"partnerName\" /></span> \r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Partnerbeschreibung: </span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"> \r\n");
      out.write("\t\t\t\t<textarea id=\"partnerDescription\" name=\"partnerDescription\"></textarea>\r\n");
      out.write("\t\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\t\tCKEDITOR.replace( 'partnerDescription' );\r\n");
      out.write("\t\t\t\t</script>\r\n");
      out.write("\t\t\t</span>\t\t\t\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">vorauss. Beginn:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\">\r\n");
      out.write("\t\t\t\t<input type=\"text\" name=\"estimatedBegin\" id=\"datepicker\"/>\r\n");
      out.write("\t\t\t</span> \r\n");
      out.write("\t\t</div>\t\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<div>\r\n");
      out.write("\t\t\t\t<span class=\"formLabel\">Projektbetreuer:</span>\t\t\t\t\r\n");
      out.write("\t\t\t\t<div id=\"contactPersons\" class=\"formInput\">\r\n");
      out.write("\t\t\t\t\t<!-- items are generated via javascript -->\t\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t    <div class=\"formInputAddPerson\">\r\n");
      out.write("\t\t\t    \t<input type=\"button\" value=\"Person hinzufügen\" onclick=\"addContactPerson();\" value=\"0\" />\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"maxNumberOfContactPersons\" id=\"maxNumberOfContactPersons\">\t\r\n");
      out.write("\t\t\t\t</div>\t\r\n");
      out.write("\t\t\t</div>\t\r\n");
      out.write("\t\t</div>\t\r\n");
      out.write("            \t\t\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Teamgroesse:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\">\r\n");
      out.write("\t\t\t\t<select name=\"minStud\">\r\n");
      out.write("\t\t\t\t\t\t<option value=1>1</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=2>2</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=3 selected=\"selected\">3</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=4>4</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=5>5</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=6>6</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=7>7</option>\r\n");
      out.write("\t\t\t\t</select>\r\n");
      out.write("\t\t\t\tbis\r\n");
      out.write("\t\t\t\t<select name=\"maxStud\">\r\n");
      out.write("\t\t\t\t\t\t<option value=4>4</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=5>5</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=6>6</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=7 selected=\"selected\">7</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=8>8</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=9>9</option>\r\n");
      out.write("\t\t\t\t\t\t<option value=10>10</option>\r\n");
      out.write("\t\t\t\t</select> \r\n");
      out.write("\t\t\t\tStudenten\r\n");
      out.write("\t\t\t</span>\r\n");
      out.write("\t\t</div>\t\t\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Projektdatei:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"><input type=\"file\" name=\"projectFile\"/></span> \r\n");
      out.write("\t\t</div>\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">weitere Dateien:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"><input type=\"file\" name=\"additionalFiles\" /></span> \r\n");
      out.write("\t\t</div>\t\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"formButtons\">\r\n");
      out.write("\t\t\t<span><input type=\"submit\" value=\"Speichern\" /></span>\r\n");
      out.write("\t\t\t<span><input type=\"reset\" value=\"Verwerfen\" /></span>\r\n");
      out.write("\t\t\t<span><input type=\"button\" value=\"PDF erzeugen\" /></span>\t\t\r\n");
      out.write("\t\t</div>\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t</div>\r\n");
      out.write("\t</form>\r\n");
      out.write("\t\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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
