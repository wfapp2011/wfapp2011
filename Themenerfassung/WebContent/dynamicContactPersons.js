/**
 * 
 */

function addContactPerson(aName, anEmail) {

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
			var nameInput = createTextinput("contactPersonName_"+count, aName);
			div.appendChild(nameInput);
			
			//Create textinput for "email"
			var emailInput = createTextinput("contactPersonEmail_"+count, anEmail);
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

function addDefaultContactPerson() {
		addContactPerson("Name", "E-Mail-Adresse");
}


function removeContactPerson(node){
	
	node.parentNode.removeChild(node);	
	//decrement counter
	//var myCounter = document.getElementById("numberOfContactPersons");
	//myCounter.setAttribute('value', Number(myCounter.value) - 1);
}


function createTextinput(anId, aValue) {
	var result = document.createElement("input");
	result.setAttribute("type", "text");
	result.setAttribute("name", anId);
	result.setAttribute("id", anId);
	result.setAttribute("value", aValue);
	result.setAttribute("onclick", "this.value='';");
	result.setAttribute("class", "formInputContactPerson");	
	return result;	
}