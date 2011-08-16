/**
 * 
 */

function addDefaultFile() {
	
	if(!document.getElementById) return;
	var field_area = document.getElementById("additionalFiles");
	
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
		
		// create fileupload
		var fileUpload = createFileupload("additionalFile_"+count);
		div.appendChild(fileUpload);
				
		// create trash to delete items
		var span = document.createElement("span");
		span.setAttribute("class", "trashFiles");
		div.appendChild(span);
		
		var img = document.createElement("img");
		img.setAttribute("src", "img/trash.gif");
		img.setAttribute("onclick", "removeAdditionalFile(this.parentNode.parentNode);");
		img.setAttribute("class", "trashImage");
		span.appendChild(img);
		
		//add div
		field_area.appendChild(div);
		
		//set counter to highest count
		var myCounter = document.getElementById("maxNumberOfAdditionalFiles");
		myCounter.setAttribute("value", count);
		
		
	} else {
		field_area.innerHTML += "<div> Dynamisches Erstellen von Elementen wird von Ihrem Browser nicht unterstützt. </div>";
	}
}

function removeAdditionalFile(node){	
	node.parentNode.removeChild(node);	
}

function removeProjectFile(node){	
	
	var div = document.getElementById("divProjectFile");
	
	var span = document.createElement("span");
	span.setAttribute("class", "formInput");
	div.appendChild(span);
	
	var input = document.createElement("input");
	input.setAttribute("type", "file");
	input.setAttribute("name", "projectFile");
	input.setAttribute("id", "projectFile");
	input.setAttribute("size", "70px");
	span.appendChild(input);	

	node.parentNode.removeChild(node);	
}


function createFileupload(anId) {
	var result = document.createElement("input");
	result.setAttribute("type", "file");
	result.setAttribute("name", anId);
	result.setAttribute("id", anId);
	result.setAttribute("class", "formFileUpload");	
	result.setAttribute("size", 70);
	return result;	
}