/**
 * 
 */
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


function verifyDeleteWorker(x) {
    var x = confirm("Are you sure you would like to delete worker: " + x);
	if(x == true){
		return true;
	}
	else{
		return false;
	}

}