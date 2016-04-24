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
	    $(".logout_info").text("Login as " + username +". Are you sure you want to log out?" );
	    $(".fa-user").css({'color':'#800000', 'animation-iteration-count': '0'});
	    $(".user-shape").css({'border': '1px solid #800000'});
	    $(".loginPop").hide();
	    $(".logoutPop").show();
	    $(".modal_close").click();
	    var uid = authData.uid;
	    var dj_ref = ref.child("prod/user_access/djs");
	    dj_ref.on('value', function(snapshot) {
            console.log(snapshot.child(uid).exists());
            if(snapshot.child(uid).exists()) {
              showCB();
            }
        });
	  }
	});
}

function logout() {
	var ref = new Firebase("https://vibekey-open.firebaseio.com/");
	ref.unauth();
	$(".fa-user").css({'color':'#cccccc', 'animation-iteration-count': 'infinite'});
	$(".user-shape").css({'border': '1px solid #cccccc'});
	$(".logoutPop").hide();
	$(".loginPop").show();
	$(".modal_close").click();
	hideCB();
}

function register() {
	var email = $("#email").val();
	var password = $("#password").val();
	var ref = new Firebase("https://vibekey-open.firebaseio.com/");
	ref.createUser({
  		email    : email,
	  	password : password
	}, function(error, userData) {
	  if (error) {
	    window.alert("Error creating user.");
	  } else {
	  	$(".modal_close").click();
	    window.alert("Successfully created user account.");
	  }
	});
}

function showCB() {
	$(".jp-control-board").show();
}

function hideCB() {
	$(".jp-control-board").hide();
}

function openControlBoard() {
	window.open("assets/webpages/controlboard.html","Control Board");
}