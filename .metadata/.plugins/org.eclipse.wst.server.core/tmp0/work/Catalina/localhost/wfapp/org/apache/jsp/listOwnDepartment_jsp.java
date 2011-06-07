package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.ArrayList;
import data.ProjectProposal;
import data.Person;
import java.io.File;

public final class listOwnDepartment_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>Project proposals (own department)</title>\r\n");
      out.write("</head> \r\n");
      out.write(" \r\n");
      out.write("<body>\r\n");
      out.write("\t<p>\r\n");
      out.write("\t\t<a href=\"listAllDepartments.jsp\">Alle Themenvorschläge</a>&nbsp;|&nbsp;\r\n");
      out.write("\t\t<a href=\"listOwnDepartment.jsp\">eigene Themenvorschläge</a> &nbsp;|&nbsp; \r\n");
      out.write("\t\t<a href=\"createProject.jsp\">neuer Vorschlag</a>\r\n");
      out.write("\t</p>\r\n");
      out.write("\t\r\n");
      out.write("\t<h1>eigene Themenvorschlaege</h1>\r\n");
      out.write("\t\t<table border=\"1\">\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t\t<td>öffentlich</td>\r\n");
      out.write("\t\t\t<td>Projektname</td>\r\n");
      out.write("\t\t\t<td>Keywords</td>\r\n");
      out.write("\t\t\t<td>Partner</td>\r\n");
      out.write("\t\t\t<td>Betreuer</td>\r\n");
      out.write("\t\t\t<td>Teamgröße</td>\r\n");
      out.write("\t\t\t<td>Dateien</td>\r\n");
      out.write("\t\t\t<td></td>\r\n");
      out.write("\t\t\t<td></td>\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t");
 data.DummyDatabase db = data.DummyDatabase.getInstance();
		ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
		for (ProjectProposal proposal : projectProposals){
			if (/*check for department here &&*/ !(proposal.getIsDeleted())){
      out.write("\r\n");
      out.write("\t\t\t<tr>\r\n");
      out.write("\t\t\t\t<td align=\"center\"><form action=\"SetPublicness\" method=\"post\">\r\n");
      out.write("\t\t\t\t\t<input type=\"checkbox\" ");
      out.print( proposal.getIsPublic() ? "checked" : "" );
      out.write(" onClick=\"submit()\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"projectID\" value=\"");
      out.print( proposal );
      out.write("\">\r\n");
      out.write("\t\t\t\t\t<input type=\"hidden\" name=\"checked\" value=");
      out.print( proposal.getIsPublic() ? "false" : "true" );
      out.write(">\r\n");
      out.write("\t\t\t\t\t</form></td>\r\n");
      out.write("\t\t\t\t<td><a href=\"showProject.jsp?projectID=");
      out.print( proposal.toString());
      out.write('"');
      out.write('>');
      out.print( proposal.getProjectName() );
      out.write("</a></td>\r\n");
      out.write("\t\t\t\t<td>");
 for (String keyword : proposal.getKeywords().split("\\;|,")){
      out.write("\r\n");
      out.write("\t\t\t\t\t");
 out.println(keyword); 
      out.write("<br>\r\n");
      out.write("\t\t\t\t\t");
 }
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print( proposal.getPartnerName() );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
 if (proposal.getContactPersons().isEmpty()) {out.println("bisher keiner");}
				else {for (Person person : proposal.getContactPersons()){
      out.write("\r\n");
      out.write("\t\t\t\t\t");
 out.println(person.getName()); 
      out.write("<br>\r\n");
      out.write("\t\t\t\t\t");
 }}
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
      out.print( proposal.getMinStud() );
      out.write(' ');
      out.write('-');
      out.write(' ');
      out.print( proposal.getMaxStud() );
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>");
 if (proposal.getProjectFile() == null && proposal.getAdditionalFiles().isEmpty()) {out.println("bisher keine");}
					else {
						if (proposal.getProjectFile() != null) {
      out.write(" <b>");
 out.println(proposal.getProjectFile());
      out.write("</b><br>");
 }
						for (File file : proposal.getAdditionalFiles()){
							out.println(file.getName()); 
      out.write("<br>\r\n");
      out.write("\t\t\t\t\t");
 }}
      out.write("</td>\r\n");
      out.write("\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t<!-- <a href=\"editProject.jsp?projectID=");
      out.print( proposal.toString());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t<img src=\"img\\edit.png\" alt=\"edit\" width=\"16\" height=\"16\" />\r\n");
      out.write("\t\t\t\t\t</a> -->\r\n");
      out.write("\t\t\t\t\t<a href=\"showProject.jsp?projectID=");
      out.print( proposal.toString());
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t<img src=\"img/edit.png\" alt=\"edit\" width=\"16\" height=\"16\" />\r\n");
      out.write("\t\t\t\t\t</a>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t<td><form name=\"delete\" action=\"Delete\" method=\"get\">\r\n");
      out.write("            \t\t<input type=\"image\" src=\"img/trash.gif\" alt=\"delete\" \r\n");
      out.write("            \t\t\tonClick=\"return(confirm('Möchten Sie den Projektvorschlag ");
      out.print( proposal.getProjectName() );
      out.write(" wirklich löschen?'))\"/>\r\n");
      out.write("            \t\t<input type=\"hidden\" name=\"projectID\" value=\"");
      out.print( proposal );
      out.write("\"/>\r\n");
      out.write("            \t\t</form></td>\r\n");
      out.write("\t\t\t</tr>\r\n");
      out.write("\t\t");
 }}
      out.write("\r\n");
      out.write("\t</table>\r\n");
      out.write("\t\t\r\n");
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
