/**
 * 
 */
 var userInput = "";
 
function verifyDeleteOrder(x, y) {
    var x = confirm("Are you sure you would like to delete order #" + x + " for " + y);
	if(x == true){
		return true;
	}
	else{
		return false;
	}

}

function verifyDeleteCust(x) {
    var x = confirm("Are you sure you would like to delete customer: " + x);
	if(x == true){
		return true;
	}
	else{
		return false;
	}
}

function verifyDeleteService(x){
	var x = confirm("Are you sure you would like to delete service: " + x);
	if (x == true){
		return true;	
	}else{
		return false;
	}
}

function emailLowerCase(id) {
  var x = document.getElementById(id);
  x.value = x.value.toLowerCase();
}


function verifyDeleteWorker(x) {
    var x = confirm("Are you sure you would like to delete worker: " + x);
	if(x == true){
		return true;
	}
	else{
		return false;
	}

}

function isNumberKey(evt) {
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode
    if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57))
        return false;
    return true;
}

function onFocusOutFormat (id){
	var formattedInput = "";
	var input = document.getElementById(id);
	var inputLength = document.getElementById(id).value.length;
	console.log(inputLength);

	if ( inputLength == 10 ) {

		formattedInput = "(" + input.value.substr(0,3) + ") " + input.value.substr(3,3) + "-" + input.value.substr(6,4);
		input.value = formattedInput;
	}
}
function onFocusFormat (id){
	var input = document.getElementById(id);
	var unformatted = "";
	if ( input.value.match("[(][0-9]{3}[)][ ][0-9]{3}-[0-9]{4}") ){

		unformatted = input.value.substr(1,3) + input.value.substr(6,3) + input.value.substr(10,4);
		input.value = unformatted;
	}
	
}
