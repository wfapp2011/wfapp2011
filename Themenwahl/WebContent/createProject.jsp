<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" type="text/css" href="wfapp.css" />
	
	<style type="text/css">
		.alignTop    { vertical-align:top }
		.inputForm  {width: 70%; min-width:500px;}
		
		.formRow	{padding-bottom:30px;}
		.formTextinput {width:50%;}		
		.formTextinputPerson {width: 40%}
		
		.formLabel {width: 15%; float: left; vertical-align:top; text-align:left; }
		.formInput {width: 85%; float:right}
		.formEditorButtons {width: 100%; text-align:right; float:right}
		.formButtons {float: right}
	</style>

	<!-- CKEditor -->
	<script type="text/javascript" src="ckeditor/ckeditor.js"></script>
	<script src="sample.js" type="text/javascript"></script>


<script type="text/javascript" language="javascript"><!--
<!--
//Add more fields dynamically.
function addContactPerson() {

	
	var MyElement = document.getElementById("contactPersonName_1");
    MyElement.value = "If you see this, it worked!";

	
	if(!document.getElementById) return; //Prevent older browsers from getting any further.
	var field_area = document.getElementById("contactPersons");
	var all_inputs = field_area.getElementsByTagName("input"); //Get all the input fields in the given area.
	//Find the count of the last element of the list. It will be in the format '<field><number>'. If the 
	//		field given in the argument is 'friend_' the last id will be 'friend_4'.
	var last_item = all_inputs.length - 1;
	var last = all_inputs[last_item].id;
	var count = Number(last.split("_")[1]) + 1;
	
	//If the maximum number of elements have been reached, exit the function.
	//		If the given limit is lower than 0, infinite number of fields can be created.
 	
	if(document.createElement) { //W3C Dom method.
		var div = document.createElement("div");
		
		var nameInput = document.createElement("input");
		nameInput.type = "text"; //Type of field - can be any valid input type like text,file,checkbox etc.
		nameInput.name = "contactPersonName_"+count;
		nameInput.id = "contactPersonName_"+count;
		nameInput.setAttribute("value", "name");
		div.appendChild(nameInput);
		
		var emailInput = document.createElement("input");
		emailInput.type = "text";
		emailInput.name = "contactPersonEmail_"+count;
		emailInput.id = "contactPersonEmail_"+count;
		emailInput.setAttribute("value", "mail");
		div.appendChild(emailInput);
		
	
		var img = document.createElement("img");
		img.src = "img/trash.gif";
		img.setAttribute("onclick", "this.parentNode.parentNode.removeChild(this.parentNode);");
		div.appendChild(img);
			
		
		field_area.appendChild(div);
	} else { //Older Method
		field_area.innerHTML += "<div><input name='"+(field+count)+"' div='"+(field+count)+"' type='text' /></div>";
	};
}


//--></script>
	
	<title>Projektvorschlag erstellen</title>
</head>

<body onload="createEditor('partnerDescrEditor');createEditor('projectDescrEditor');">

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
			<span class="formInput"><input  class="formTextinput" type="text" name="projectName" /></span> 
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
			<span class="formInput"><input type="text" name="estimatedBegin" /></span> 
		</div>	

		<div class="formRow">
			<div>
				<span class="formLabel">Projektbetreuer:</span>
				
				<div id="contactPersons">
					<div>
						<input type="text" name="contactPersonName_1" id="contactPersonName_1" value="name"/>
						<input type="text" name="contactPersonEmail_1" id="contactPersonEmail_1" value="mail"/>
						<img src="img/trash.gif" onClick="this.parentNode.parentNode.removeChild(this.parentNode);"/>	
					</div>
				</div>
			    <div>
			    	<input type="button" value="Person hinzufügen" onclick="addContactPerson();" />
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