function ifEnterChat(e) {
    if(e.keyCode === 13){
      chat();
    }
}

function chat() {
  var messagesRef = new Firebase(FIREBASE_REF).child("chatMessage");

  messagesRef.onAuth(function(authData) {
    if (authData) {
      var username = authData.password.email;
      var messageField = document.getElementsByClassName('messageInput')[1];
      var message = messageField.value;
      
      //ADD TO FIREBASE DATA
      messagesRef.push({name:username, text:message});

      //CLEAR TEXTBOX
      messageField.value = "";
    } else {
      alert("You must login to chat.")
    }
  });  
}

function syncChat() {
  var messagesRef = new Firebase(FIREBASE_REF).child("chatMessage");
  var messageList = document.getElementsByClassName('message_list')[1];

  messagesRef.limitToLast(10).on('child_added', function(snapshot) {
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

      //SCROLL TO BOTTOM OF MESSAGE LIST
  });

  clearChat();
}

function clearChat() {
  var messageList = document.getElementsByClassName('message_list')[1];
  messageList.innerHTML = "";
}