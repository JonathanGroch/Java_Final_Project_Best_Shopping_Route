//openStore code adapted from w3 Schools
// https://www.w3schools.com/howto/howto_js_tabs.asp
//autocomplete code adapted from w3 Schools
// https://www.w3schools.com/howto/howto_js_autocomplete.asp

//this array will be populated with a query to the database later
var targetItems = ["example", "meat", "children"];
var menardsItems = ["plywood", "dark matter", "garbage"];
var meijerItems = ["poop", "blueberries", "landfills"];

var getBtn = document.getElementById('myBtn');
getBtn.onclick = function() {
    getTheData() ;
}

function getTheData() {
    var some = document.getElementsByClassName("some");
    var inputValues = new Array();

    for (i in some) {
        //extract the value of input elements
        var singleVal = some[i].value;
        if (singleVal !== "" && singleVal !== undefined) {
            inputValues.push(singleVal);
        }
    }


    var showVals = document.getElementById('showInputValues');

    //Convert to string format to see the output easily 
    var stringFormat = String(inputValues);
    showVals.innerHTML = stringFormat;
    console.log(inputValues);

}

function addInput(divName){

          var newdiv = document.createElement('div');
          newdiv.innerHTML = "Item:" + "<input type='text' name='myInputs[]'>";
          document.getElementById(divName).appendChild(newdiv);
          counter++;
     }

//changes page content when nav tabs are clicked
function openStore(evt, storeName) {
	var i, tabcontent, navtab;

	tabcontent = document.getElementsByClassName("tabcontent");
	for (i=0; i<tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}

	navtab = document.getElementsByClassName("navtab");
	for (i=0; i<navtab.length; i++) {
		navtab[i].className = navtab[i].className.replace(" active", "");
	}

	document.getElementById(storeName).style.display = "grid";
	evt.currentTarget.className += " active";
}

document.getElementById("defaultOpen").click();

//autocomplete form code??
//maybe don'+ do +his
function autocomplete(input, array) {

}