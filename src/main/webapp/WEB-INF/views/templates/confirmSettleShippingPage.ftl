
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人中心 订单管理</title>
    <link href="${rc.contextPath}/components/bootstrap_datepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>


    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>


    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_datepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_datepicker/js/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/jquery/validate/jquery.validate.min.js"></script>

    <script type="text/javascript" src="${rc.contextPath}/components/jquery/validate/messages_zh.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/highcharts.js"></script>


</head>
<style type="text/css">
    .content-header>.breadcrumb {
        float: right;
        background: transparent;
        margin-top: 0;
        margin-bottom: 0;
        font-size: 12px;
        padding: 7px 5px;
        position: absolute;
        top: 15px;
        right: 10px;
        border-radius: 2px;
    }

</style>
<body>
<div class="container" style="margin-bottom:80px; margin-top:0px ">

<#--
<#include "../common/page_header_section.ftl">
-->


<h1 class="page-header">
    <small>确认结算运单</small>
</h1>
<ol class="breadcrumb">
    <li class="active">交易管理</li>
    <li class="active">订单详情</li>
    <li class="active">确认结算运单</li>
</ol>
<input type="hidden" id="orderId" name="id" value="${coalOrder.id!''}">
<#--<section class="content-header">
    <h1>
        Invoice
        <small>#007612</small>
    </h1>
    <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
        <li><a href="#">Examples</a></li>
        <li class="active">Invoice</li>
    </ol>
</section>-->
<div class="row">
    <div class="col-xs-12 hidden">
        <h2 class="page-header">
            <i class="fa fa-globe"></i> 结算操作
            <small class="pull-right">Date: 2/10/2014</small>
        </h2>
    </div><!-- /.col -->
</div>


<div class="col-lg-6">


    <div id="toolbar" class="btn-group pull-right">


        <button id="settleBtn" type="button" class="btn btn-default">
            <i class="fa fa-search">结算总额</i>
        </button>
        <script type="text/javascript">


            function refresh_logistics() {
                $('#product-table').bootstrapTable('refresh');
            }

            function selectedRow() {
                var row =  $('#settlement-table').bootstrapTable('getSelections');
                return row;
            }


            $("#settleBtn").click(function() {

                var row = selectedRow_logistics();

                if (row != '') {
                  //  $('#addManagerModal').modal();
               //     alert(JSON.stringify(row[0]));

                    var ids = $.map(row, function (item) {
                    //    alert(item.tareWeight+1);
                        return item.netWeight*item.unitPrice;
                    });
                    var total=0;
                 ids.forEach(function(e){
                        total =total +e;
                    })  ;


                    document.getElementById("total").innerHTML = total;
                    document.getElementById("amount").innerHTML = total;



                }else{
                    document.getElementById("total").innerHTML = 0;

                }
            });



        </script>
    </div>
    <table class="table-striped" id="settlement-table" data-url="${shipmentShippingUri}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
               data-content-type="orderJsonView/x-www-form-urlencoded; charset=UTF-8"
               data-query-params-type="unlimit"
               data-query-params="queryParams"
               data-onDblClickRow="onDblClickRow"


               data-response-handler="handleResponse"
               data-pagination="true"
               data-side-pagination="server"
               data-page-number="1"
               data-page-list="[10]"
               data-page-size="10"
               data-click-to-select="true"
             <#--  data-single-select="true"-->
               data-toolbar="#toolbar"
                >

        <thead>
        <tr>


            <th data-field="state" data-checkbox="true"></th>
            <th data-field="createDate">发站时间</th>
        <#--           <th data-field="outboundTime" >出场时间</th>-->
        <#--     <th data-field="shippingNo" >运单编号</th>-->
        <#--                <th data-field="deliveryUnit | deliveryAddress " data-formatter="yardInfoFormatter">对场地</th>-->
            <th data-field="plateNumber">车牌号</th>
            <th data-field="plateNumber" data-formatter="transferProductInfoFormatter">产品</th>
            <th data-field="" data-formatter="companyInfoFormatter">公司</th>



        <#--  <th data-field="truckPoc | truckPhone" data-formatter="shipmentContactInfoFormatter">联系人</th>-->
            <th data-field="netWeight">净重</th>
            <th data-field="unitPrice">价格</th>

            <th data-field="status">状态</th>
            <th data-field="id" data-formatter="shippingOperationFormatter">操作</th>

<#--            <th data-field="state" data-checkbox="true">选择</th>

            <th data-field="boundTime">进场时间</th>
            <th data-field="outboundTime" >出场时间</th>
            <th data-field="outboundTime" >签收日期</th>

            <th data-field="createDate">运单编号</th>
            <th data-field="senderName">矿发净重</th>
            <th data-field="receiverName">签收净重</th>

            <th data-field="message">价格</th>
            <th data-field="status">小计</th>-->
        </tr>
        </thead>
        <tbody>
<#--        <#list shippings as shipping>
        <tr id="tr-id-5" class="tr-class-5">
            <td><input type="checkbox" name="shippingId" value="${shipping.id!''}"/></td>

            <td data-field="">${shipping.boundTime!''}</td>
            <td data-field="" >${shipping.outboundTime!''}</td>
            <td data-field="" >${shipping.signDate!''}</td>

            <td>${shipping.shippingNo!''}</td>
            <td>${shipping.shippingWeight!''}</td>
            <td>${shipping.signWeight!''}</td>
            <td>${shipping.settlePrice!''}</td>
            <td>${shipping.settlePrice!''} * ${shipping.signWeight!''}</td>


        </tr>
        </#list>-->
        </tbody>
    </table>

</div>
<div class="col-lg-6">
    <p class="text-danger hidden" > 有个选中的  shipping list ， 然后 是一个合计的总金额 ，然后 是一个 请求结算的额 按钮 </p>
    <div class="row">
        <!-- accepted payments column -->
        <div class="col-xs-6 hidden">
            <p class="lead">Payment Methods:</p>
            <img src="../../dist/img/credit/visa.png" alt="Visa">
            <img src="../../dist/img/credit/mastercard.png" alt="Mastercard">
            <img src="../../dist/img/credit/american-express.png" alt="American Express">
            <img src="../../dist/img/credit/paypal2.png" alt="Paypal">
            <p class="text-muted well well-sm no-shadow" style="margin-top: 10px;">
                Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles, weebly ning heekya handango imeem plugg dopplr jibjab, movity jajah plickers sifteo edmodo ifttt zimbra.
            </p>
        </div><!-- /.col -->
        <div class="col-xs-12">
            <p class="lead">Amount Due 2/22/2014</p>
            <div class="table-responsive">
                <table class="table">
                    <tbody><tr>
                        <th style="width:50%" >金额:</th>
                        <td id="amount"></td>
                    </tr>
                    <tr class="hidden">
                        <th>Tax (9.3%)</th>
                        <td>$10.34</td>
                    </tr>
                    <tr class="hidden">
                        <th>Shipping:</th>
                        <td>$5.80</td>
                    </tr>
                    <tr>
                        <th >合计:</th>
                        <td id="total"></td>
                    </tr>
                    </tbody></table>
            </div>
        </div><!-- /.col -->
    </div>
    <div class="row no-print">
        <div class="col-xs-12">

            <form id="settleForm" role="settleform" action="/usercenter/coalorder/confirmSign" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <input type="hidden" id="deliveredShippingIds" name="shippingIds">

                <button id="reqSettle" type="button" class="btn btn-success">请求结算</button>

            </form>
            <a href="invoice-print.html" target="_blank" class="btn btn-default hidden"><i class="fa fa-print"></i> 打印</a>
            <button class="btn btn-primary pull-right hidden" style="margin-right: 5px;"><i class="fa fa-download"></i> Generate PDF</button>
        </div>

        <script type="text/javascript">

/*
            $('#settlement-table').on('click-row.bs.table', function (e, row, element)
            {
                $('.success').removeClass('success');//去除之前选中的行的，选中样式
                $(element).addClass('success');//添加当前选中的 success样式用于区别

                var row = selectedRow_logistics();

                alert(JSON.stringify(row));

            });

*/

            function selectedRow_settlement() {
                var row =  $('#settlement-table').bootstrapTable('getSelections');
                return row;
            }



            $("#reqSettle").bind('click', function(e){

                //var trucks = $(":checkbox[name='shippingId']:checked");
                var truckIds = new Array();

                var row = selectedRow_settlement();

                if (row != '') {
                    //  $('#addManagerModal').modal();

                    truckIds = $.map(row, function (item) {
                        //    alert(item.tareWeight+1);
                        return item.id;
                    });

                    $('#deliveredShippingIds').val(truckIds)
                    alert(JSON.stringify( $("#settleForm").serialize()));

                    alert("- - - "+truckIds.toString());

                    var req = $.ajax({
                        url: '${settleUrl}',
                        type: 'post',
                        data: $("#settleForm").serialize()
                    });
                    req.done(function (data) {
                        if (data.status) {

                        //    window.location.href = data.link;

                        } else {
                            alert(data.message);

                        }
                    });





                }



            });



        </script>

    </div>

</div>


<div class="row">
    <div class="col-lg-12">


        <a class="btn btn-primary "   href="{confirmSettleUrl}"> <i class="fa fa-certificate"></i> <span>添加提货车辆</span> </a>
        <div class="" style="margin-top: 10px">
            <table class="table-striped" id="settlement-table" data-url="${settlementUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                <#--            <th data-field="state" data-checkbox="true"></th>
                            <th data-field="createDate">发站时间</th>
                        &lt;#&ndash;           <th data-field="outboundTime" >出场时间</th>&ndash;&gt;
                        &lt;#&ndash;     <th data-field="shippingNo" >运单编号</th>&ndash;&gt;
                        &lt;#&ndash;                <th data-field="deliveryUnit | deliveryAddress " data-formatter="yardInfoFormatter">对场地</th>&ndash;&gt;
                            <th data-field="plateNumber">车牌号</th>
                            <th data-field="plateNumber" data-formatter="transferProductInfoFormatter">产品</th>
                            <th data-field="" data-formatter="companyInfoFormatter">公司</th>



                        &lt;#&ndash;  <th data-field="truckPoc | truckPhone" data-formatter="shipmentContactInfoFormatter">联系人</th>&ndash;&gt;
                            <th data-field="netWeight">净重</th>
                            <th data-field="unitPrice">价格</th>

                            <th data-field="status">状态</th>
                            <th data-field="id" data-formatter="shippingOperationFormatter">操作</th>-->

                    <th data-field="createDate">创建时间</th>
                    <th data-field="consumeTitle">名称</th>

                    <th data-field="no">交易号</th>
                    <th data-field="reciprocalName">对方</th>
                    <th data-field="amount">金额</th>



                    <th data-field="status">状态</th>
                <#--    <th data-field="" data-formatter="paymentOperationFormatter">操作</th>-->
                </tr>
                </thead>
            </table>


        </div>

    </div>
</div>

</div>



<#--table header row-->


<script type="text/javascript" src="${rc.contextPath}/js/page_multi.js"></script>
<script type="text/javascript">
    function handleResponse(original) {
        //alert(JSON.stringify(original));
        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;
        return res;
    }

    function transferProductInfoFormatter(value, row, index) {

        return ' <div class="">  '+
                '<div> <a href="'+row.productUrl+'">' + row.coalType +' '+ row.granularity+'</a></div> </div>';

    }

    function companyInfoFormatter(value, row, index) {

        return ' <div class="">' +
                '<div>  <a href="'+row.companyUrl+'">' +row.companyName +'</a></div> </div>';

    }
/*
    $('#settlement-table').on('click-row.bs.table', function (e, row, element)
    {
        $('.success').removeClass('success');//去除之前选中的行的，选中样式
        $(element).addClass('success');//添加当前选中的 success样式用于区别

        var row = selectedRow_logistics();

        alert(JSON.stringify(row));

    });
*/


    function selectedRow_logistics() {
        var row =  $('#settlement-table').bootstrapTable('getSelections');
        return row;
    }

    function getSelectedRow()
    {
        var index = $('#settlement-table').find('tr.success').data('index');//获得选中的行
        return $('#settlement-table').bootstrapTable('getData')[index];//返回选中行所有数据
    }

    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;
        var status = $.trim($("#shipment-table-selectpicker").val());
        var createDateS = $.trim($("#createDateS").val());
        var createDateE = $.trim($("#createDateE").val());

        var sender = $.trim($("#sender").val());

        if (status) {
            params.status = "shipping";
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
        // alert(JSON.stringify(params));
        return params;
    }




</script>


</body>
</html>