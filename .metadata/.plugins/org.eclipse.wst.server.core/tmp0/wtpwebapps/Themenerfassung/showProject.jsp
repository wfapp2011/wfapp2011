<%@page import="java.util.ArrayList"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
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
<style type="text/css">
		.chapter    { font-weight:bold }
		.invalidElement {font-style: italic}
		.lastModifiedAnnotation {}
		
</style>
<title>Projektvorschlag ansehen</title>
</head>
<body>
	<p>
		<a href="listAllDepartments.jsp">Alle Themenvorschläge</a>&nbsp;|&nbsp;
		<a href="listOwnDepartment.jsp">eigene Themenvorschläge</a> &nbsp;|&nbsp; 
		<a href="createProject.jsp">neuer Vorschlag</a> &nbsp;|&nbsp; 
	</p>
	
	<%
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
		%> 
		
	<p>
		<% if (projectToShow.getProjectName() != null && projectToShow.getProjectName().trim().length() > 0) {%>
			<h2><%= projectToShow.getProjectName()%></h2>		
		<% } else { %>
			<div class="invalidElement"> Es wurde bisher kein Projektname angegeben. </div>
		<% } %>			
	</p>
	
	<p>
		<h4> Projektbeschreibung: </h4>
		<% if (projectToShow.getProjectDescription() != null && projectToShow.getProjectDescription().trim().length() > 0) {%>
			<h2><%= projectToShow.getProjectDescription()%></h2>		
		<% } else { %>
			<div class="invalidElement"> Es wurde bisher keine Projektbeschreibung angegeben. </div>
		<% } %>		
	</p>
		
	<p>
		<h4> Projektpartner: 
			<% if (projectToShow.getPartnerName() != null && projectToShow.getPartnerName().trim().length() > 0) {%>
				<%= projectToShow.getPartnerName()%>
			<% } %>
		</h4>	
		
		<% if (projectToShow.getPartnerDescription() != null && projectToShow.getPartnerDescription().trim().length() > 0) {%>
			<%= projectToShow.getPartnerDescription()%>	
		<% } else { %>
			<div class="invalidElement"> Es wurde bisher keine Partnerbeschreibung angegeben. </div>
		<% } %>		
	</p>
	
	<p>
		<h4> Eckdaten: </h4>
		An dem Projekt können <%= projectToShow.getMinStud()%> bis <%= projectToShow.getMaxStud()%> Studenten teilnehmen.<br />
		
		<% if (projectToShow.getEstimatedBegin() != null) {%>
			Voraussichtlicher Projektbeginn ist der <%= df.format(projectToShow.getEstimatedBegin())%>.	
		<% } else { %>
			<div class="invalidElement"> Der voraussichtliche Projektbeginn steht noch nicht fest. </div>
		<% } %>		
	</p>
	
	<p>
		<h4> Kontakt: </h4>
		
		<% if (projectToShow.getDepartment().getName() != null && projectToShow.getDepartment().getName().trim().length() > 0) {%>
			<%= projectToShow.getDepartment().getName()%>	
		<% } else { %>
			<div class="invalidElement"> Beim Einlesen des Fachbereichs ist ein Fehler aufgetreten. </div>
		<% } %>	

		<ul>
			<% if (projectToShow.getDepartment().getProf() == null) {%>
				<li class="invalidElement" >Bisher ist kein Professor zuständig.</li>
			<% } else { %>
				<% if (projectToShow.getDepartment().getProf().getName() != null && projectToShow.getDepartment().getProf().getEmail() != null ) {%>			
					<li> <%= projectToShow.getDepartment().getProf().getName() %>,<%= projectToShow.getDepartment().getProf().getEmail() %></li>
				<% } %>
				<% if (projectToShow.getContactPersons() != null) {%>
					<%contactPersons = projectToShow.getContactPersons();
					for (Person person : contactPersons){ %>
						<li>
							<% if (!(person.getName().trim().length() > 0)) {%>
								<span class="invalidElement"> kein Name angegeben, </span>
							<% } else { %>
								<%=person.getName()%>,
							<% } %>
							<% if (!(person.getEmail().trim().length() > 0)) {%>
								<span class="invalidElement"> keine E-Mail-Adresse angegeben </span>
							<% } else { %>
								<%=person.getEmail()%>
							<% } %>
						 </li>	
				<% }}} %>
		</ul>
	</p>
	

	<p class="lastModifiedAnnotation">
		Dieser Projektvorschlag wurde zuletzt 
		<% if (projectToShow.getLastModifiedAt() != null) { %>
			am <%= df.format(projectToShow.getLastModifiedAt()) %>
		 <% } else { %>
			<span class="invalidElement"> an einem unbekannten Datum </span>
		<% } %>
		von 
		<% if (projectToShow.getLastModifiedBy().getName() != null) { %>
			<%= projectToShow.getLastModifiedBy().getName() %>
		 <% } else { %>
			<span class="invalidElement"> Unbekannt </span>
		<% } %>		
		geändert.
	</p>

	
</body>
</html>