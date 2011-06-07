package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import java.text.DateFormat;
import java.util.Date;
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"wfapp.css\" />\r\n");
      out.write("<style type=\"text/css\">\r\n");
      out.write("\t\t.chapter    { font-weight:bold }\r\n");
      out.write("\t\t.invalidElement {font-style: italic}\r\n");
      out.write("\t\t.lastModifiedAnnotation {}\r\n");
      out.write("\t\t\r\n");
      out.write("</style>\r\n");
      out.write("<title>Projektvorschlag ansehen</title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<a href=\"listAllDepartments.jsp\">Alle Themenvorschläge</a>&nbsp;|&nbsp;\r\n");
      out.write("\t\t<a href=\"listOwnDepartment.jsp\">eigene Themenvorschläge</a> &nbsp;|&nbsp; \r\n");
      out.write("\t\t<a href=\"createProject.jsp\">neuer Vorschlag</a> &nbsp;|&nbsp; \r\n");
      out.write("\t</p>\r\n");
      out.write("\t\r\n");
      out.write("\t");

			DummyDatabase db = DummyDatabase.getInstance();
	
			String projectID = request.getParameter("projectID");
			ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
			ProjectProposal projectToShow = null;
			for (ProjectProposal project : projectProposals){
				if (project.toString().equals(projectID)){
					projectToShow = project;
				}
			}
			
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			
			ArrayList<Person> contactPersons = new ArrayList<Person>();
		
      out.write(" \r\n");
      out.write("\t\t\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t");
 if (projectToShow.getProjectName() != null && projectToShow.getProjectName().trim().length() > 0) {
      out.write("\r\n");
      out.write("\t\t\t<h2>");
      out.print( projectToShow.getProjectName());
      out.write("</h2>\t\t\r\n");
      out.write("\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<div class=\"invalidElement\"> Es wurde bisher kein Projektname angegeben. </div>\r\n");
      out.write("\t\t");
 } 
      out.write("\t\t\t\r\n");
      out.write("\t</p>\r\n");
      out.write("\t\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Projektbeschreibung: </h4>\r\n");
      out.write("\t\t");
 if (projectToShow.getProjectDescription() != null && projectToShow.getProjectDescription().trim().length() > 0) {
      out.write("\r\n");
      out.write("\t\t\t<h2>");
      out.print( projectToShow.getProjectDescription());
      out.write("</h2>\t\t\r\n");
      out.write("\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<div class=\"invalidElement\"> Es wurde bisher keine Projektbeschreibung angegeben. </div>\r\n");
      out.write("\t\t");
 } 
      out.write("\t\t\r\n");
      out.write("\t</p>\r\n");
      out.write("\t\t\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Projektpartner: \r\n");
      out.write("\t\t\t");
 if (projectToShow.getPartnerName() != null && projectToShow.getPartnerName().trim().length() > 0) {
      out.write("\r\n");
      out.write("\t\t\t\t");
      out.print( projectToShow.getPartnerName());
      out.write("\r\n");
      out.write("\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t</h4>\t\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
 if (projectToShow.getPartnerDescription() != null && projectToShow.getPartnerDescription().trim().length() > 0) {
      out.write("\r\n");
      out.write("\t\t\t");
      out.print( projectToShow.getPartnerDescription());
      out.write("\t\r\n");
      out.write("\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<div class=\"invalidElement\"> Es wurde bisher keine Partnerbeschreibung angegeben. </div>\r\n");
      out.write("\t\t");
 } 
      out.write("\t\t\r\n");
      out.write("\t</p>\r\n");
      out.write("\t\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Eckdaten: </h4>\r\n");
      out.write("\t\tAn dem Projekt können ");
      out.print( projectToShow.getMinStud());
      out.write(" bis ");
      out.print( projectToShow.getMaxStud());
      out.write(" Studenten teilnehmen.<br />\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
 if (projectToShow.getEstimatedBegin() != null) {
      out.write("\r\n");
      out.write("\t\t\tVoraussichtlicher Projektbeginn ist der ");
      out.print( df.format(projectToShow.getEstimatedBegin()));
      out.write(".\t\r\n");
      out.write("\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<div class=\"invalidElement\"> Der voraussichtliche Projektbeginn steht noch nicht fest. </div>\r\n");
      out.write("\t\t");
 } 
      out.write("\t\t\r\n");
      out.write("\t</p>\r\n");
      out.write("\t\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<h4> Kontakt: </h4>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
 if (projectToShow.getDepartment().getName() != null && projectToShow.getDepartment().getName().trim().length() > 0) {
      out.write("\r\n");
      out.write("\t\t\t");
      out.print( projectToShow.getDepartment().getName());
      out.write("\t\r\n");
      out.write("\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<div class=\"invalidElement\"> Beim Einlesen des Fachbereichs ist ein Fehler aufgetreten. </div>\r\n");
      out.write("\t\t");
 } 
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t\t<ul>\r\n");
      out.write("\t\t\t");
 if (projectToShow.getDepartment().getProf() == null) {
      out.write("\r\n");
      out.write("\t\t\t\t<li class=\"invalidElement\" >Bisher ist kein Professor zuständig.</li>\r\n");
      out.write("\t\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t\t");
 if (projectToShow.getDepartment().getProf().getName() != null && projectToShow.getDepartment().getProf().getEmail() != null ) {
      out.write("\t\t\t\r\n");
      out.write("\t\t\t\t\t<li> ");
      out.print( projectToShow.getDepartment().getProf().getName() );
      out.write(',');
      out.print( projectToShow.getDepartment().getProf().getEmail() );
      out.write("</li>\r\n");
      out.write("\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t");
 if (projectToShow.getContactPersons() != null) {
      out.write("\r\n");
      out.write("\t\t\t\t\t");
contactPersons = projectToShow.getContactPersons();
					for (Person person : contactPersons){ 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t<li>\r\n");
      out.write("\t\t\t\t\t\t\t");
 if (!(person.getName().trim().length() > 0)) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"invalidElement\"> kein Name angegeben, </span>\r\n");
      out.write("\t\t\t\t\t\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      out.print(person.getName());
      out.write(",\r\n");
      out.write("\t\t\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t");
 if (!(person.getEmail().trim().length() > 0)) {
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t<span class=\"invalidElement\"> keine E-Mail-Adresse angegeben </span>\r\n");
      out.write("\t\t\t\t\t\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t");
      out.print(person.getEmail());
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t\t\t\t </li>\t\r\n");
      out.write("\t\t\t\t");
 }}} 
      out.write("\r\n");
      out.write("\t\t</ul>\r\n");
      out.write("\t</p>\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("\t<p class=\"lastModifiedAnnotation\">\r\n");
      out.write("\t\tDieser Projektvorschlag wurde zuletzt \r\n");
      out.write("\t\t");
 if (projectToShow.getLastModifiedAt() != null) { 
      out.write("\r\n");
      out.write("\t\t\tam ");
      out.print( df.format(projectToShow.getLastModifiedAt()) );
      out.write("\r\n");
      out.write("\t\t ");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<span class=\"invalidElement\"> an einem unbekannten Datum </span>\r\n");
      out.write("\t\t");
 } 
      out.write("\r\n");
      out.write("\t\tvon \r\n");
      out.write("\t\t");
 if (projectToShow.getLastModifiedBy().getName() != null) { 
      out.write("\r\n");
      out.write("\t\t\t");
      out.print( projectToShow.getLastModifiedBy().getName() );
      out.write("\r\n");
      out.write("\t\t ");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<span class=\"invalidElement\"> Unbekannt </span>\r\n");
      out.write("\t\t");
 } 
      out.write("\t\t\r\n");
      out.write("\t\tgeändert.\r\n");
      out.write("\t</p>\r\n");
      out.write("\r\n");
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
