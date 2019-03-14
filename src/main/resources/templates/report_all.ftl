<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="refresh"content="60;url=${url}">
    <title>${rc.contextPath}</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>

    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/variwide.js"></script>
    <style type="text/css">


        .target-fix {
            padding-top: 45px;
            margin-top: -10px;
            display: block;
            height: 0;
            overflow: hidden;
        }

        .first {
            width: 10%;
        }

        .second {
            width: 30%;
        }
        .third {
            width: 40%;
        }

        .forth {
            width: 10%;
        }

        .img-comment-list  > div {
            display:table-cell;
        }

        .img-comment-list img {
            border-radius:50%;
            width: 80px;
            height: 80px;
            margin-right: 10px;
            margin-top: 20px;
        }

        .img-comment-list p {
            margin: 0;
        }

        .img-comment-list span {
            font-size: .8em;
            color: #aaa;
        }

        .xs-first {
            width: 25%;
        }

        .xs-second {
            width: 25%;
        }
    </style>


</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:5px ;width: 1570px;">

<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button " class="navbar-toggle btn-primary" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

        <a class="navbar-brand hidden" href="#"><img class="" src="${rc.contextPath}/logo_header.png" style="width:100px; ;"></a>

        <a class="navbar-brand " href="${synthesizedUrl}"> <strong class="h3">${companyName}</strong> </a>
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

        <div class="col-lg-8">
            <div class="" style="">
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
            </div>
            <table class=" table-striped" id="station-table" data-url="${inventoryUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                    <col style="width:5%"/>
                    <col style="width:10%"/>
                    <col style="width:20%"/>
                    <col style="width:5%"/>
                    <col style="width:20%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                    <col style="width:5%"/>
                </colgroup>
                <thead>
                <tr>



                    <th data-field="no">编号</th>

                    <th data-field="coalType">名称</th>
                    <th data-field="granularity">类型</th>

                    <th data-field="inventory">当前库存</th>

                    <th data-field="quote">当前价格</th>
                    <th data-field="quantityOnHand">热量</th>
                    <th data-field="quantityOnHand">硫</th>
                    <th data-field="quantityOnHand">灰</th>
                    <th data-field="quantityOnHand">水</th>


                </tr>
                </thead>
            </table>
            <table class=" table-striped" id="location-table" data-url="${storageUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                    <th data-field="name">名称</th>
                    <th data-field="no">编号</th>

                    <th data-field="product">产品</th>

                    <th data-field="status">状态</th>
                    <th data-field="status" data-formatter="reportOperationFormatter">操作</th>
                </tr>
                </thead>
            </table>
            <div id="section6" class="" style="margin-bottom: 40px;margin-top: 20px;">
                <img class=" img-responsive hidden " src="/qrcode (2).png" alt="User profile picture">

            </div>
        </div>

        <div class="col-lg-4">
            <div class="col-lg-12">
                <div><marquee behavior="scroll" direction="left" scrollamount="5"><label class="text-danger">实时关注${companyName}</label></marquee></div>

                <script type="text/javascript" src="${rc.contextPath}/js/qrcode.js"></script>
            <#if permanentQrcode??>
                <input id="text" type="hidden" value="${permanentQrcode.content!'---'}" style="width:80%" />
            <#else>
                <input id="text" type="hidden" value="123" style="width:80%" />
            </#if>

                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="qrcode_div"/>
                </svg>
                <script type="text/javascript">
                    var qrcode = new QRCode(document.getElementById("qrcode_div"), {
                        width : 100,
                        height : 100,
                        useSVG: true
                    });

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
            <div id="section6" class="" style="margin-bottom: 40px;margin-top: 20px;">
                <img class=" img-responsive hidden " src="/qrcode (2).png" alt="User profile picture">

            </div>
            <table class=" table-striped" id="location-table" data-url="${eventUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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

                    <th data-field="note">内容</th>


                    <th data-field="createDate">时间</th>

                </tr>
                </thead>
            </table>


        </div>

</div>




<div id="addStationModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">添加区域</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="well">

                            <form id="addStationModalForm"   novalidate="novalidate" action="${command_create_url}">
                                <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId hidden" name="companyId" value=""  >


                                <div class="form-group">
                                    <label for="username" class="control-label">线路</label>

                                    <select class="selectpicker" <#--data-max-options="2" --> id="locationId" name="lineId" class="form-control" placeholder="特征">
                                    <#-- <select class="form-control select2" id="userType" name="userType"  placeholder="公司类型"  multiple="multiple">-->

                                    <#if lines??>
                                        <#list lines as feature>
                                            <option value="${feature.id}" >${feature.id!''}--${feature.name!''}--${feature.description!''}</option>

                                        </#list>
                                    </#if>

                                    </select>

                                    <span class="help-block"></span>
                                </div>



                                <div class="form-group">
                                    <label for="username" class="control-label">自备</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="flatWagonPrice" value=""  >
                                    <span class="help-block"></span>
                                </div>
                                <div class="form-group">
                                    <label for="username" class="control-label">敞口</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="openTopWagonPrice" value=""  >
                                    <span class="help-block"></span>
                                </div>


                                <div class="form-group">
                                    <label for="username" class="control-label">描述</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                    <span class="help-block"></span>
                                </div>




                                <button id="addStationModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#addStationModalFormBtn").click(function() {
                                    alert($('#addStationModalForm').serialize());

                                    var req = $.ajax({
                                        url:  $('#addStationModalForm').attr('action'),
                                        type: 'post',
                                        data:  $('#addStationModalForm').serialize(),
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
                        </div>


                    </div>
                </div>




            </div>
        </div>
    </div>
</div>

<div id="editStationModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">编辑价格</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">


                            <form id="editStationModalForm"   novalidate="novalidate" action="${command_edit_url}">
                                <input style="margin-bottom: 15px;" type="" placeholder="Username" class="id hidden inventoryId" name="id" value=""  >


                                <div class="form-group">
                                    <label for="username" class="control-label">价格</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="postalCode form-control" name="quotation" value=""  >
                                    <span class="help-block"></span>
                                </div>
                                <div class="form-group">
                                    <label for="username" class="control-label">库存</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="postalCode form-control" name="inventory" value=""  >
                                    <span class="help-block"></span>
                                </div>



                                <button id="editStationModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#editStationModalFormBtn").click(function() {
                                    alert($('#editStationModalForm').serialize());

                                    var req = $.ajax({
                                        url:  $('#editStationModalForm').attr('action'),
                                        type: 'put',
                                        data:  $('#editStationModalForm').serialize(),
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
                        </div>


                </div>




            </div>
        </div>
    </div>
</div>


<#include "./common/page_foot_section.ftl">



</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

<script>
    $(document).ready(function(){
    });
</script>
<script type="text/javascript">
function reportOperationFormatter(value, row, index) {
    return '<div class=""><a class="btn btn-link detailBtn " href=" '+row.reportUrl+'">报告查看</a></div>';
}

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



    function ompanyInfoFormatter(value, row, index) {


        return  ' <div class="img-comment-list">  <div  class="comment-img"  > <img class="" src="'+row.companyLogoUrl+'"/> </div>'+
                ' <div class="comment-text">'+
                '<strong><a href="'+ row.companyUrl + '">' + row.companyName  + '</a></strong>'+
                '<p class="">@'+ row.inventoryCounty + '</p> '+
                '</div>'+
                ' </div>';



    }

    function companyInfoFormatter(value, row, index) {



        var inventories = "";
        $.each(row.inventories, function (n, value) {
            //  alert(n + ' ' + value);
            var trs = "";
            trs += "<a href='"+value.storageUrl+"'>" + value.storageName + "</a> 存 " + value.quantityOnHand + " 吨 ";
            inventories += trs;
        });

        var indicators = "";
        $.each(row.indicators, function (n, value) {
            //  alert(n + ' ' + value);
            var trs = "";
            trs += "" + value.symbol + " " + value.value + "  "+value.unit;
            indicators += trs;
        });

        return "    <div class='col-sm-3 no-padding photobox'>"+
        '                <div class=" carousel-row">'+
                ' <div class=" slide-row">'+
                ' <div id="carousel-1" class="carousel slide slide-carousel" data-ride="carousel">'+
                '  <!-- Indicators -->'+
        '  <ol class="carousel-indicators">'+
        '  <li data-target="#carousel-1" data-slide-to="0" class="active"></li>'+
        '  <li data-target="#carousel-1" data-slide-to="1"></li>'+
        '  <li data-target="#carousel-1" data-slide-to="2"></li>'+
        '  </ol>'+

        '      <!-- Wrapper for slides -->'+
        '   <div class="carousel-inner">'+
        '          <div class="item active">'+
        '           <img src="'+row.companyLogoUrl+'" alt="Image">'+
        '             </div>'+

        '  </div>'+
        '   </div>'+
        '    <div class="slide-content">'+
        '          <h4><a href=" '+ row.companyUrl + '">'+ row.companyName+'<a></h4>'+

        '  </div>'+
        '  </div>'+

        '    </div>'+
        '    </div>'+

        '    </div>'+

            //row.companyDesc+


    '<div class="col-sm-7 add-desc-box">'+
               ' <div class="add-details">'+
               ' <h4 class="add-title"> <a href="'+row.url+'"> '+ row.granularity+' </a> <small>'+  row.companyAddress+'</small> </h4>'+
    ' <span class="info-row"> <span class="add-type business-ads tooltipHere" data-toggle="tooltip" data-placement="right" title="Business Ads"> </span> <span class="date"><i class=" icon-clock"> </i> '+row.updateTime+' </span>  <span class="category"> </span> <span class="item-location"><i class="fa fa-map-marker"> '+   row.companyAddress+'</i>  </span> </span> </div>'+
       '<hr>'+
        inventories+'<br>'+indicators+'<br>'+


       ' </div>'+
        '  <div class="slide-footer hidden">'+
        '        <span class="pull-right buttons">'+
        '         <button class="btn btn-sm btn-default"><i class="fa fa-fw fa-eye"></i> Show</button>'+
        '    <button class="btn btn-sm btn-primary"><i class="fa fa-fw fa-shopping-cart"></i> Buy</button>'+
        '    </span>'+
        '    </div>';

    }


    function supplyCompanyInfoFormatter(value, row, index) {


        var warehouseInfo = ""

        warehouseInfo = "堆场名称：" + row.warehouseStatistic.name +"\n" +
                "当前正在装车： " +row.warehouseStatistic.countShipmentsLoading + "\n" +
                "当前等待装车：" + row.warehouseStatistic.countShipmentsCreated+ "\n" ;



        return  '                <div class=" carousel-row" data-trigger="hover" title="'+row.companyDesc+ '" data-content="'+ warehouseInfo+'" >'+
                ' <div class=" slide-row">'+
                ' <div id="carousel-1" class="carousel slide slide-carousel" data-ride="carousel">'+
                '  <!-- Indicators -->'+
        '      <!-- Wrapper for slides -->'+

        '          <div class="item active">'+
        '           <img src="logo.png" alt="Image">'+
        '          </div>'+

        '   </div>'+
        '    <div class="slide-content">'+
        '          <h4><a href=" '+ row.companyUrl + '"> '+ row.companyName+'<a></h4>'+
        '    <p>'+

        '    </p>'+
        '  </div>'+

        '    </div>';

    }


    function supplyInfoFormatter(value, row, index) {




        var List = row.qualityItems;
        var personString = "";
        for(var student in List){ //第二层循环取list中的对象
            personString += "<strong>"+List[student].qualityItemName +" "+List[student].exp+ " "+List[student].value+"</strong> |";
        }


        return  " <h3><a class='' href='"+row.url+"'>"+row.coalCategory+" - "+row.granularity+"- <strong>￥"+row.price+"</strong></a></h3>" +
         "       <p class='muted'>地址：<strong>"+row.deliveryPlace+"</strong></p>"+
        "<p class='muted'>发布时间："+row.createdDateString+"<a href='#' class='underline'>Cars, Vans &amp; Motorbikes</a></p>"+
        "<p>"+row.qualityContent+"</p>"+

                "  <p class='ad-description'>"+
                personString +

                " </p>";

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
        //   var socket = new SockJS('${websocket_url}');
        var socket = new SockJS('/ws');


        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);

            stompClient.subscribe('${websocket_topic}', function (greeting) {
                //    stompClient.subscribe('/topic/COALPIT_DELIVERY_workbench/1', function (greeting) {
                $('#delivery-table').bootstrapTable('refresh');

                console.log("------"+JSON.parse(greeting.body))
                console.log("------"+JSON.stringify(greeting.body));
                //  alert(JSON.stringify(greeting.body));
                var map = JSON.parse(greeting.body);
                $.notify({
                    title: map.message,
                    message:map.message,
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

</html>