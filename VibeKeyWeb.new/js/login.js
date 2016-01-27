function login() {
	var login_email = $("#email").val();
	var login_password = $("#password").val();
	var ref = new Firebase("https://vibekey-open.firebaseio.com/");
	ref.authWithPassword({
	  email    : login_email,
	  password : login_password
	}, function(error, authData) {
	  if (error) {
	    alert("Login Failed!", error);
	  } else {
	    var username = authData.password.email;
	    $("#modal_trigger").text("Login as " + username + "Click to log out");
	    $(".loginPop").hide();
	    $(".logoutPop").show();
	    changeToDjView();
	  }
	});
}

function logout() {
	var ref = new Firebase("https://vibekey-open.firebaseio.com/");
	ref.unauth();
	$("#modal_trigger").text("Login or register");
	$(".logoutPop").hide();
	$(".loginPop").show();
	changeToUserView();
}

function changeToDjView() {
	$(".DjView").show();
	$(".UserView").hide();
}

function changeToUserView() {
	$(".DjView").hide();
	$(".UserView").show();
}

function openregister() {
	window.open("webpage/register.html","Kerberos Authentication");
}