<%@page import="java.util.ArrayList"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.ProjectProposal"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.Person"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.DummyDatabase"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.*"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
		<a href="createProject.jsp">neuer Vorschlag</a>
	</p>
	
	<h1>eigene Themenvorschlaege</h1>
		<table border="1">
		<tr>
			<td>�ffentlich</td>
			<td>Projektname</td>
			<td>Keywords</td>
			<td>Partner</td>
			<td>Betreuer</td>
			<td>Teamgr��e</td>
			<td>Dateien</td>
			<td></td>
			<td></td>
		</tr>
		
		<% DummyDatabase db = DummyDatabase.getInstance();
		ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
		for (ProjectProposal proposal : projectProposals){
			if (/*check for department here &&*/ !(proposal.getIsDeleted())){%>
			<tr>
				<% if (false /*Frist �berschritten*/) {%>
					<td><input type="checkbox" <%= proposal.getIsPublic() ? "checked" : "" %> disabled></td>
				<% } else { %>
					<td align="center"><form action="SetPublicness" method="post">
						<input type="checkbox" <%= proposal.getIsPublic() ? "checked" : "" %> onClick="submit()">
						<input type="hidden" name="projectID" value="<%= proposal %>">
						<input type="hidden" name="checked" value=<%= proposal.getIsPublic() ? "false" : "true" %>>
						</form></td>
				<% }%>
				<td><a href="showProject.jsp?projectID=<%= proposal.toString()%>"><%= proposal.getProjectName() %></a></td>
				<td><% for (String keyword : proposal.getKeywords().split("\\;|,")){%>
					<% out.println(keyword); %><br>
					<% }%></td>
				<td><%= proposal.getPartnerName() %></td>
				<td><% if (proposal.getContactPersons().isEmpty()) {out.println("bisher keiner");}
				else {for (Person person : proposal.getContactPersons()){%>
					<% out.println(person.getName()); %><br>
					<% }}%></td>
				<td><%= proposal.getMinStud() %> - <%= proposal.getMaxStud() %></td>
				<td><% if (proposal.getProjectFile() == null && proposal.getAdditionalFiles().isEmpty()) {out.println("bisher keine");}
					else {
						if (proposal.getProjectFile() != null) {%> <b><% out.println(proposal.getProjectFile());%></b><br><% }
						for (File file : proposal.getAdditionalFiles()){
							out.println(file.getName()); %><br>
					<% }}%></td>
				<td>
					<a href="editProject.jsp?projectID=<%= proposal.toString()%>">
						<img src="img/edit.png" alt="edit" width="16" height="16" />
					</a>
					
				</td>
				<td><form name="delete" action="Delete" method="post">
            		<input type="image" src="img/trash.gif" alt="delete" 
            			onClick="return(confirm('M�chten Sie den Projektvorschlag <%= proposal.getProjectName() %> wirklich l�schen?'))"/>
            		<input type="hidden" name="projectID" value="<%= proposal %>"/>
            		</form></td>
			</tr>
		<% }}%>
	</table>
		
</body>
</html>