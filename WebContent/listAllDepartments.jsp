<%@page import="java.util.Random"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.Department"%>
<%@page import="java.util.ArrayList"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.ProjectProposal"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.Person"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.DummyDatabase"%>
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
		<a href="listAllDepartments.jsp">Alle Themenvorschläge</a>&nbsp;|&nbsp;
		<a href="listOwnDepartment.jsp">eigene Themenvorschläge</a> &nbsp;|&nbsp; 
		<a href="createProject.jsp">neuer Vorschlag</a>
	</p>

	<h1>Alle Themenvorschlaege</h1>
	<table>
		<tr>
			<td>Status</td>
			<td>Projektname</td>
			<td>Keywords</td>
			<td>Partner</td>
			<td>Betreuer</td>
			<td>Teamgröße</td>
			<td>Dateien</td>
		</tr>
		<% DummyDatabase db = DummyDatabase.getInstance();
		for (Department department : db.getDepartments()) { %>
			<tr bgcolor="#CFCFCF">
				<th colspan="7" align="left"><b>Projektvorschläge vom Fachgebiet <%= department.getName() %></b></th>
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
		<tr>
			<th colspan="7">&#160</th>
		</tr>
	<% }%>
	</table><br>
		
</body>

</html>
