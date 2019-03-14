
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤 ${company.companyName} 提煤系统 ${websocket_topic}</title>

    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/js/mustache.min.js" type="text/javascript"></script>

</head>
<body>


<div class="col-lg-12">



    <div class ="col-lg-6" >
        <div class="col-lg-12 row">
            <video id="preview" class="pull-left" width="400" height="300" autoplay  ></video>

        </div>



        <div role="tabpanel">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">


            <li role="presentation" class="  active"><a href="#waitingEnterOperations" aria-controls="waitingEnterOperations" role="tab"
                                                                                                                          data-toggle="tab">等待装车</a></li>
            <li role="presentation" class="<#if tabIndex?? && tabIndex == 'loading'>active</#if>"><a href="#loadingOperations" aria-controls="loadingOperations" role="tab"
                                                                                                     data-toggle="tab">正在装车</a></li>
            <li role="presentation" class="<#if  tabIndex?? && tabIndex == 'shipping'>active</#if>"><a href="#leaveOperations" aria-controls="leaveOperations" role="tab"
                                                                                                       data-toggle="tab">已装车</a></li>

        </ul>
        <!-- Tab panes -->

        <div class="tab-content">

        <#--table header row-->
            <div role="tabpanel" class="tab-pane active" id="waitingEnterOperations">

                <div class="row">
                <#--table header row-->
                    <div class="col-lg-12" style="margin-top: 10px">


                        <table class="table-striped" id="created-table" data-url="${storageSpaceCreateOperationsUrl}" data-toggle="table" data-classes="table table-hover"   data-method="POST"
                               data-content-type="orderExecutionView/x-www-form-urlencoded; charset=UTF-8"
                               data-query-params-type="unlimit"
                               data-query-params="queryParams"
                               data-response-handler="handleResponse"
                               data-pagination="true"
                               data-side-pagination="server"
                               data-page-number="1"
                               data-page-list="[10]"
                               data-page-size="10"
                               data-click-to-select="true"
                               data-single-select="true"
                                >

                            <thead>
                            <tr>

                                <th data-field="id">ID</th>


                                <th data-field="lpn | truckType | truckLoad"  data-formatter="canvassTruckInfoFormatter">车牌号</th>

                                <th data-field="truckPoc | truckPhone" data-formatter="operationContactInfoFormatter">联系人-联系电话</th>
                                <th data-field="truckLoad">运费-载重</th>

                            </tr>
                            </thead>
                        </table>


                    </div>

                </div>

            </div>

            <div role="tabpanel" class="tab-pane " id="loadingOperations">
                <div class="row">
                    <div class="col-lg-12">

                    </div>
                </div>

            <#--table header row-->
                <div class="" style="margin-top: 10px">


                    <table class="table-striped" id="loading-operation-table" data-url="${storageSpaceLoadingOperationsUrl}" data-toggle="table" data-classes="table table-hover"   data-method="POST"
                           data-content-type="orderExecutionView/x-www-form-urlencoded; charset=UTF-8"
                           data-query-params-type="unlimit"
                           data-query-params="queryParams"
                           data-response-handler="handleResponse"
                           data-pagination="true"
                           data-side-pagination="server"
                           data-page-number="1"
                           data-page-list="[10]"
                           data-page-size="10"
                           data-click-to-select="true"
                           data-single-select="true"
                            >

                        <thead>
                        <tr>

                            <th data-field="boundDate">进场时间</th>

                            <th data-field="lpn | truckType | truckLoad"  data-formatter="canvassTruckInfoFormatter">车牌号</th>
                            <th data-field="tareWeight">皮重</th>
                            <th data-field="grossWeight">毛重</th>
                            <th data-field="netWeight">净重</th>



                            <th data-field="cargoType">产品类型</th>



                        </tr>
                        </thead>
                    </table>
                </div>
            <#--table content row-->

            </div>
            <div role="tabpanel" class="tab-pane " id="leaveOperations">
                <div class="row">
                    <div class="col-lg-12">



                        <div class="" style="margin-top: 10px">

                            <table class="table-striped" id="shipping-table" data-url="${storageSpaceLeaveOperationsUrl}" data-toggle="table" data-classes="table table-hover"   data-method="POST"
                                   data-content-type="orderExecutionView/x-www-form-urlencoded; charset=UTF-8"
                                   data-query-params-type="unlimit"
                                   data-query-params="queryParams"
                                   data-response-handler="handleResponse"
                                   data-pagination="true"
                                   data-side-pagination="server"
                                   data-page-number="1"
                                   data-page-list="[10]"
                                   data-page-size="10"
                                   data-click-to-select="true"
                                   data-single-select="false"
                                    >

                                <thead>
                                <tr>

                                    <th data-field="plateNumber">车牌号</th>
                                    <th data-field="truckLoad">矿发净重</th>
                                    <th data-field="type">类型</th>
                                    <th data-field="status">状态</th>

                                </tr>
                                </thead>
                            </table>

                        </div>

                    </div>


                </div>

            </div>



        </div>
        </div>



        </div>



    <div class="col-lg-6">

        <video id="preview11" class="pull-right" width="100" height="100" autoplay style="margin-top: 0dp" ></video>
        <div>     <h1 class="page-header "><strong>${company.companyName} 打印提煤单</strong>
        </h1>


        </div>

        <div class="">

            <div class="panel panel-success">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <i class="fa fa-comments fa-5x"></i>
                        </div>
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
        <div class="" style="">
            <div class="input-group hidden">

                <input id="verifyCode__" type="text" class="form-control bg-silver" placeholder="输入揽货编号">
									<span class="input-group-btn">
										<button id="--inputVerifyCodeBtn" class="btn btn-primary" type="button" onclick="refresh()"><i class="fa fa-pencil"   ></i>输入验证码--</button>
									</span>
            </div>


            <hr>

        </div>


        <div class="" >
            <div data-spy="scroll" data-target="#navbar-example" data-offset="0"
                 style="height:300px;overflow:auto; position: relative;">

                <table class="table-striped" id="result-table" data-url="${operationResultListUri}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
                       data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                       data-query-params-type="unlimit"
                       data-query-params="queryParamsResult"
                       data-response-handler="resultHandleResponse"
                       data-pagination="true"
                       data-side-pagination="server"
                       data-page-number="1"
                       data-page-list="[3]"
                       data-page-size="3"
                <#--
                                       data-click-to-select="true"
                -->
                       data-single-select="true"
                       data-card-view="true">
                    <thead>
                    <tr>
                        <th data-field="createDate">时间：</th>
                        <th data-field="shippingNo">运单编号：</th>
                        <th data-field="contactName">联系人：</th>
                        <th data-field="contactPhone">联系电话：</th>

                        <th data-field="operation"  data-formatter="resultOperationFormatter" >操作</th>

                    </tr>
                    </thead>
                </table>
            </div>


        </div>




    </div>



</div>
<div class="modal fade" id="addOperationModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">


            <form id="operationForm"  role="form" action="" method="post" >
                <input  type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="userModalLabel">确认提煤单</h4>
                </div>
                <div id="target">Loading...</div>





            </form>
            <div class="modal-footer">



                <form id="create-capacity-Form"  >
                    <input  type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <button type="button"  class="btn btn-default" data-dismiss="modal">取消</button>
                    <input type="hidden" class="form-control" id="id" name="id" value="" required="" title="提货名称" placeholder="堆场名称">

                    <button type="button" id="addOperationBtn" ref="${agreeOperationsUrl}" value="{coalPromotionDetails.id}" class="btn btn-primary">确定新建 进场车辆</button>
                </form>
            </div>

        </div>
    </div>
</div>


<#--
<#include "./common/page_websocket_workbench_section.ftl">

-->


<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>

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
<script id="template" type="x-tmpl-mustache">
                             <table class="table  .js-test">

                        <tr class="">
                            <td>
                                提单编号：{{no}}
                            </td>
                        </tr>
                        <tr class="">
                            <td>
                                产品类型：{{productName}}
                            </td>
                        </tr>
                                                <tr class="">
                            <td>
                                煤矿：{{companyName}}
                            </td>
                        </tr>

                        <tr>
                            <td>
                                购买方：{{partnerName}}
                            </td>
                        </tr>



                        <tr class="active">
                            <td>
                                车牌号：{{plateNumber}}
                            </td>
                        </tr>
                        <tr class="active">
                            <td>
                                毛重：{{ name }}$storageSpace.status!''$
                            </td>
                        </tr>

                        <tr class="active">
                            <td>
                                净重：{{ name }}$storageSpace.status!''$
                            </td>
                        </tr>
                        <tr>
                            <td>
                                汽车类型：{{ vehicleType }}
                            </td>
                        </tr>

                       <tr>
                            <td>
                                司机姓名：{{ driverName }}
                            </td>
                        </tr>
                       <tr>
                            <td>
                                司机手机号：{{ driverPhone }}
                            </td>
                        </tr>


                   <tr>
                            <td>
                                状态：{{ status }}
                            </td>
                        </tr>
                    </table>
</script>

<script type="text/javascript">
$("button#addOperationBtn").click(function(){

    alert($('#create-capacity-Form').serialize());
    var req = $.ajax({
        url:  $(this).attr('ref'),
        type: 'post',
        data: $('#create-capacity-Form').serialize()
    });
    req.done(function (data) {

        alert("json" + JSON.stringify(data));


        if (data.status) {
            alert(data.message);

        } else {
            alert(data.message);
        }
    });

});

function loadUser() {

}
loadUser();



    function queryParamsResult(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var verificationCode = $.trim($("#verificationCode").val());

        if (verificationCode) {
            params.verificationCode = verificationCode;
        }
        return params;
    }

    function resultOperationFormatter(value, row, index) {
        return '<button type="button" value="' + row.id + '" class="btn btn-primary checkBtn">查看</button>';
    }



    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;
        var status = $.trim($("#status").val());
        var createDateS = $.trim($("#createDateS").val());
        var createDateE = $.trim($("#createDateE").val());

        var sender = $.trim($("#sender").val());

        if (status) {
            params.status = status;
        }

        if (createDateS) {
            params.createDateS = createDateS;
        }
        if (createDateE) {
            params.createDateE = createDateE;
        }
        if (sender) {
            params.sender = sender;
        }
        return params;
    }
    function refresh() {
        $('#result-table').bootstrapTable('refresh');
    }



    function canvassing_queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;
        var status =  $.trim($("#canvassing-table-selectpicker").val());

        var shipmentNo = $.trim($("#shipmentNo").val());
        var personName = $.trim($("#personName").val());
        var phone = $.trim($("#phone").val());
        var shipmentNo = $.trim($("#shipmentNo").val());


        if (status) {
            alert(status);
            params.status = status;
        }
        if (shipmentNo) {
            params.lpn = lpn;
        }

        if (shipmentNo) {
            params.truckLoad = truckLoad;
        }
        return params;
    }

    String.prototype.temp = function(obj) {
        return this.replace(/\$\w+\$/gi, function(matchs) {
            var returns = obj[matchs.replace(/\$/g, "")];
            return (returns + "") == "undefined"? "": returns;
        });
    };

function resultHandleResponse(original) {
    var res = {};
    res.rows = original.content;
    res.total = original.totalElements;
    //alert(JSON.stringify(original));

    var text = JSON.stringify(original.content[0]);
   // alert(text);
    var template = $('#template').html();
    Mustache.parse(template);   // optional, speeds up future uses
    var rendered = Mustache.render(template, original.content[0]);
    $('#target').html(rendered);

//alert(JSON.stringify(original.content[0]['id']));
    $("#addOperationModal").modal('show');
    $("#id").attr("value",original.content[0]['id']);
  //  $("#addOperationModal").find('#operationId').val(1);
    return res;
}
    function handleResponse(original) {
        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;
       alert(JSON.stringify(original));

        return res;
    }



    function loadFormatter(value, row, index) {

        return  row.truckLoad + '吨';
    }


    function canvassingOperationFormatter(value, row, index) {

        return '<a type="button" href="' + row.url + '" class="btn btn-default ">查看</a>'+
                '<button type="button" value="' + row.id + '" class="btn btn-default confirnArrivalBtn">确认到场</button>';

    }
    $('#result-table').bootstrapTable({
        onLoadSuccess: function (data) {

            $('#result-table .checkBtn').not('.hidden').each(function () {
                $(this).click(function (){
                    $("#addOperationModal").modal('show');

                });
            });


        }

    });










    $('#supplies-table').bootstrapTable({
        onLoadSuccess: function (data) {
            $('#supplies-table .requireShipmentBtn').not('.hidden').each(function () {
                $(this).click(function () {
                    alert("applicationId is:" +$("#applicationId").val() );

                    var req = $.ajax({
                        url:this.value ,//'/usercenter/capacitylogistis/command=agreeCanvassings',
                        type: 'post',
                        data: {applicationId:$("#applicationId").val()}
                    });
                    req.done(function (data) {
                        if (data.status) {
                            var applyId = $('.modelOrderId').html();
                            alert(data.message);

                            //  window.location.href = "/usercenter/cargocanvassing/canvassing?id=" + $("#applicationId").val();<#-- $("#quotationId").val();-->

                        } else {
                            alert(data.message);

                        }
                    });

                });
            });
        }
    });
    var requireShipmentBtn = function () {

        alert("applicationId is:" + this.value );


    }



function canvassTruckInfoFormatter(value, row, index) {

    //      return     '<div class="col-lg-1 text-center"><button type="button" value="' +row.approveCommandUrl + '" class="btn approveCanvassingBtn btn-link">同意申请，生产运单</button></div>';

    return   '<button type="button" value="' + row.id + '" class="btn btn-default agreeBtn">'+row.plateNumber+'</button>';

}
    function pendingOperationFormatter(value, row, index) {

        //      return     '<div class="col-lg-1 text-center"><button type="button" value="' +row.approveCommandUrl + '" class="btn approveCanvassingBtn btn-link">同意申请，生产运单</button></div>';

        return   '<button type="button" value="' + row.id + '" class="btn btn-default agreeBtn">同意进场</button>'+
                '<a type="button" href="' + row.url + '" class="btn btn-default deleteBtn">查看</a>';

    }

    $('#loading-operation-table').bootstrapTable({
        onLoadSuccess: function (data) {

            $('#loading-operation-table .leaveBtn').not('.hidden').each(function () {
                $(this).click(function (){
                    alert("ddddddddddd");
                    $("#exitModal").modal('show');
                    $('#transportId').val(this.value);

                });
            });


        }

    });


    $('#pending-enter-table').bootstrapTable({
        onLoadSuccess: function (data) {

            $('#pending-enter-table .agreeBtn').not('.hidden').each(function () {
                $(this).click(function (){
                    $("#agreeModal").modal('show');
                    $('#transportId').val(this.value);

                    /*                var req = $.ajax({
                                        url: this.value,
                                        type: 'post'
                                    });
                                    req.done(function (data) {
                                        if (data.status) {
                                            alert(data.message);
                                            $('#canvassing-table').bootstrapTable('refresh');

                                        } else {
                                            alert(data.message);

                                        }
                                    });
                                    alert(this.value);*/

                });
            });


        }

    });



    function operationContactInfoFormatter(value, row, index) {



        return            '<div class="img-comment-list">' +
                '         <div  class="comment-img"  > <img class="" src="'+ row.contactAvatarUrl + '"/> </div> '+

                ' <div class="comment-text">'+
          //      '<strong><a href="/person?id='+ row.driverId + '">' + row.driverName  + '</a></strong>'+

                '</div>' +
                '</div>';

    };



/*
    setInterval(function(){
        $.get('/ImStillAlive.action');
    }, 20000); // 14 mins * 60 * 1000
*/





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
              //    stompClient.subscribe('/topic/COALPIT_DELIVERY_workbench/1', function (greeting) {

                console.log("------"+JSON.parse(greeting.body))
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

                });
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