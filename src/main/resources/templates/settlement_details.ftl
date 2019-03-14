<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人中心 产品管理</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">


    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

</head>
<body>

<div class="container" style="margin-bottom:50px; margin-top:0px">

<h1 class="page-header">
    <small>结算详情</small>
</h1>
<ol class="breadcrumb">
    <li class="active">资金账户</li>
    <li class="active">支付详情</li>
</ol>



<div class="row">

    <div class="col-lg-6">

        <div class="row">
            <div class="col-xs-12">

                <form id="settleForm" role="settleform" action="/usercenter/coalorder/confirmSign" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" id="deliveredShippingIds" name="shippingIds">

                    <select class="form-control selectpicker" id="principalIds" name="receiver"  <#--multiple="multiple"-->>

<#--                    <#list purchaserUsers as person>

                        <option value="${person.id}" >${person.id!''}--${person.realName!''}</option>
                    </#list>-->

                    </select>

                    <button id="reqSettle" type="button" class="btn btn-success">请求结算</button>

                </form>

                <button class="btn btn-primary pull-right hidden" style="margin-right: 5px;"><i class="fa fa-download"></i> Generate PDF</button>
            </div>

            <script type="text/javascript">


                $("#reqSettle").bind('click', function(e){

                    var req = $.ajax({
                        url: '{settlementUrl}',
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



                });



            </script>

        </div>

    </div>
    <div class="col-lg-12">





        <div class="well well-lg">



            <table class="table table-hover">
                <thead>
                <tr>

                    <th>申请时间</th>
                    <th>类型</th>
                    <th>备注</th>
                    <th>金额</th>

                </tr>
                </thead>
                <tbody>
                <tr class="infoRow">
                    <td>${capitalPayment.createDate!''}</td>
                    <td>${capitalPayment.type!''}</td>
                    <td>${capitalPayment.note!''}</td>

                    <td>${capitalPayment.amount!''} 吨</td>

                </tr>
                </tbody>
            </table>

        </div>
        <div class=style="border-bottom: black 1px dashed"></div>
</div>







<div class="col-lg-12">
    <div  id="toolbar" class="pull-right ">
        <a id="addBtn" name="addBtn" class="btn btn-primary" data-toggle="modal" data-target="#editInventoryModal">部分结算</a>


    </div>

    <div id="editInventoryModal" class="modal fade" role="dialog">
        <div id="login-overlay" class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">调整库存</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">

                            <form id="editInventoryModalForm"   novalidate="novalidate">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <input style="margin-bottom: 15px;" type="hidden" placeholder="Username" id="editInventoryModalId" name="inventoryId" value=""  >
                                <input style="margin-bottom: 15px;" type="hidden" placeholder="requestUrl" id="" name="" value=""  >

                                <div class="form-group">
                                    <label for="username" class="control-label">库存</label>
                                    <input type="text" class="form-control" id="" name="inventory" value="" required="" title="详细地址" placeholder="库存">
                                    <span class="help-block"></span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label" for="prependedtext">选择状态</label>

                                    <select id="inventoryStatus" class="selectpicker form-control" name="status" data-style="btn-primary" ">

                                    </select>

                                </div>
                                <div id="loginErrorMsg" class="alert alert-error hide">用户名或密码错误</div>


                            </form>

                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="" id="editInventoryModalBtn" ref="{editSingleInventory}" class="btn btn-success btn-block addTruckForShippingBtn" data-dismiss="modal">确定</button>

                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">

        $('#inventory-table').bootstrapTable({
            onLoadSuccess: function (data) {

                $('#inventory-table .editInventoryBtn').not('.hidden').each(function () {
                    $(this).click(function (){
                        $("#editInventoryModal").modal('show');
                        $('#editInventoryModalId').val(this.value);
                    });
                });
                /*    $('#price-category-table .priceCancelBtn').not('.hidden').each(function () {
                        $(this).click(function (){
                            $("#deletePriceCategoryModal").modal('show');
                            $('#deletePriceCategoryModalId').val(this.value);
                        });
                    });*/

            }

        });



        function groupBuyOperationFormatter(value, row, index) {
            return '<button type="button" value="' + row.id + '" class="btn btn-link editInventoryBtn">详情</button>';
        }


        $("#editInventoryModalBtn").bind('click', function(e){
            alert($('#editInventoryModalForm').serialize());

            var applicationId = ${product.id};

            alert(applicationId);

            var req = $.ajax({
                url:  $(this).attr('ref'),
                type: 'post',
                data: $('#editInventoryModalForm').serialize(),
            });
            req.done(function (data) {

                alert("json" + JSON.stringify(data));

                if (data.status) {
                    $('#pending-enter-table').bootstrapTable('refresh');

                } else {
                    alert(data.message);
                }
            });
        });

        $("#addPrincipalBtn").bind('click', function(e){
            alert($('#principalForm').serialize());

            var applicationId = ${product.id};

            alert(applicationId);

            var req = $.ajax({
                url:  $(this).attr('ref'),
                type: 'post',
                data: $('#principalForm').serialize(),
            });
            req.done(function (data) {

                alert("json" + JSON.stringify(data));

                if (data.status) {
                    $('#pending-enter-table').bootstrapTable('refresh');

                } else {
                    alert(data.message);
                }
            });
        });

    </script>


    <table class="table-striped" id="settlement-table" data-url="${paymentItemsUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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



           data-search="true"
           data-show-refresh="true"
           data-toolbar="#toolbar"
            >

        <thead>
        <tr>

            <th data-field="state" data-radio="true"></th>

            <th data-field="createDate">添加时间</th>
            <th data-field="id">编号</th>
            <th data-field="plateNumber">车牌号</th>

            <th data-field="driverPhone">司机手机号</th>

            <th data-field="amount">净重</th>
            <th data-field="unitPrice">单价</th>

            <th data-field="totalAmount">合计</th>
            <th data-field="status">状态</th>



            <th data-field="id" data-formatter="operationFormatter">操作</th>

        </tr>
        </thead>
    </table>


</div>




<script type="text/javascript" src="${rc.contextPath}/js/page_multi.js"></script>

<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>



<script type="text/javascript">

    $('.selectpicker').selectpicker();

    /*    $('.selectpicker').on('change', function(){
            var selected = $(this).find("option:selected").val();
            refresh();
        });*/
</script>
<script type="text/javascript">

    function handleResponse(original) {
        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;
        return res;
    }
    var processingOrderSettings = {
        size: 5,
        url: '/usercenter/coalorder/orderShippingList',
        placeHoler: 1,

        rowTemplateDiv: '#rowTemplate1',
        pageDataDiv: '#pageData1',
        paginationDiv: '#pagination1',
        params: function () {
            return {
                orderId: ''
            }
        },
        rowData: function (row) {
            row.btns = '';
            row.info = '';
            /*                if (row.shippingStatus == 'shipping') {
                                #if Session['SESSION_CONTEXT'].buyerFlag>
                                    row.btns = '<button type="button" class="btn signShipment btn-link">签收</button>';
                                /#if>
                            } else if (row.shippingStatus == 'received') {
                                row.info = row.signDate;
                                #if Session['SESSION_CONTEXT'].sellerFlag>
                                    row.btns = '<button type="button" class="btn reqSettle btn-link">结算</button>';
                                /#if>
                            } else if (row.shippingStatus == 'settling') {
                                row.info = '卖家请求结算' + row.settlePrice + ' 元';
                                #if Session['SESSION_CONTEXT'].buyerFlag>
                                    row.btns = '<button type="button" class="btn confirmSettle btn-link">确认</button>';
                                /#if>
                            } else if (row.shippingStatus == 'settled') {

                            } else if (row.shippingStatus == 'unsettled') {
                                row.info = '-' + row.debt + ' 元';
                            } else if (row.shippingStatus == 'cleared') {

                            }*/
            return row;

        },
        afterRenderTable: function () {
            addHover();

        }
    };

    var addTruck = function(){
        $('#pageData1 .addTruck').each(function () {
            $(this).click(function () {
                var coalOrderId = $(this).parent().parent().find(".id").html();
                showTruckModal(coalOrderId);
            });
        });
    };
    var addHover = function () {
        $(".rowContent").hover(function () {
            $(this).css("background-color", "#f5f5f5");
        }, function () {
            $(this).css("background-color", "#ffffff");
        });
    };
    var page1 = new page();

    page1.initPage(processingOrderSettings);
    $(".addTruck").on('click', function(){
        showTruckModal(1);
    });



    var trucksForShipping = {
        size: 5,
        url: '/usercenter/coalorder/trucksForShippingList',
        placeHoler: 1,

        rowTemplateDiv: '#rowTemplate3',
        pageDataDiv: '#pageData3',
        paginationDiv: '#pagination3',
        params: function () {
            return {
                orderId: ''
            }
        },
        rowData: function (row) {
            row.btns = '';
            row.info = '';

            return row;

        },

    };






    $(".immediatePay").on('click', function(){
        window.location.href = '/usercenter/cashier?paymentId=' + ${capitalPayment.id!''} ;
    });



</script>


    </div>





<script type="text/javascript" src="${rc.contextPath}/js/page_multi.js"></script>

<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>



<script type="text/javascript">

    $('.selectpicker').selectpicker();

/*    $('.selectpicker').on('change', function(){
        var selected = $(this).find("option:selected").val();
        refresh();
    });*/
</script>
<script type="text/javascript">

    function handleResponse(original) {
        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;
        return res;
    }
        var processingOrderSettings = {
            size: 5,
            url: '/usercenter/coalorder/orderShippingList',
            placeHoler: 1,

            rowTemplateDiv: '#rowTemplate1',
            pageDataDiv: '#pageData1',
            paginationDiv: '#pagination1',
            params: function () {
                return {
                    orderId: ''
                }
            },
            rowData: function (row) {
                row.btns = '';
                row.info = '';
/*                if (row.shippingStatus == 'shipping') {
                    #if Session['SESSION_CONTEXT'].buyerFlag>
                        row.btns = '<button type="button" class="btn signShipment btn-link">签收</button>';
                    /#if>
                } else if (row.shippingStatus == 'received') {
                    row.info = row.signDate;
                    #if Session['SESSION_CONTEXT'].sellerFlag>
                        row.btns = '<button type="button" class="btn reqSettle btn-link">结算</button>';
                    /#if>
                } else if (row.shippingStatus == 'settling') {
                    row.info = '卖家请求结算' + row.settlePrice + ' 元';
                    #if Session['SESSION_CONTEXT'].buyerFlag>
                        row.btns = '<button type="button" class="btn confirmSettle btn-link">确认</button>';
                    /#if>
                } else if (row.shippingStatus == 'settled') {

                } else if (row.shippingStatus == 'unsettled') {
                    row.info = '-' + row.debt + ' 元';
                } else if (row.shippingStatus == 'cleared') {

                }*/
                return row;

            },
            afterRenderTable: function () {
                addHover();

            }
        };

        var addTruck = function(){
            $('#pageData1 .addTruck').each(function () {
                $(this).click(function () {
                    var coalOrderId = $(this).parent().parent().find(".id").html();
                    showTruckModal(coalOrderId);
                });
            });
        };
        var addHover = function () {
            $(".rowContent").hover(function () {
                $(this).css("background-color", "#f5f5f5");
            }, function () {
                $(this).css("background-color", "#ffffff");
            });
        };
        var page1 = new page();

        page1.initPage(processingOrderSettings);
        $(".addTruck").on('click', function(){
            showTruckModal(1);
        });



        var trucksForShipping = {
            size: 5,
            url: '/usercenter/coalorder/trucksForShippingList',
            placeHoler: 1,

            rowTemplateDiv: '#rowTemplate3',
            pageDataDiv: '#pageData3',
            paginationDiv: '#pagination3',
            params: function () {
                return {
                    orderId: ''
                }
            },
            rowData: function (row) {
                row.btns = '';
                row.info = '';

                return row;

            },

        };






        $(".immediatePay").on('click', function(){
            window.location.href = '/usercenter/cashier?paymentId=' + ${capitalPayment.id!''} ;
        });



</script>
</body>
</html>