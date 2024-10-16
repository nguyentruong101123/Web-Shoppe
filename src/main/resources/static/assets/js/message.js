'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#messageInput');
var messageArea = document.querySelector('#messages'); // Khai báo messageArea

var stompClient = null;

function connect() {
	var socket = new SockJS('/ws'); // Sửa cú pháp thành SockJS WebSocket
	stompClient = Stomp.over(socket);

	stompClient.connect({}, onConnected, onError);
}

function onConnected() {
	stompClient.subscribe('/topic/public', onMessageReceived);
}

function onError(error) {
	console.log('Could not connect to WebSocket server. Please refresh this page to try again!');
}

function sendMessage(event) {
	var messageContent = messageInput.value.trim();
	if (messageContent && stompClient) {
		var chatMessage = {
			chatConversation: { id: conversation },  // Đã sửa thành conversation
			sender: { username: username },
			content: messageInput.value,
		};
		stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
		messageInput.value = '';
	}
	event.preventDefault();
}
function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');
    messageElement.classList.add('chat-message');
    
    // Xác định người gửi
    var isSender = message.sender === username; // username là tài khoản hiện tại

    // Tạo avatar
    if (!isSender) {
        var avatarElement = document.createElement('img');
        avatarElement.src = message.avatarUrl ? `/assets/images/avatas/${message.avatarUrl}` : '/assets/images/avatas/default.png';
        avatarElement.alt = message.sender;
        avatarElement.classList.add('avatar');
        messageElement.appendChild(avatarElement);
    }

    // Tạo nội dung tin nhắn
    var textElement = document.createElement('div');
    textElement.classList.add('content');
    textElement.textContent = message.content;

    // Thêm các phần tử vào DOM
    messageElement.appendChild(textElement);
    
    if (isSender) {
        messageElement.classList.add('sender'); // Thêm lớp cho người gửi
    } else {
        messageElement.classList.add('recipient'); // Thêm lớp cho người nhận
    }

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight; // Cuộn xuống dưới cùng
}


messageForm.addEventListener('submit', sendMessage, true);
