
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤  提煤系统 </title>

    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>




</head>
<body>


<div class="col-lg-12">





    <div class="center-block" style="width:500px;">
        <video id="preview" class="pull-left" width="500" height="400" autoplay  ></video>
        <div class="col-lg-6 center">
            <label>扫一扫微信获得信息 </label>
            <div class="thumbnail">
            <#if qrcodeUrl??>
                <img src="${qrcodeUrl!'/logo.png'}">

            </#if>
            </div>

        </div>
        <div class="panel panel-success  ">
            <div class="panel-heading">
                <div class="row">

                    <div class="col-xs-12 text-right">


                        <div class="" style="">
                            <div class="input-group input-group-lg">

                                <input id="verificationCode" type="text" class="form-control bg-silver " placeholder="输入揽货编号">

									<span class="input-group-btn">
										<button id="--inputVerifyCodeBtn" class="btn btn-primary btn-large" type="button" onclick="refresh()"><i class="fa fa-pencil"   ></i>输入验证码</button>
									</span>
                            </div>


                            <hr>

                        </div>

                    </div>
                </div>
            </div>


            <div class="panel-footer announcement-bottom hidden">

                <div class="row ">
                    <div class="col-xs-6">



                    </div>
                    <div class="col-xs-6 text-right">
                        <i class="fa fa-arrow-circle-right"></i>
                    </div>
                </div>
            </div>
        </div>

    </div>




    </div>



</div>

<script type="text/javascript" src="https://rawgit.com/schmich/instascan-builds/master/instascan.min.js"></script>

<script type="text/javascript">
    let scanner = new Instascan.Scanner({ video: document.getElementById('preview') });
    scanner.addListener('scan', function (content) {
        console.log(content);
        $("#verificationCode").val(content);
        refresh();
        //  document.getElementById("verifycode").value=content;
    });
    Instascan.Camera.getCameras().then(function (cameras) {
        if (cameras.length > 0) {
            scanner.start(cameras[0]);
        } else {
            console.error('No cameras found.');
        }
    }).catch(function (e) {
        console.error(e);
    });
</script>


<script src="${rc.contextPath}/js/bootstrap-notify.min.js" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>
<script src="${rc.contextPath}/js/stomp.js" type="text/javascript"></script>
<script type="text/javascript">
    var stompClient = null;
    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    function connect() {
        var socket = new SockJS('${websocket_url}');
        // var socket = new SockJS('http://localhost:6080/hello');


        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);

          stompClient.subscribe('${websocket_topic}', function (greeting) {
           // stompClient.subscribe('/topic/configuration/1', function (greeting) {


              var map = JSON.parse(greeting.body);
              window.location.href = '${url}'+'?ticket='+map.ticket;




            /*    console.log("------"+JSON.parse(greeting.body))
                console.log("------"+JSON.stringify(greeting.body));
                //  alert(JSON.stringify(greeting.body));
                var map = JSON.parse(greeting.body);
                $.notify({
                    title: "Welcome:${websocket_url}",
                    message:map.plateNumber,
                    delay: 0
                },{
                    // type: 'minimalist',
                    delay: 0

                });*/
                //    label1.setContent(JSON.parse(greeting.body).plateNumber + Math.random())


                // showGreeting(JSON.parse(greeting.body));
            });


        });
    }

    function disconnect() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    }

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $( "#connect" ).click(function() { connect(); });
        connect();

        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendName(); });
    });









</script>


</body>
</html>