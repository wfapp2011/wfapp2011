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
      out.write("\t<style type=\"text/css\">\r\n");
      out.write("\t\t.alignTop    { vertical-align:top }\r\n");
      out.write("\t\t.inputForm  {width: 70%; min-width:500px;}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t.formRow\t{padding-bottom:30px;}\r\n");
      out.write("\t\t.formTextinput {width:50%;}\t\t\r\n");
      out.write("\t\t.formTextinputPerson {width: 40%}\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t.formLabel {width: 15%; float: left; vertical-align:top; text-align:left; }\r\n");
      out.write("\t\t.formInput {width: 85%; float:right}\r\n");
      out.write("\t\t.formEditorButtons {width: 100%; text-align:right; float:right}\r\n");
      out.write("\t\t.formButtons {float: right}\r\n");
      out.write("\t</style>\r\n");
      out.write("\r\n");
      out.write("\t<!-- CKEditor -->\r\n");
      out.write("\t<script type=\"text/javascript\" src=\"ckeditor/ckeditor.js\"></script>\r\n");
      out.write("\t<script src=\"sample.js\" type=\"text/javascript\"></script>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" language=\"javascript\"><!--\r\n");
      out.write("<!--\r\n");
      out.write("//Add more fields dynamically.\r\n");
      out.write("function addContactPerson() {\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\tvar MyElement = document.getElementById(\"contactPersonName_1\");\r\n");
      out.write("    MyElement.value = \"If you see this, it worked!\";\r\n");
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\tif(!document.getElementById) return; //Prevent older browsers from getting any further.\r\n");
      out.write("\tvar field_area = document.getElementById(\"contactPersons\");\r\n");
      out.write("\tvar all_inputs = field_area.getElementsByTagName(\"input\"); //Get all the input fields in the given area.\r\n");
      out.write("\t//Find the count of the last element of the list. It will be in the format '<field><number>'. If the \r\n");
      out.write("\t//\t\tfield given in the argument is 'friend_' the last id will be 'friend_4'.\r\n");
      out.write("\tvar last_item = all_inputs.length - 1;\r\n");
      out.write("\tvar last = all_inputs[last_item].id;\r\n");
      out.write("\tvar count = Number(last.split(\"_\")[1]) + 1;\r\n");
      out.write("\t\r\n");
      out.write("\t//If the maximum number of elements have been reached, exit the function.\r\n");
      out.write("\t//\t\tIf the given limit is lower than 0, infinite number of fields can be created.\r\n");
      out.write(" \t\r\n");
      out.write("\tif(document.createElement) { //W3C Dom method.\r\n");
      out.write("\t\tvar div = document.createElement(\"div\");\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar nameInput = document.createElement(\"input\");\r\n");
      out.write("\t\tnameInput.type = \"text\"; //Type of field - can be any valid input type like text,file,checkbox etc.\r\n");
      out.write("\t\tnameInput.name = \"contactPersonName_\"+count;\r\n");
      out.write("\t\tnameInput.id = \"contactPersonName_\"+count;\r\n");
      out.write("\t\tnameInput.setAttribute(\"value\", \"name\");\r\n");
      out.write("\t\tdiv.appendChild(nameInput);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tvar emailInput = document.createElement(\"input\");\r\n");
      out.write("\t\temailInput.type = \"text\";\r\n");
      out.write("\t\temailInput.name = \"contactPersonEmail_\"+count;\r\n");
      out.write("\t\temailInput.id = \"contactPersonEmail_\"+count;\r\n");
      out.write("\t\temailInput.setAttribute(\"value\", \"mail\");\r\n");
      out.write("\t\tdiv.appendChild(emailInput);\r\n");
      out.write("\t\t\r\n");
      out.write("\t\r\n");
      out.write("\t\tvar img = document.createElement(\"img\");\r\n");
      out.write("\t\timg.src = \"img/trash.gif\";\r\n");
      out.write("\t\timg.setAttribute(\"onclick\", \"this.parentNode.parentNode.removeChild(this.parentNode);\");\r\n");
      out.write("\t\tdiv.appendChild(img);\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tfield_area.appendChild(div);\r\n");
      out.write("\t} else { //Older Method\r\n");
      out.write("\t\tfield_area.innerHTML += \"<div><input name='\"+(field+count)+\"' div='\"+(field+count)+\"' type='text' /></div>\";\r\n");
      out.write("\t};\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("//--></script>\r\n");
      out.write("\t\r\n");
      out.write("\t<title>Projektvorschlag erstellen</title>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body onload=\"createEditor('partnerDescrEditor');createEditor('projectDescrEditor');\">\r\n");
      out.write("\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<a href=\"listAllDepartments.jsp\">Alle Themenvorschläge</a>&nbsp;|&nbsp;\r\n");
      out.write("\t\t<a href=\"listOwnDepartment.jsp\">eigene Themenvorschläge</a> &nbsp;|&nbsp; \r\n");
      out.write("\t\t<a href=\"createProject.jsp\">neuer Vorschlag</a> &nbsp;|&nbsp; \r\n");
      out.write("\t</p>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t<h2>Neues Projekt erstellen</h2>\r\n");
      out.write("\r\n");
      out.write("\t<form name=\"input\" method=\"get\" action=\"createProject\">\r\n");
      out.write("\t\t\r\n");
      out.write("\t<div class=\"inputForm\">\t\r\n");
      out.write("\t\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Projektname:</span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"><input  class=\"formTextinput\" type=\"text\" name=\"projectName\" /></span> \r\n");
      out.write("\t\t</div>\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<span class=\"formLabel\">Beschreibung: </span>\r\n");
      out.write("\t\t\t<span class=\"formInput\"> \r\n");
      out.write("\t\t\t\t\t<textarea id=\"projectDescription\" name=\"projectDescription\"></textarea>\r\n");
      out.write("\t\t\t\t\t<script type=\"text/javascript\">\r\n");
      out.write("\t\t\t\t\t\tCKEDITOR.replace( 'projectDescription' );\r\n");
      out.write("\t\t\t\t\t</script>\r\n");
      out.write("\t\t\t</span>\r\n");
      out.write("\t\t</div>\r\n");
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
      out.write("\t\t\t<span class=\"formInput\"><input type=\"text\" name=\"estimatedBegin\" /></span> \r\n");
      out.write("\t\t</div>\t\r\n");
      out.write("\r\n");
      out.write("\t\t<div class=\"formRow\">\r\n");
      out.write("\t\t\t<div>\r\n");
      out.write("\t\t\t\t<span class=\"formLabel\">Projektbetreuer:</span>\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t<div id=\"contactPersons\">\r\n");
      out.write("\t\t\t\t\t<div>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" name=\"contactPersonName_1\" id=\"contactPersonName_1\" value=\"name\"/>\r\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" name=\"contactPersonEmail_1\" id=\"contactPersonEmail_1\" value=\"mail\"/>\r\n");
      out.write("\t\t\t\t\t\t<img src=\"img/trash.gif\" onClick=\"this.parentNode.parentNode.removeChild(this.parentNode);\"/>\t\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t    <div>\r\n");
      out.write("\t\t\t    \t<input type=\"button\" value=\"Person hinzufügen\" onclick=\"addContactPerson();\" />\r\n");
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
