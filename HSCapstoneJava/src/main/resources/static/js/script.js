/**
 * 
 */
function verifyDelete(x, y) {
    var x = confirm("Are you sure you would like to delete order #" + x + " for " + y);
	if(x == true){
		return true;
	}
	else{
		return false;
	}

}

/* Toggle between adding and removing the "responsive" class to topnav when the user clicks on the icon */
function myFunction() {
  var x = document.getElementById("myTopnav");
  if (x.className === "topnav") {
    x.className += " responsive";
  } else {
    x.className = "topnav";
  }
}