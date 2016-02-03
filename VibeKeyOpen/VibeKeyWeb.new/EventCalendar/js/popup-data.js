var sName;
var sDescrip;
var sRepeat;
var sType;
//var isPaused;

$(function () {  
	$("#create-Schedule").click(
	function() {	
		sName = document.getElementById("sName").value;
		sDescrip = document.getElementById("sDescrip").value;
		//var sRepeat = document.getElementsByName("sRepeat").value;
		sRepeat = $('input[name="sRepeat"]:checked').val();
		sType = document.getElementById("sType").value;
		
		console.log(sName + "-" + sDescrip + "-" + sRepeat + "-" + sType);
	});
 });