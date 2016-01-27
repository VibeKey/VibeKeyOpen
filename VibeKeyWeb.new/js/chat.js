function chat() {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var messagesRef = fireRef.child("chatMessage");

  messagesRef.onAuth(function(authData) {
    if (authData) {
      var username = authData.password.email;
      var messageField = document.getElementsByClassName('messageInput')[1];
      var messageList = document.getElementsByClassName('chat_messages')[1];
      var message = messageField.value;
      messagesRef.push({name:username, text:message});

      messagesRef.limitToLast(10).on('child_added', function (snapshot) {
        //GET DATA
        var data = snapshot.val();
        var username = data.name;
        var message = data.text;

        //CREATE ELEMENTS MESSAGE & SANITIZE TEXT
        var messageElement = $("<li>");
        var nameElement = $("<strong class='chat-username'></strong>")
        nameElement.text(username);
        messageElement.text(message).prepend(nameElement);

        //ADD MESSAGE
        var option = document.createElement('option');
        option.id = option.value = "message";
        option.text =  username + " : " + message;
        messageList.appendChild(option);

        //CLEAR TEXTBOX
        messageField.value = "";
      });
    } else {
      alert("You must login to chat.")
    }
  });  
}