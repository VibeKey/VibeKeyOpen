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
      var messageField = document.getElementById('messageInput');
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
  var messageList = $('#message_list');

  messagesRef.limitToLast(10).on('child_added', function(snapshot) {
      //GET DATA
      var data = snapshot.val();
      var username = data.name.split("@")[0];
      var message = data.text;

      //CREATE ELEMENTS MESSAGE & SANITIZE TEXT
      var messageElement = $("<li>");
      var nameElement = $("<strong class='chat-username'></strong>")
      nameElement.text(username);
      messageElement.text(" : " + message).prepend(nameElement);

      //ADD MESSAGE
      //var option = document.createElement('option');
      //option.id = option.value = "message";
      //option.text =  username + " : " + message;
      messageList.append(messageElement);

      //SCROLL TO BOTTOM OF MESSAGE LIST
      messageList[0].scrollTop = messageList[0].scrollHeight;
  });

  clearChat();
}

function clearChat() {
  var messageList = document.getElementById('message_list');
  messageList.innerHTML = "";
}