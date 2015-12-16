function register() {
	//var username = $("#username").val();
	var email = $("#email").val();
	var password = $("#password").val();
	var ref = new Firebase("https://vibekey-open.firebaseio.com/");
	ref.createUser({
  		email    : email,
	  	password : password
	}, function(error, userData) {
	  if (error) {
	    console.log("Error creating user:", error);
	    window.alert("Error creating user.");
	  } else {
	    console.log("Successfully created user account with uid:", userData.uid);
	    window.alert("Successfully created user account.");
	  }
	});
}