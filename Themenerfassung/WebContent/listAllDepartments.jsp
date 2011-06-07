<%@page import="java.util.Random"%>
<%@page import="data.Department"%>
<%@page import="java.util.ArrayList"%>
<%@page import="data.ProjectProposal"%>
<%@page import="data.Person"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="wfapp.css" />
<title>Project proposals (own department)</title>
</head>
<body>
	<p>
		<a href="listAllDepartments.jsp">Alle Themenvorschl�ge</a>&nbsp;|&nbsp;
		<a href="listOwnDepartment.jsp">eigene Themenvorschl�ge</a> &nbsp;|&nbsp; 
		<a href="createProject.jsp">neuer Vorschlag</a> &nbsp;|&nbsp; 
	</p>

	<h1>Alle Themenvorschlaege</h1>
		<% data.DummyDatabase db = data.DummyDatabase.getInstance();
		for (Department department : db.getDepartments()) { %>
		<h3>Projektvorschl�ge vom Fachgebiet <%= department.getName() %></h3>
			<table border="1">
			<tr>
				<td>Status</td>
				<td>Projektname</td>
				<td>Keywords</td>
				<td>Partner</td>
				<td>Betreuer</td>
				<td>Teamgr��e</td>
				<td>Dateien</td>
			</tr>
			
			<%for (ProjectProposal proposal : db.getProjectProposals()){
				if (proposal.getIsPublic() && proposal.getDepartment() == department && !(proposal.getIsDeleted())){%>
				<tr>
					<td>n/a</td>
					<td><a href="showProject.jsp?projectID=<%= proposal.toString()%>"><%= proposal.getProjectName() %></a></td>
					<td><% /*for (String keyword : proposal.getKeywords()){*/%>
						<% out.println(proposal.getKeywords()); %><br>
						<% /*}*/%></td>
					<td><%= proposal.getPartnerName() %></td>
					<td><% if (proposal.getContactPersons().isEmpty()) {out.println("bisher keiner");}
					else {for (Person person : proposal.getContactPersons()){%>
						<% out.println(person.getName()); %><br>
					<% }}%></td>
					<td><%= proposal.getMinStud() %> - <%= proposal.getMaxStud() %></td>
					<td><% if (proposal.getProjectFile() == null && proposal.getAdditionalFiles().isEmpty()) {out.println("bisher keine");}
						else {
							if (proposal.getProjectFile() != null) {%> <b><% out.println(proposal.getProjectFile());%></b><br><% }
							for (File file : proposal.getAdditionalFiles()){%>
								<% out.println(file.getName()); %><br>
						<% }}%></td>				
				</tr>
		<% }} %>
	</table><br>
	<% }%>
		
</body>

</html>
