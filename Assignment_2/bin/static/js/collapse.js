function collapse(tag) {
	var ul = tag.nextElementSibling;
	if(ul.style.display == "") {
		ul.style.display = "none";
		tag.innerHTML = "(+)";
	}
	else {
		ul.style.display = "";
		tag.innerHTML = "(-)";
	}
}