//openStore code adapted from w3 Schools
// https://www.w3schools.com/howto/howto_js_tabs.asp

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

	document.getElementById(storeName).style.display = "block";
	evt.currentTarget.className += " active";
}

document.getElementById("defaultOpen").click();