var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#contentTimeTable").show();
    }
    else {
        $("#contentTimeTable").hide();
    }
    $("#contentTimeData").html("");
}

function connect() {
    var socket = new SockJS('/broadcaster-websocket-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/broadcast', function (payload) {
        	pushData(JSON.parse(payload.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Connection Terminated");
}


function pushData(jsonObject) {
    $("#contentTimeData").append("<tr><td>" + jsonObject.content + "</td><td>" + jsonObject.timestamp + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

