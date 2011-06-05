package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import data.ProjectProposal;
import data.DummyDatabase;
import data.Person;

public final class showProject_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"wfapp.css\" />\r\n");
      out.write("<title>Projektvorschlag ansehen</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<a href=\"listAllDepartments.jsp\">Alle Themenvorschläge</a>&nbsp;|&nbsp;\r\n");
      out.write("\t\t<a href=\"listOwnDepartment.jsp\">eigene Themenvorschlaege</a> &nbsp;|&nbsp; \r\n");
      out.write("\t\t<a href=\"newProject.html\">neuer Vorschlag</a> &nbsp;|&nbsp; \r\n");
      out.write("\t</p>\r\n");
      out.write("\t\r\n");
      out.write("\t");

			data.DummyDatabase db = data.DummyDatabase.getInstance();
			String projectID = request.getParameter("projectID");
			ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
			ProjectProposal projectToShow = null;
			for (ProjectProposal project : projectProposals){
				if (project.toString().equals(projectID)){
					projectToShow = project;
				}
			}
			
			
			ArrayList<Person> contactPersons = new ArrayList<Person>();
		
      out.write(" \r\n");
      out.write("\t\r\n");
      out.write("\t<h2>");
      out.print( projectToShow.getProjectName());
      out.write("</h2>\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Projektbeschreibung: </h4>\r\n");
      out.write("\t\t");
      out.print( projectToShow.getProjectDescription());
      out.write("\r\n");
      out.write("\t</p>\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Projektpartner: </h4>\r\n");
      out.write("\t\t");
      out.print( projectToShow.getPartnerDescription());
      out.write("\r\n");
      out.write("\t</p>\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Eckdaten: </h4>\r\n");
      out.write("\t\tAn dem Projekt können ");
      out.print( projectToShow.getMinStud());
      out.write(" bis ");
      out.print( projectToShow.getMaxStud());
      out.write(" Studenten teilnehmen.<br />\r\n");
      out.write("\t\tVoraussichtlicher Projektbeginn ist der ");
      out.print(projectToShow.getEstimatedBegin());
      out.write("\r\n");
      out.write("\t</p>\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Kontakt: </h4>\r\n");
      out.write("\t\t");
      out.print( projectToShow.getDepartment().getName());
      out.write("\r\n");
      out.write("\t\t<ul>\r\n");
      out.write("\t\t\t");
 if (projectToShow.getDepartment().getProf() == null) {
      out.write("\r\n");
      out.write("\t\t\t\t<li>bisher kein Professor zuständig</li>\r\n");
      out.write("\t\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<li> ");
      out.print( projectToShow.getDepartment().getProf().getName() );
      out.write(',');
      out.write(' ');
      out.print( projectToShow.getDepartment().getProf().getEmail() );
      out.write("</li>\r\n");
      out.write("\t\t\t");
 	contactPersons = projectToShow.getContactPersons();
				for (Person person : contactPersons){ 
      out.write("\r\n");
      out.write("\t\t\t\t<li> ");
      out.print(person.getName());
      out.write(',');
      out.write(' ');
      out.print( person.getEmail() );
      out.write(" </li>\t\r\n");
      out.write("\t\t\t\t");
 }} 
      out.write("\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\r\n");
      out.write("\t</p>\r\n");
      out.write("\r\n");
      out.write("<p>\r\n");
      out.write("Dieser Projektvorschlag wurde zuletzt am ");
      out.print(  projectToShow.getLastModifiedAt());
      out.write(" von ");
      out.print( projectToShow.getLastModifiedBy().getName() );
      out.write(" geändert.\r\n");
      out.write("</p>\r\n");
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
