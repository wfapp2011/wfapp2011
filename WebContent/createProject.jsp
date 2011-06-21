<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="wfapp.css" />
	
	<script type="text/javascript" src="dynamicContactPersons.js"></script>
	
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
		.formEditorButtons {width: 100%; text-align:right; float:right}
		.formButtons {float: right}
		.formInputContactPerson {width: 25%; color:grey; margin-right: 1%;}
		.formInputAddPerson {width: 54%; float:right;}
	</style>

	
	<script type="text/javascript" language="javascript">
		$(function() {
			$( "#datepicker" ).datepicker({
				showOn: "button",
				buttonImage: "img/calendar.gif",
				buttonImageOnly: true
			});	
			$( "#datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd");
		});	
	</script>
	
	<title>Projektvorschlag erstellen</title>
</head>

<body onload="addDefaultContactPerson();">

	<p>
		<a href="listAllDepartments.jsp">Alle Themenvorschläge</a>&nbsp;|&nbsp;
		<a href="listOwnDepartment.jsp">eigene Themenvorschläge</a> &nbsp;|&nbsp; 
		<a href="createProject.jsp">neuer Vorschlag</a>
	</p>
		
		
	<h2>Neues Projekt erstellen</h2>

	<form name="input" method="post" action="saveFile" enctype="multipart/form-data">		
	<div class="inputForm">
		
		<div class="formRow">
			<span class="formLabel">Projektname:</span>
			<span class="formInput">
				<input  class="formTextinput" type="text" name="projectName" />
			</span>
		</div> 

		<div class="formRow">
			<span class="formLabel">Beschreibung: </span>
			<span class="formInput"> 
				<textarea id="projectDescription" name="projectDescription"></textarea>
				<script type="text/javascript">
					CKEDITOR.replace( 'projectDescription' );
				</script>
			</span>
		</div>
		
		<div class="formRow">
			<span class="formLabel">Keywords:</span>
			<span class="formInput"><input class="formTextinput" type="text" name="keywords"/></span> 
		</div>
	
		<div class="formRow">
			<span class="formLabel">Partnername:</span>
			<span class="formInput"><input class="formTextinput" type="text" name="partnerName" /></span> 
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
				<input type="text" name="estimatedBegin" id="datepicker"/>
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
				<select name="minStud">
						<option value=1>1</option>
						<option value=2>2</option>
						<option value=3 selected="selected">3</option>
						<option value=4>4</option>
						<option value=5>5</option>
						<option value=6>6</option>
						<option value=7>7</option>
				</select>
				bis
				<select name="maxStud">
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
					
			
		<div class="formRow">
			<span class="formLabel">Projektdatei:</span>
			<span class="formInput"><input type="file" name="projectFile"/></span> 
		</div>	
		
		 
		<div class="formRow">
			<span class="formLabel">weitere Dateien:</span>
			<span class="formInput"><input type="file" name="additionalFiles" /></span> 
		</div>	 
	
		<div class="formButtons">
			<span><input type="submit" value="Speichern" /></span>
			<span><input type="reset" value="Verwerfen" /></span>
			<span><input type="button" value="PDF erzeugen" /></span>		
		</div>
		
		<div>
			<input type="hidden" name="projectID" value=""/>
		</div>		
		
	</div> 
	</form>
	
</body>
</html>