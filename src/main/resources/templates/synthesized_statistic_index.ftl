<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤 ${companyName}</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>

   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>



    <script type="text/javascript"    src="${rc.contextPath}/js/bootstrap3-typeahead.js"></script>


</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:5px ">



    <h1 class="page-header">
        <small>今日汇总</small>
    </h1>
    <div class="row">

        <div class="col-lg-6">
            <div id="statistic_toolbar" class="" >

                <select class="selectpicker "   id="periodDay" name="periodDay" placeholder="" >

                        <#list localDateTimes as localDateTime>
                            <option value="${localDateTime}">${localDateTime}</option>

                        </#list>
                </select>


                <script type="text/javascript">


                    function refresh() {
                        $('#station-table').bootstrapTable('refresh');
                    }

                    function selectedRow_() {
                        var row =  $('#station-table').bootstrapTable('getSelections');
                        return row;
                    }


                    $("#editBtn").click(function() {
                        var row = selectedRow_();

                        if (row != '') {
                            $('#addOpenAccountModal').modal();
                            //alert(JSON.stringify(row[0]));
                            $('#addOpenAccountModalForm .producerId').val(row[0].id);

                        }
                    });



                </script>


            </div>

            <table class=" table-striped" id="statistic-table" data-url="${statistic_today_report_url!''}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
                   data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
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

                   data-search="true"
                   data-show-refresh="true"
                   data-toolbar="#statistic_toolbar">
                <thead>
                <tr>

                    <th data-field="name">生产商编号</th>
                    <th data-field="total">全部</th>
                    <th data-field="checked">已核算</th>
                    <th data-field="correct">错误更正</th>
                    <th data-field="unsettled">未核算</th>



                </tr>
                </thead>
            </table>

        </div>
        <div class="col-lg-6">
            <table class=" table-striped" id="statistic-inventory-table" data-url="${statisticInventoryUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
                   data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
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

                   data-search="true"
                   data-show-refresh="true"
                   data-toolbar="#shipment_toolbar">
                <thead>
                <tr>

                    <th data-field="inventoryNo">库存编号</th>
                    <th data-field="coalType">工艺</th>
                    <th data-field="granularity">粒度</th>

                    <th data-field="totalQuantity">数量</th>
                    <th data-field="totalAmount">金额</th>
                    <th data-field="totalCount">车辆数</th>



                </tr>
                </thead>
            </table>
        </div>

    </div>


    <div class=" "  style="">


        <div class="tab-box ">

            <ul class="nav nav-tabs " id="ajaxTabs" role="tablist">
                <li class="active"><a href="#inventoryTransfer" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">出库信息<span class="badge"></span></a></li>

                <li class=""><a href="#statistic" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">分销商分类统计<span class="badge"></span></a></li>

            </ul>


        </div>

        <div  class="tab-content">


            <div class="tab-pane active" id="inventoryTransfer" style="padding-top: 5px;padding-bottom: 10px" >



                <div class="">
                    <table class=" table-striped" id="companies-table" data-url="${inventoryTransferUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-single-select="true"
                           data-search="true"
                           data-show-refresh="true"
                           data-toolbar="#toolbar" >
                        <thead>
                        <tr>

                            <th data-field="state" data-checkbox="true"></th>

                            <th data-field="createDate">时间</th>
                            <th data-field="inventoryNo">库存编号</th>
                            <th data-field="deliveryOrderNo">提煤单编号</th>

                            <th data-field="license">车牌号</th>

                        <#--                                <th data-field="outboundTime">出战时间</th>


                                                        <th data-field="loadingTime">装车时间</th>-->


                            <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>
                            <th data-field="distributorNo" >分销商编号</th>

                            <th data-field="weight" >净重</th>
                            <th data-field="status" >状态</th>

                            <th data-field="reconcileStatus" >状态</th>
                            <th data-field="description" >描述</th>

                        </tr>
                        </thead>
                    </table>

                </div>

            </div>

            <div class="tab-pane " id="statistic" style="padding-top: 5px;padding-bottom: 10px" >



                <div class="">
                    <table class=" table-striped" id="companies-table" data-url="${distributorUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-single-select="true"
                           data-search="true"
                           data-show-refresh="true"
                           data-toolbar="#toolbar" >
                        <thead>
                        <tr>



                            <th data-field="name" data-formatter="distributorInfoFormatter">开户商名称</th>
                            <th data-field="companyNo">批发商编号</th>


                            <th data-field="advancedPaymentAmount">预付账款余额</th>

                            <th data-field="totalQuantity">发货数量</th>
                            <th data-field="totalAmount">总金额</th>
                            <th data-field="totalTaxAmount">开票金额</th>

                            <th data-field="description">描述</th>


                        <#--
                                                        <th data-field="companyName | senderCompanyName"  data-formatter="companyInfoFormatter">发送者</th>
                        -->
                        </tr>
                        </thead>
                    </table>


                </div>



                <div class="col-lg-6">




                </div>





            </div>



        </div>




    </div>




</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>


<script type="text/javascript">

    $('.selectpicker').selectpicker();





    $('.selectpicker').on('change', function(){

      $('#statistic-table').bootstrapTable('refresh');
        $('#statistic-inventory-table').bootstrapTable('refresh');


     //   $('#shipment-by-operator-table').bootstrapTable('refresh');
  //      $('#shipment-by-producer-table').bootstrapTable('refresh');



    });


function deliveryNoInfoFormatter(value, row, index) {


    return  '<div class="">'  +'<a class="" href="' + row.url+ '">'+row.no+'</a></div>';

}




function queryParams(params) {
    params.page = params.pageNumber - 1;
    params.size = params.pageSize;

    var sender = $.trim($("#search_param").val());

    if (sender) {
        params.q = sender;
    }
    var periodDay = $.trim($("#periodDay").val());

    if (periodDay) {
        params.period = periodDay;
    }


    return params;
}


/*
    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }

        var deliveryReportDistributorId = $.trim($("#deliveryReportDistributorId").val());

        if (deliveryReportDistributorId) {
            params.distributorId = deliveryReportDistributorId;
        }

        return params;
    }
*/





function companyInfoFormatter(value, row, index) {



    return  "   <div class=''>"+
    "<h4><a href='"+ row.distributorUrl + "'> "+ row.distributor+"<a></h4> </div>";

}
function productInfoFormatter(value, row, index) {
    return "<span>"+ row.coalType+row.granularity+"</span>";
}

function licinseInfoFormatter(value, row, index) {
    return  "   <div class=''>"+
            "<h4>"+ row.license+"</h4> </div>";
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
       // alert(JSON.stringify(original));
        return res;
    }


</script>



</html>