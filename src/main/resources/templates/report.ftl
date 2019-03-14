<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
<#--    <meta http-equiv="refresh"content="${interval};url=${url}">-->
    <title>${rc.contextPath}</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<#--
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
-->   <#-- <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>-->

    <script src="${rc.contextPath}/js/highcharts.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/js/highcharts-zh_CN.js"></script>
    <script src="${rc.contextPath}/js/variwide.js"></script>
    <script src="${rc.contextPath}/js/news-box/jquery.bootstrap.newsbox.min.js"></script>



</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:5px ;width: 1820px;">

<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">

        <a class="navbar-brand " href=""> <img class="" src="${rc.contextPath}/logo_header.png" style="width:100px;"> </a>
          <a class="navbar-brand " href="${synthesizedUrl}"><strong>${companyName} ${storageSpace.name}</strong></a>


     <#include "./tip/status.ftl">





    </div>


</nav>


<div class=" row" style="padding-bottom: 5px">
    <div class="" style="">
        <div class=-"col-lg-12">
            <div id="priceTrendencyContainer" style="width:100%; height: 300px"></div>
        </div>
        <script type="text/javascript">




            var req = $.ajax({
                url: '${trendencyUrl}',
                type: 'get',
                data: {
                }
            });
            req.done(function (data) {


   /*             alert(JSON.stringify(data));*/
                var chart = Highcharts.chart('priceTrendencyContainer', {
                    chart: {
                        type: 'spline'
                    },
                    title: {
                        text: '某地积雪厚度监测'
                    },
                    subtitle: {
                        text: ''
                    },
                    xAxis: {
                        type: 'datetime',

                        dateTimeLabelFormats: { day: '%m-%d' },
                        title: {
                            text: null
                        }
                    },
                    colors: ['#6CF', '#39F', '#06C', '#036', '#000'],
                    yAxis: {
                        title: {
                            text: '积雪 厚度 (m)'
                        },
                        min: 0
                    },
                    tooltip: {
                        headerFormat: '<b>{series.name}</b><br>',
                        pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
                    },
                    plotOptions: {
                        spline: {
                            marker: {
                                enabled: true
                            }
                        }
                    },
                    series:data
                });


            });


        </script>
    </div>

        <div class="col-lg-7">
            <div class="" style="">
                <div class=-"col-lg-12">
                    <div id="container" style="width:100%; height: 400px"></div>
                </div>
                <script type="text/javascript">



                    refresh_chart();

                    function refresh_chart() {
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
                                        format: '<h3>￥{point.y:.0f}/吨 </h3> <b>  存{point.z}吨</b>',
                                        style:{
                                            fontSize: 20
                                        }
                                    },
                                    tooltip: {
                                        pointFormat: '报价： <b>￥ {point.y}/h</b><br>' +
                                                '库存: <b> {point.z} 吨</b><br>'
                                    },
                                    colorByPoint: true
                                }]
                            });


                        });
                    }

                </script>
            </div>

            <#if quotationPlan??>
<h1> <span class="label label-danger">${quotationPlan.id} ${quotationPlan.triggerDate!''} 预调价</span></h1>

            </#if>
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
<#--                    <col style="width:5%"/>
                    <col style="width:5%"/>-->
                    <col style="width:15%"/>
                    <col style="width:10%"/>

                    <col style="width:5%"/>
                    <col style="width:15%"/>
                <#if quotationPlan??>
                    <col style="width:15%"/>

                </#if>
    <col style="width:10%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
    <col style="width:5%"/>
                </colgroup>
                <thead>
                <tr>




<#--                    <th data-field="no">编号</th>
                    <th data-field="productNo">产品编号</th>-->

                    <th data-field="coalType" data-formatter="productOperationFormatter">煤种</th>
                    <th data-field="granularity">工艺</th>
                    <th data-field="tax" >税</th>

                    <th data-field="quote" data-formatter="priceOperationFormatter">当前价格</th>
            <#if quotationPlan??>
                    <th data-field="quote" data-formatter="probationaryPriceOperationFormatter">预计调价</th>

            </#if>
                    <th data-field="quantityOnHand" data-formatter="inventoryOperationFormatter">当前库存</th>

                    <th data-field="indicator1">热量</th>
                    <th data-field="indicator2">硫</th>
                    <th data-field="indicator3">灰</th>
                    <th data-field="indicator4">水</th>


                </tr>
                </thead>
            </table>

            <div class="well">
                <table class=" table-striped " style="font-size: 20px; line-height: 1.428;" id="location-table" data-url="${intelligentUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                    <#--                    <th data-field="state" data-radio="true"></th>-->
                    <#--                    <th data-field="createDate">时间</th>-->

                        <th data-field="createDate">时间</th>
                        <th data-field="license">车牌</th>
                        <th data-field="plateNumber">车牌</th>

                        <th data-field="distributor">提货单位</th>
                        <th data-field="product">产品</th>

                        <th data-field="status">状态</th>

                    </tr>
                    </thead>
                </table>

            </div>

        </div>
    <div class="col-lg-2">




        <div class="col-lg-12">
            <div><marquee behavior="scroll" direction="left" scrollamount="5"><label class="text-danger">实时关注${companyName}</label></marquee></div>

            <script type="text/javascript" src="${rc.contextPath}/js/qrcode.js"></script>
            <#if permanentQrcode??>
                <input id="text" type="hidden" value="${permanentQrcode.content!""}" style="width:80%" />
            <#else>
                <input id="text" type="hidden" value="123" style="width:80%" />
            </#if>

        <#--                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">

                        </svg>-->
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
        <div class="col-xs-12 divider text-center">
            <div class="col-xs-12 col-sm-12 emphasis">
                <h2><strong> ${followers} </strong></h2>
                <p><small>关注</small></p>
            </div>

        </div>

        <table class=" table-striped" id="follower-table" data-url="${followerUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
            <#--                    <th data-field="state" data-radio="true"></th>-->
            <#--                    <th data-field="createDate">时间</th>-->


                <th data-field="nickName">名称</th>

                <th data-field="attendTime">时间</th>

            </tr>
            </thead>
        </table>
        <div class="col-xs-12">
            <ul class="demo">

                <li class="news-item">
                    <table cellpadding="4">
                        <tr>
                            <td><img src="images/1.png" width="60" class="img-circle" /></td>
                            <td> News 1<a href="#">Read more...</a></td>
                        </tr>
                    </table>
                </li>

                <li class="news-item">
                    <table cellpadding="4">
                        <tr>
                            <td><img src="images/2.png" width="60" class="img-circle" /></td>
                            <td> News 2<a href="#">Read more...</a></td>
                        </tr>
                    </table>
                </li>

                <li class="news-item">
                    <table cellpadding="4">
                        <tr>
                            <td><img src="images/3.png" width="60" class="img-circle" /></td>
                            <td> News 3<a href="#">Read more...</a></td>
                        </tr>
                    </table>
                </li>


            </ul>
        </div>
    </div>

        <div class="col-lg-3">


            <div id="inventoryContainer" style="min-width:400px;height:400px"></div>
            <script>

                function refresh_container_inventoryData() {
                    $.get("${pie_inventoryDataUrl}", function(result){
                        var  chart = Highcharts.chart('inventoryContainer', {
                            chart: {
                                plotBackgroundColor: null,
                                plotBorderWidth: null,
                                plotShadow: false
                            },
                            title: {
                                text: '库存占比'
                            },
                            tooltip: {
                                headerFormat: '{series.name}<br>',
                                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
                            },
                            plotOptions: {
                                pie: {
                                    allowPointSelect: true,
                                    cursor: 'pointer',
                                    dataLabels: {
                                        enabled: true,
                                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                                        style: {
                                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                                        }
                                    },
                                    states: {
                                        hover: {
                                            enabled: false
                                        }
                                    },
                                    slicedOffset: 20,         // 突出间距
                                    point: {                  // 每个扇区是数据点对象，所以事件应该写在 point 下面
                                        events: {
                                            // 鼠标滑过是，突出当前扇区
                                            mouseOver: function() {
                                                this.slice();
                                            },
                                            // 鼠标移出时，收回突出显示
                                            mouseOut: function() {
                                                this.slice();
                                            },
                                            // 默认是点击突出，这里屏蔽掉
                                            click: function() {
                                                return false;
                                            }
                                        }
                                    }
                                }
                            },
                            series: [{
                                type: 'pie',
                                name: '库存',
                                data: //[{name:"樊河三号矿",y:4364.77},{name:"张家洼煤矿",y:2000.0},{name:"山东煤矿",y:1000.0},{name:"兴维煤矿",y:400.0},{name:"正和煤矿",y:5500.0},{name:"胜凯煤矿",y:3400.0}]
                                result

                                /*[
                                    ['Firefox',   45.0],
                                    ['IE',       26.8],
                                    {
                                        name: 'Chrome',
                                        y: 12.8,
                                        sliced: true, // 突出显示这个点（扇区），用于强调。
                                    },
                                    ['Safari',    8.5],
                                    ['Opera',     6.2],
                                    ['其他',   0.7]
                                ]*/
                            }]
                        });
                    });

                }
                refresh_container_inventoryData();
            </script>
            <table class=" table-striped" id="location-table" data-url="${beingLoadedUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
<#--                    <th data-field="state" data-radio="true"></th>-->
<#--                    <th data-field="createDate">时间</th>-->

                    <th data-field="plateNumber">车牌</th>

                    <th data-field="distributor">提货单位</th>
                    <th data-field="product">产品</th>

                    <th data-field="status">状态</th>

                </tr>
                </thead>
            </table>


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

function operationInfoFormatter(value, row, index) {

    //      return     '<div class="col-lg-1 text-center"><button type="button" value="' +row.approveCommandUrl + '" class="btn approveCanvassingBtn btn-link">同意申请，生产运单</button></div>';

    return  '<a type="button" href="' + row.url + '" class="b">查看</a>';

}

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

    return  '<strong type="button" class="h3 text-danger">'+row.coalType+'</strong>';

}
function priceOperationFormatter(value, row, index) {

    return  '<strong type="button" class="h3 danger">'+row.quote+' 元/吨</strong>';

}
function probationaryPriceOperationFormatter(value, row, index) {

    if(row.probationaryQuote!= null){

        if(row.probationaryQuote >row.quote ){
            var diff =   row.probationaryQuote -row.quote  ;

            return  '<h2><span class="label label-danger">涨'+ diff+' 元/吨</span></h2><h2>'  +  row.probationaryQuote+' 元/吨</h2>' ;

        }
        if(row.probationaryQuote < row.quote ){
            var diff =  row.quote - row.probationaryQuote ;

            return  '<h2><span class="label label-success">降'+diff+' 元/吨</span></h2><h2>'  +  row.probationaryQuote+' 元/吨</h2>' ;

        }
    }else{
        return '';
    }

}

function inventoryOperationFormatter(value, row, index) {

    return  '<strong type="button" class="h3 danger">'+row.quantityOnHand+' 吨</strong>';

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
        //   var socket = new SockJS('${websocket_url}');

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
                if(map.type=="Follower"){
                    event_follower(map)
                }else if(map.type=="Inventory"){
                    refresh_chart();

                    event_inventory(map)
                }else{
                    event_default(map)

                }

                if(map.type=="Probationary_quotation"){
                    window.location.reload();
                }

            });


            stompClient.subscribe('${websocket_topic_status}', function (greeting) {

                var map = JSON.parse(greeting.body);
                status_decision(map);


            });

            stompClient.subscribe('${websocket_topic_welcome}', function (greeting) {


                var map = JSON.parse(greeting.body);
                if(map.type=="redirect"){
                    console.log("------reload "+map.type);
                    window.location.href = map.content;


                }

            });

        });






/*
        socket.onclose = function (evnt) {
            //console.log('websocket服务关闭了');
            reconnect(wsUrl);
        };
        socket.onerror = function (evnt) {
            //console.log('websocket服务出错了');
            reconnect(wsUrl);
        };
        socket.onopen = function (evnt) {
            //心跳检测重置
            heartCheck.reset().start();
        };
        socket.onmessage = function (evnt) {
            //如果获取到消息，心跳检测重置
            //拿到任何消息都说明当前连接是正常的
            //console.log('websocket服务获得数据了');

            //接受消息后的UI变化
            doWithMsg(evnt.data);
            heartCheck.reset().start();
        }

*/


        function reconnect(url) {
            if(lockReconnect) return;
            lockReconnect = true;
            //没连接上会一直重连，设置延迟避免请求过多
            setTimeout(function () {
                createWebSocket(url);
                lockReconnect = false;
            }, 2000);
        }



        var heartCheck = {
            timeout: 20000,
            timeoutObj: null,
            serverTimeoutObj: null,
            reset: function(){
                clearTimeout(this.timeoutObj);
                clearTimeout(this.serverTimeoutObj);
                return this;
            },
            start: function(){
                var self = this;
                this.timeoutObj = setTimeout(function(){
                    //这里发送一个心跳，后端收到后，返回一个心跳消息，
                    //onmessage拿到返回的心跳就说明连接正常
                    ws.send("HeartBeat" + new Date().format("yyyy-MM-dd hh:mm:ss"));
                    console.info("客户端发送心跳：" + new Date().format("yyyy-MM-dd hh:mm:ss"));
                    self.serverTimeoutObj = setTimeout(function(){//如果超过一定时间还没重置，说明后端主动断开了
                        ws.close();//如果onclose会执行reconnect，我们执行ws.close()就行了.如果直接执行reconnect 会触发onclose导致重连两次
                    }, self.timeout)
                }, this.timeout)
            }
        }

    }


    //心跳检测,每20s心跳一次


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




    function event_inventory(map) {

        $('#inventory-table').bootstrapTable('refresh');

        $.notify({
            title: map.companyName,
            message:map.nickname +" 调整了价格" + " "+ map.eventTime,
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

    function event_follower(map) {

        $('#follower-table').bootstrapTable('refresh');

        $.getJSON("${liveInfoUrl}",function(result){

          //  alert(result);
      /*      $.each(result, function(i, field){
                $("div").append(field + " ");
            });*/
        });

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

        $('#inventory-table').bootstrapTable('refresh');
        $('#event-table').bootstrapTable('refresh');
        refresh_chart()

        $.notify({
            title: map.distributor,
            message:map.plateNumber +" "+ map.productName,
            delay: 5000,
            allow_dismiss: true,

        },{
            type: 'info',
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

</script>
<script type="text/javascript">
    $(function () {
        $(".demo").bootstrapNews({
            newsPerPage: 4,
            navigation: true,
            autoplay: true,
            direction:'up', // up or down
            animationSpeed: 'normal',
            newsTickerInterval: 4000, //4 secs
            pauseOnHover: true,
            onStop: null,
            onPause: null,
            onReset: null,
            onPrev: null,
            onNext: null,
            onToDo: null
        });
    });
</script>

</html>