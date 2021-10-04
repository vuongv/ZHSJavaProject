/**
 * 
 */
 var userInput = "";
 var workerSortTableState = 1;
 
 
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
function sortWorkerTableByName() {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("workerTable");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("TD")[1];
      y = rows[i + 1].getElementsByTagName("TD")[1];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}

function sortWorkerTableById() {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("workerTable");
  switching = true;
  
  dir = "asc";
  
  while (switching) {
    
    switching = false;
    rows = table.rows;
    
    for (i = 1; i < (rows.length - 1); i++) {
      
      shouldSwitch = false;
      
      x = rows[i].getElementsByTagName("TD")[0];
      y = rows[i + 1].getElementsByTagName("TD")[0];
      
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      
      switchcount ++;
    } else {
      
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}