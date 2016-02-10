function chat() {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var messagesRef = fireRef.child("chatMessage");

  messagesRef.onAuth(function(authData) {
    if (authData) {
      var username = authData.password.email;
      var messageField = document.getElementsByClassName('messageInput')[1];
      var message = messageField.value;
      
      //ADD TO FIREBASE DATA
      messagesRef.push({name:username, text:message});

      refreshChat();

      //CLEAR TEXTBOX
      messageField.value = "";
    } else {
      alert("You must login to chat.")
    }
  });  
}

function refreshChat() {
  var fireRef = new Firebase("https://vibekey-open.firebaseio.com/");
  var messagesRef = fireRef.child("chatMessage");
  var messageList = document.getElementsByClassName('chat_messages')[1];

  messageList.innerHTML = "";
  messagesRef.on('value', function (data) {
    data.forEach(function (snapshot) {
      //GET DATA
        var data = snapshot.val();
        var username = data.name.split("@")[0];
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
    });
  });
}