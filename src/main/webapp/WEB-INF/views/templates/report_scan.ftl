<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="refresh"content="160;url=${url}">
    <title>${rc.contextPath}</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/js/highcharts.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/js/highcharts-zh_CN.js"></script>
    <script src="${rc.contextPath}/js/variwide.js"></script>

</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:5px ;">

<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button " class="navbar-toggle btn-primary" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

        <a class="navbar-brand hidden" href="#"><img class="" src="${rc.contextPath}/logo_header.png" style="width:100px; ;"></a>

        <a class="navbar-brand " href="${synthesizedUrl}"> <strong class="h3">${companyName} ${storageSpace.name}</strong> </a>
        <span id="mqttConnect" class="label label-danger">status</span>

    </div>
<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav hidden">

    <#if links??>
        <#list links as link>
            <li class="">

                <#if link.url??>
                    <a href="${link.url!''}">

                    ${link.name}

                    </a>

                <#else>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"> ${link.name} <b class="caret"></b></a>
                    <ul class="dropdown-menu">

                        <#list link.subMenu as menu>
                            <li><a href="${menu.url}">${menu.name}</a></li>

                        </#list>

                    </ul>
                </#if>


            </li>
        </#list>
    </#if>




    </ul>




</nav>


<div class=" row" style="padding-bottom: 5px">

    <div class="col-lg-12">

        <div class="col-lg-3">
            <div><marquee behavior="scroll" direction="left" scrollamount="5"><label class="text-danger">扫一扫验证提煤授权</label></marquee></div>

            <script type="text/javascript" src="${rc.contextPath}/js/qrcode.js"></script>
            <#if permanentQrcode??>
                <input id="text" type="hidden" value="${permanentQrcode}" style="width:80%" />
            <#else>
                <input id="text" type="hidden" value="" style="width:80%" />
            </#if>

            <div id="qrcode"></div>
            <g id="qrcode_div"/>
            <script type="text/javascript">
                var qrcode = new QRCode(document.getElementById("qrcode_div"), {
                    width : 200,
                    height : 200,
                    //  useSVG: true
                });
                // qrcode.clear(); // clear the code.
                // qrcode.makeCode("http://naver.com"); // make another code.
                function makeCode () {
                    var elText = document.getElementById("text");

                    /*                           if (!elText.value) {
                                                   alert("Input a text");
                                                   elText.focus();
                                                   return;
                                               }*/

                    qrcode.makeCode(elText.value);
                }

                makeCode();

                $("#text").
                on("blur", function () {
                    makeCode();
                }).
                on("keydown", function (e) {
                    if (e.keyCode == 13) {
                        makeCode();
                    }
                });
            </script>


        </div>
        <div class="col-lg-9">
            <table class=" table-striped" id="inventory-table" data-url="${inventoryUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
                   data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                   data-query-params-type="unlimit"
                   data-query-params="queryParams_company"
                   data-response-handler="handleResponse"
                   data-pagination="true"
                   data-side-pagination="server"
                   data-page-number="1"
                   data-page-list="[10]"
                   data-page-size="10"

                   data-click-to-select="true"
                   data-single-select="true">

                <colgroup>
                    <col style="width:10%"/>

                    <col style="width:5%"/>
                    <col style="width:15%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>

                    <col style="width:15%"/>
                    <col style="width:15%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                </colgroup>
                <thead>
                <tr>





                    <th data-field="coalType" data-formatter="productOperationFormatter">煤种</th>
                    <th data-field="granularity">工艺</th>
                    <th data-field="quote" data-formatter="priceOperationFormatter">当前价格</th>

                    <th data-field="quantityOnHand" data-formatter="inventoryOperationFormatter">当前库存</th>

  <#--                  <th data-field="quantityOnHand" data-formatter="waitingCountLineupInfoFormatter">等待排队</th>-->
                    <th data-field="quantityOnHand" data-formatter="loadingCountLineupInfoFormatter">正在装载</th>
                    <th data-field="quantityOnHand" data-formatter="averageLaytimeLineupInfoFormatter">平均装车时间</th>

                    <th data-field="no">编号</th>
                    <th data-field="productNo">产品编号</th>




                </tr>
                </thead>
            </table>

        </div>





    </div>
 <div class="col-lg-3">
            <form class="form-horizontal well" id="addLineModalForm" role="form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <fieldset>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="ds_host">编号</label>
                        <div class="col-sm-8">
                            <input class="form-control" id="ds_host" name="no" type="text" placeholder="编号"/>
                        </div>

                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label" for="ds_username">用户验证</label>
                        <div class="col-sm-8">
                            <input class="form-control" id="ds_username" type="text" placeholder="root"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="ds_username"></label>
                        <div class="col-sm-4">
                            <button id="synchronizeBtn"  ref="${verifyUrl}" type="button" class="btn btn-primary" >
                                修改
                            </button>

                        </div>

                    </div>

                </fieldset>

                <script type="text/javascript">

                    function refresh() {
                        $('#station-table').bootstrapTable('refresh');
                    }


                    $("#synchronizeBtn").click(function() {

                        alert("dddddddddddddddddddddddddddd" +$('#addLineModalForm').serialize());
                        var req = $.ajax({
                            url:  $('#synchronizeBtn').attr('ref'),
                            type: 'post',
                            data:  $('#addLineModalForm').serialize(),
                        });
                        req.done(function (data) {
                            if (data.status) {
                                alert("成功:"+data.message);
                                window.location.reload(true);
                            } else {
                                alert(data.message);
                            }
                        });


                    });


                </script>

            </form>
        </div>
    <div class="col-lg-9">

    <#--            <div class="" style="">
                    <div class=-"col-lg-12">
                        <div id="container" style="width:100%; height: 200px"></div>
                    </div>
                    <script type="text/javascript">



                        var req = $.ajax({
                            url: '${getInventoryUrl!''}',
                            type: 'get',
                            data: {
                            }
                        });
                        req.done(function (data) {


                            Highcharts.chart('container', {
                                chart: {
                                    type: 'variwide' // varwide 依赖 varwide.js
                                },
                                title: {
                                    text: '当前报价和库存',
                                    floating: true,
                                    align: "center"
                                },
                                subtitle: {
                                    text: null// '数据来源：<a href="http://ec.europa.eu/eurostat/web/' +         'labour-market/labour-costs/main-tables">eurostat</a>'
                                },
                                xAxis: {
                                    type: 'category',
                                    title: {
                                        text: '* 柱子宽度与 库存 成正比'
                                    }
                                },
                                legend: {
                                    enabled: false
                                },
                                series: [{
                                    name: '人工成本',
                                    data:data.data ,
                                    dataLabels: {
                                        enabled: true,
                                        format: '￥{point.y:.0f}/吨  <b> 存 {point.z} 吨</b>'
                                    },
                                    tooltip: {
                                        pointFormat: '报价： <b>￥ {point.y}/h</b><br>' +
                                                '库存: <b> {point.z} 吨</b><br>'
                                    },
                                    colorByPoint: true
                                }]
                            });
                        });


                    </script>
                </div>-->


        <div class="well">

            <table class=" table-striped" id="scanResult-table" data-url="${scanResultUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
                   data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                   data-query-params-type="unlimit"
                   data-query-params="queryParams_company"
                   data-response-handler="handleResponse"
                   data-pagination="true"
                   data-side-pagination="server"
                   data-page-number="1"
                   data-page-list="[10]"
                   data-page-size="10"
                   data-click-to-select="true"
                   data-single-select="true">
                <thead>
                <tr>

                    <th data-field="no">编号</th>

                    <th data-field="createDate">时间</th>
                    <th data-field="license">车牌</th>

                    <th data-field="distributor">提货单位</th>
                    <th data-field="product">产品</th>

                    <th data-field="status">状态</th>

                </tr>
                </thead>
            </table>

        </div>


    <#--            <table class=" table-striped" id="location-table" data-url="${beingLoadedUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
                       data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                       data-query-params-type="unlimit"
                       data-query-params="queryParams_company"
                       data-response-handler="handleResponse"
                       data-pagination="true"
                       data-side-pagination="server"
                       data-page-number="1"
                       data-page-list="[10]"
                       data-page-size="10"
                       data-click-to-select="true"
                       data-single-select="true">
                    <thead>
                    <tr>
                    &lt;#&ndash;                    <th data-field="state" data-radio="true"></th>&ndash;&gt;
                    &lt;#&ndash;                    <th data-field="createDate">时间</th>&ndash;&gt;

                        <th data-field="plateNumber">车牌</th>

                        <th data-field="distributor">提货单位</th>
                        <th data-field="product">产品</th>

                        <th data-field="status">状态</th>

                    </tr>
                    </thead>
                </table>

                <table class=" table-striped" id="location-table" data-url="${verifiedUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
                       data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                       data-query-params-type="unlimit"
                       data-query-params="queryParams_company"
                       data-response-handler="handleResponse"
                       data-pagination="true"
                       data-side-pagination="server"
                       data-page-number="1"
                       data-page-list="[10]"
                       data-page-size="10"
                       data-click-to-select="true"
                       data-single-select="true">
                    <thead>
                    <tr>

                        <th data-field="no">编号</th>

                        <th data-field="createDate">时间</th>
                        <th data-field="license">车牌</th>

                        <th data-field="distributor">提货单位</th>
                        <th data-field="product">产品</th>

                        <th data-field="status">状态</th>

                    </tr>
                    </thead>
                </table>
                -->
    </div>



</div>


<#--
<#include "./common/page_foot_section.ftl">
-->


</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>


<script type="text/javascript">


function originToDestinationInfoFormatter(value, row, index) {



    return '<div class="">'  + row.origin  +
            '<hr style="margin-top: 0px;       margin-bottom: 0px;" >'   +'<span class="pull-right" style="   margin-bottom: 0px; padding-bottom: 0px; ">'+ row.destination  +'</span></div>';

}
    function queryParams_company(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }
      //  alert(JSON.stringify(sender))
        return params;
    }

    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }
       // alert(JSON.stringify(sender))
        return params;
    }
    $('#supplies-table').bootstrapTable({
        onLoadSuccess: function (data) {
            $('[data-trigger="hover"]').popover();

        }
    });

function productOperationFormatter(value, row, index) {

    return  '<strong type="button" class="h4 text-danger">'+row.coalType+'</strong>';

}
function priceOperationFormatter(value, row, index) {

    return  '<strong type="button" class="">'+row.quote+' 元/吨</strong>';

}
function inventoryOperationFormatter(value, row, index) {

    return  '<strong type="button" class="">'+row.quantityOnHand+' 吨</strong>';

}

function loadingCountLineupInfoFormatter(value, row, index) {
    return  '<strong type="button" class="h4 danger">'+row.loadingCount+' </strong>';
}


function averageLaytimeLineupInfoFormatter(value, row, index) {
    return  '<strong type="button" class="h4 danger">'+row.averageLaytime+'分钟</strong>';
}


function waitingCountLineupInfoFormatter(value, row, index) {
    return  '<strong type="button" class="h4 danger">'+row.waitingCount+' </strong>';
}



    function ompanyInfoFormatter(value, row, index) {


        return  ' <div class="img-comment-list">  <div  class="comment-img"  > <img class="" src="'+row.companyLogoUrl+'"/> </div>'+
                ' <div class="comment-text">'+
                '<strong><a href="'+ row.companyUrl + '">' + row.companyName  + '</a></strong>'+
                '<p class="">@'+ row.inventoryCounty + '</p> '+
                '</div>'+
                ' </div>';
    }




    function refresh() {
      //  $('#role-table').bootstrapTable('refresh');
        // $('#capacity-table').bootstrapTable('refresh');
        $('#companies-table').bootstrapTable('refresh');


    }

    function handleResponse(original) {
        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;
        //alert(JSON.stringify(original));
        return res;
    }

$(function(){

    $('.selectpicker').selectpicker();



    $('.searchBtn').bind('click', function(){
        refresh();

    });






})
</script>





<script src="${rc.contextPath}/js/bootstrap-notify.min.js" type="text/javascript"></script>
<#--<script src="https://cdn.jsdelivr.net/sockjs/1/sockjs.min.js"></script>-->
<script src="${rc.contextPath}/js/sockjs.min.js"></script>
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
        var socket = new SockJS('/ws');


        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);

            stompClient.subscribe('${websocket_topic}', function (greeting) {

                console.log("------"+JSON.parse(greeting.body))
                console.log("------ event-table ");
                //  alert(JSON.stringify(greeting.body));
                var map = JSON.parse(greeting.body);


                if(map.type == "authorizeComplete"){
                    event_follower(map)
                }else{
                    event_default(map)

                }


            });
            stompClient.subscribe('${websocket_topic_status}', function (greeting) {
                /*
                                console.log("------"+JSON.parse(greeting.body))
                                console.log("------ event-table ");*/
                //  alert(JSON.stringify(greeting.body));
                var map = JSON.parse(greeting.body);
                if(map.type=="status"){

                    if(map.mqttConnect){
                        $("#mqttConnect").removeClass("label-danger");
                        $("#mqttConnect").addClass("label-success");
                    }

                    if(!map.mqttConnect){
                        $("#mqttConnect").removeClass("label-succes");
                        $("#mqttConnect").addClass("label-danger");
                    }

                    /*
                                        event_default(map)
                    */
                }
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





    function event_follower(map) {

        $('#scanResult-table').bootstrapTable('refresh');

        $.notify({
            title: map.companyName,
            message:map.nickname +" 关注了我们" + " "+ map.eventTime,
            delay: 5000,
            allow_dismiss: true,

        },{
            type: 'success',
            animate: {
                enter: 'animated fadeInUp',
                exit: 'animated fadeOutRight'
            },
            // type: 'minimalist',
            delay:5000,
            allow_dismiss: true,
            placement: {
                from: 'bottom',
                align: 'left'
            },
            offset: 20,
            spacing: 10,
            z_index: 1031,

        });


    }

    function event_default(map) {

     //   $('#inventory-table').bootstrapTable('refresh');
     //   $('#event-table').bootstrapTable('refresh');
        qrcode.clear(); // clear the code.
        qrcode.makeCode(map.content); // make another code.

    }


</script>

</script>

</html>