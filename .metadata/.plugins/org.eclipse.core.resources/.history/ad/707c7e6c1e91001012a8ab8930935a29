<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="wfapp.css" />
	
	<!-- jQuery-UI for datepicker -->
	<link type="text/css" href="jquery-ui-1.8.13.custom/css/smoothness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />	
	<script type="text/javascript" src="jquery-ui-1.8.13.custom/js/jquery-1.5.1.min.js"></script>
	<script type="text/javascript" src="jquery-ui-1.8.13.custom/js/jquery-ui-1.8.13.custom.min.js"></script>
			
	<!-- CKEditor for wikiedit -->
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<script src="sample.js" type="text/javascript"></script>
	
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


<script>
	$(function() {
		$( "#datepicker" ).datepicker({
			showOn: "button",
			buttonImage: "img/calendar.gif",
			buttonImageOnly: true
		});	
		$( "#datepicker" ).datepicker( "option", "dateFormat", "yy-mm-dd");
	});			
</script>	
<script type="text/javascript" language="javascript">
<!--
	function addContactPerson() {

		if(!document.getElementById) return;
		var field_area = document.getElementById("contactPersons");
		
		//Get all input fields in the given area and find count of last field (= highest count)
		//note: when fields are deleted, the highest count might be higher than the number of fields
		var all_inputs = field_area.getElementsByTagName("input"); 
		var length = all_inputs.length;
		var count = 1;
		if (length > 0){
			var last = all_inputs[length-1].id;
			count = Number(last.split("_")[1]) + 1;
		}
		
		if(document.createElement) { 
			var div = document.createElement("div");
		
			//Create textinput for "name"
			var nameInput = document.createElement("input");
			nameInput.setAttribute("type", "text");
			nameInput.setAttribute("name", "contactPersonName_"+count);
			nameInput.setAttribute("id", "contactPersonName_"+count);
			nameInput.setAttribute("value", "Name");
			nameInput.setAttribute("onclick", "this.value='';");
			nameInput.setAttribute("class", "formInputContactPerson");
			div.appendChild(nameInput);
			
			//Create textinput for "email"
			var emailInput = document.createElement("input");
			emailInput.setAttribute("type", "text");
			emailInput.setAttribute("name", "contactPersonEmail_"+count);
			emailInput.setAttribute("id", "contactPersonEmail_"+count);
			emailInput.setAttribute("value", "E-Mail-Adresse");
			emailInput.setAttribute("onclick", "this.value='';");
			emailInput.setAttribute("class", "formInputContactPerson");
			div.appendChild(emailInput);
			
			//Create trash to delete items
			var img = document.createElement("img");
			img.setAttribute("src", "img/trash.gif");
			img.setAttribute("onclick", "removeContactPerson(this.parentNode);");
			div.appendChild(img);
					
			//add div
			field_area.appendChild(div);
			
			//set counter to highest count
			var myCounter = document.getElementById("maxNumberOfContactPersons");
			//myCounter.setAttribute("value", Number(myCounter.value) + 1);
			myCounter.setAttribute("value", count);
			
			
		} else {
			field_area.innerHTML += "<div> Dynamisches Erstellen von Elementen wird von Ihrem Browser nicht unterstützt. </div>";
		}
	}

	function removeContactPerson(node){
		
		//remove div
		node.parentNode.removeChild(node);
		
		//decrement counter
		//var myCounter = document.getElementById("numberOfContactPersons");
		//myCounter.setAttribute('value', Number(myCounter.value) - 1);
	}
	
//--></script>
	
<title>Projektvorschlag erstellen</title>
</head>

<body onload="addContactPerson(); addContactPerson();">

	<p>
		<a href="listAllDepartments.jsp">Alle Themenvorschläge</a>&nbsp;|&nbsp;
		<a href="listOwnDepartment.jsp">eigene Themenvorschläge</a> &nbsp;|&nbsp; 
		<a href="createProject.jsp">neuer Vorschlag</a> &nbsp;|&nbsp; 
	</p>
		
		
	<h2>Neues Projekt erstellen</h2>

	<form name="input" method="get" action="createProject">		
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
			    	<input type="button" value="Person hinzufügen" onclick="addContactPerson();" value="0" />
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
		
	</div>
	</form>
	
</body>
</html>