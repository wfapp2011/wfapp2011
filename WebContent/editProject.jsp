<%@page import="java.util.ArrayList"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.ProjectProposal"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.Person"%>
<%@page import="de.uni_potsdam.hpi.wfapp2011.data.DummyDatabase"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Locale "%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="wfapp.css" />
	
	<script type="text/javascript" src="dynamicContactPersons.js"></script>
	<script type="text/javascript" src="dynamicFiles.js"></script>
		
	<!-- jQuery-UI for datepicker -->
	<link type="text/css" href="jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />	
	<script type="text/javascript" src="jquery-ui-1.8.13.custom/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
			
	<!-- CKEditor for wikiedit -->
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	
	<!-- will be refactored and transferred to external css -->
	<style type="text/css">
		.alignTop    { vertical-align:top }
		.inputForm  {width: 70%; min-width:500px;}		
		.formRow	{padding-bottom:30px; clear: both;}
		.formTextinput {width:50%;}		
		.formTextinputPerson {width: 40%}	
		.formLabel {width: 15%;  text-align:left; float:left}
		.formInput {width: 85%; float:right; }
				
		.formInputContactPerson {width: 25%; color:grey; margin-right: 1%;}
		.formInputAddPerson {width: 54%; float:right;}
		
		.formAddFile {width: 54%; float:right}
		.trashFiles {margin-left: 10px;}
		.trashImage {width: 17px; vertical-align: bottom;}
		.formFile {width: 85%; float:right; margin-bottom: 5px;}
		
		.formButtons {float: right; clear:both}
	</style>

	<%
		DummyDatabase db = DummyDatabase.getInstance();

		String projectID = request.getParameter("projectID");
		ArrayList<ProjectProposal> projectProposals = db.getProjectProposals();
		ProjectProposal projectToEdit = null;
		for (ProjectProposal project : projectProposals){
			if (project.toString().equals(projectID)){
				projectToEdit = project;
			}
		}
	%> 


<script type="text/javascript" language="javascript">
	
	$(function() {
		$( "#estimatedBegin" ).datepicker({
			showOn: "button",
			buttonImage: "img/calendar.gif",
			buttonImageOnly: true
		});	
		$( "#estimatedBegin" ).datepicker( "option", "dateFormat", "yy-mm-dd");
				
	});		
	
	
	
	function initForm() {
		
		var projectName = document.getElementById("projectName");     
		projectName.setAttribute("value", "<%=projectToEdit.getProjectName()%>");
						
		var keywords = document.getElementById("keywords");     
		keywords.setAttribute("value", "<%=projectToEdit.getKeywords()%>");
		
		var partnerName = document.getElementById("partnerName");     
		partnerName.setAttribute("value", "<%=projectToEdit.getPartnerName()%>");		
						
		<% if (projectToEdit.getEstimatedBegin() != null) {%>
			$('#estimatedBegin').datepicker("setDate", new Date(<%=projectToEdit.getEstimatedBegin().getTime()%>));
		<%}%>
		
		var minStud = document.getElementById("minStud");
		minStud.value = <%= projectToEdit.getMinStud()%>;
	
		var maxStud = document.getElementById("maxStud");
		maxStud.value = <%= projectToEdit.getMaxStud()%>;
		
		<% 
		ArrayList<Person> contactPersons = projectToEdit.getContactPersons();
		int numberOfContactPersons = contactPersons.size();%>
		
		<% if (numberOfContactPersons > 0) { 
			for (int i=0; i<numberOfContactPersons; i++){
				String aName = contactPersons.get(i).getName();
				String anEmail = contactPersons.get(i).getEmail();%>
				addContactPerson("<%=aName%>","<%=anEmail%>");
			<%}
		} else {%>
			addDefaultContactPerson();
		<%}%>
		
	}
	
	<%--
	<% if (projectToEdit.getProjectFile() != null) { %>;
		var projectFile = document.getElementById("projectFileName");
		projectFile.value = <%= projectToEdit.getProjectFile().getName()%>;
	<%}%>
	--%>
	


	function insertHTML(editor, content){
		// Check the active editing mode.
		if ( editor.mode == 'wysiwyg' ){
			editor.insertHtml( content );
		}
		else alert( 'You must be in WYSIWYG mode!' );
	}
	
	
	var secondEditorIsReady = false;
	CKEDITOR.on( 'instanceReady', function(){
		
		if (!secondEditorIsReady) {
			secondEditorIsReady = true;			
		}
		else {
			var projectDescrEditor = CKEDITOR.instances.projectDescription;
			var projectDescription = <%= projectToEdit.getProjectDescription()%>
			insertHTML(projectDescrEditor, projectDescription);
					
			var partnerDescrEditor = CKEDITOR.instances.partnerDescription;
			var partnerDescription = <%= projectToEdit.getPartnerDescription()%>
			insertHTML(partnerDescrEditor, partnerDescription);
		}
	});
	
	
</script>

	
<title>Projektvorschlag bearbeiten</title>
</head>

<body onload="initForm(); addDefaultFile();">

	<p>
		<a href="listAllDepartments.jsp">Alle Themenvorschläge</a>&nbsp;|&nbsp;
		<a href="listOwnDepartment.jsp">eigene Themenvorschläge</a> &nbsp;|&nbsp; 
		<a href="createProject.jsp">neuer Vorschlag</a>
	</p>
	
	
	<h2>Projektvorschlag bearbeiten</h2>

	<form name="input" method="post" action="saveProject" enctype="multipart/form-data">		
	<div class="inputForm">		
		
		<div class="formRow">
			<span class="formLabel">Projektname:</span>
			<span class="formInput">
				<input  class="formTextinput" type="text" name="projectName" id="projectName" />
			</span>
		</div> 

		<div class="formRow">
			<span class="formLabel">Beschreibung: </span>
			<span class="formInput"> 
				<textarea  id="projectDescription" name="projectDescription"></textarea>
				<script type="text/javascript">
					CKEDITOR.replace( 'projectDescription' );
		</script>	
			</span>
		</div>
	
		<div class="formRow">
			<span class="formLabel">Keywords:</span>
			<span class="formInput"><input class="formTextinput" type="text" name="keywords" id="keywords"/></span> 
		</div>
	
		<div class="formRow">
			<span class="formLabel">Partnername:</span>
			<span class="formInput"><input class="formTextinput" type="text" name="partnerName" id="partnerName"/></span> 
		</div>
		
		<div class="formRow">
			<span class="formLabel">Partnerbeschreibung: </span>
			<span class="formInput"> 
				<textarea id="partnerDescription" name="partnerDescription"></textarea>
				<script type="text/javascript">
					CKEDITOR.replace( 'partnerDescription' );
				</script>	
			</span>			
		</div>
				
		<div class="formRow">
			<span class="formLabel">vorauss. Beginn:</span>
			<span class="formInput">
				<input type="text" name="estimatedBegin" id="estimatedBegin"/>
			</span> 
		</div>	

		<div class="formRow">
			<div>
				<span class="formLabel">Projektbetreuer:</span>				
				<div id="contactPersons" class="formInput">
					<!-- items are generated via javascript -->	
				</div>
			    <div class="formInputAddPerson">
			    	<input type="button" value="Person hinzufügen" onclick="addDefaultContactPerson();" value="0" />
				</div>
				<div>
					<input type="hidden" name="maxNumberOfContactPersons" id="maxNumberOfContactPersons">	
				</div>	
			</div>	
		</div>	
            			
		<div class="formRow">
			<span class="formLabel">Teamgroesse:</span>
			<span class="formInput">
				<select name="minStud" id="minStud">
						<option value=1>1</option>
						<option value=2>2</option>
						<option value=3 selected="selected">3</option>
						<option value=4>4</option>
						<option value=5>5</option>
						<option value=6>6</option>
						<option value=7>7</option>
				</select>
				bis
				<select name="maxStud" id="maxStud">
						<option value=4>4</option>
						<option value=5>5</option>
						<option value=6>6</option>
						<option value=7 selected="selected">7</option>
						<option value=8>8</option>
						<option value=9>9</option>
						<option value=10>10</option>
				</select> 
				Studenten
			</span>
		</div>	
			
		<div class="formRow" id="projectFile">
		<span class="formLabel">Projektdatei:</span>	
			<div id="divProjectFile">	
			<% if (projectToEdit.getProjectFile() == null) {	 %>
					<span class="formFile"><input type="file" name="projectFile" id="projectFile" size="70px"/></span> 
				<%}
				else {%>
					<div>
					<b><a onClick="window.location.href='uploads/'+'<%=projectToEdit.getProjectFile()%>'"> 
						<%= projectToEdit.getProjectFile().getName() %> </a></b>
					<img class="trashImage" src="img/trash.gif" onclick="removeProjectFile(this.parentNode)";/>	
					<br>
					</div>							
				<%}%>								   		
			</div>	   		
		<div class="formRow" id="files">
			<span class="formLabel">weitere Dateien:</span>		
			<ul>	
				<% if (projectToEdit.getAdditionalFiles().isEmpty() == false) {
						for (File file : projectToEdit.getAdditionalFiles()){%>
							<%-- must be replaced by ftp-link --%>
							<li class="formFile"> <a onClick="window.location.href='uploads/'+'<%=file.getName()%>'"> <%= file.getName() %> </a>
								<img class="trashImage" src="img/trash.gif" onclick="removeAdditionalFile(this.parentNode)";/> </li>						
						<%}	
				}%>
			
			</ul>
			<div id="additionalFiles" class="formInput">
					<!-- items are generated via javascript -->	
			</div>
			<div class="formAddFile">
			    <input type="button" value="weitere Datei" onclick="addDefaultFile();" />
			</div>
			<div>
				<input type="hidden" name="maxNumberOfAdditionalFiles" id="maxNumberOfAdditionalFiles">	
			</div>	
		</div>	 

		<div class="formButtons">
			<span><input type="submit" value="Speichern" /></span>
			<span><input type="reset" value="Verwerfen" /></span>
			<span><input type="button" value="PDF erzeugen" onclick="initCKEditor();"/></span>		
		</div>	
		
		<div>
			<input type="hidden" name="projectID" value="<%=projectID%>" />
		</div>	
		
	</div>
	</form>
	
</body>
</html>