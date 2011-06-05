<%@page import="java.util.ArrayList"%>
<%@page import="data.ProjectProposal"%>
<%@page import="data.DummyDatabase"%>
<%@page import="data.Person"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="wfapp.css" />
<title>Projektvorschlag ansehen</title>
</head>
<body>
	<p>
		<a href="listAllDepartments.jsp">Alle Themenvorschläge</a>&nbsp;|&nbsp;
		<a href="listOwnDepartment.jsp">eigene Themenvorschlaege</a> &nbsp;|&nbsp; 
		<a href="newProject.html">neuer Vorschlag</a> &nbsp;|&nbsp; 
	</p>
	
	<%
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
		%> 
	
	<h2><%= projectToShow.getProjectName()%></h2>
	<p>
		<h4> Projektbeschreibung: </h4>
		<%= projectToShow.getProjectDescription()%>
	</p>
	<p>
		<h4> Projektpartner: </h4>
		<%= projectToShow.getPartnerDescription()%>
	</p>
	<p>
		<h4> Eckdaten: </h4>
		An dem Projekt können <%= projectToShow.getMinStud()%> bis <%= projectToShow.getMaxStud()%> Studenten teilnehmen.<br />
		Voraussichtlicher Projektbeginn ist der <%=projectToShow.getEstimatedBegin()%>
	</p>
	<p>
		<h4> Kontakt: </h4>
		<%= projectToShow.getDepartment().getName()%>
		<ul>
			<% if (projectToShow.getDepartment().getProf() == null) {%>
				<li>bisher kein Professor zuständig</li>
			<% } else { %>
			<li> <%= projectToShow.getDepartment().getProf().getName() %>, <%= projectToShow.getDepartment().getProf().getEmail() %></li>
			<% 	contactPersons = projectToShow.getContactPersons();
				for (Person person : contactPersons){ %>
				<li> <%=person.getName()%>, <%= person.getEmail() %> </li>	
				<% }} %>
		</ul>

	</p>
</body>
</html>