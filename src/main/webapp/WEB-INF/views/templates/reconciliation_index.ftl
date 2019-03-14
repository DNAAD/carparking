<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤 库存作业</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>



</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:10px ">

    <h1 class="page-header">
        <small>对账管理</small>
    </h1>



    <div class="row "  >


        <div class="col-sm-12">

            <div class="tab-box ">

                <ul class="nav nav-tabs add-tabs" id="ajaxTabs" role="tablist">
<#--
                    <li class="active"><a href="#companies" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false"><strong>库存作业</strong> <span class="badge"></span></a></li>
-->
                    <li class="active"><a href="#transfer" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false"><strong>未入账出库信息</strong> <span class="badge"></span></a></li>

                    <li class=""><a href="#reconciliation" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false"><strong>未确认对账单</strong> <span class="badge"></span></a></li>
    <li class=""><a href="#reconciliation_confirm" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false"><strong>已确认对账单</strong> <span class="badge"></span></a></li>

                </ul>

            </div>

             <div  class="tab-content" style="padding-top: 10px;padding-bottom: 10px">


<div class="tab-pane active" id="transfer" >

    <div id="transfer_toolbar" class="" >

        <select class="selectpicker" data-max-options="2"  id="feature" name="feature" class="form-control" placeholder="开户分销商">

        <#if distributors??>
            <#list distributors as feature>
                <option value="${feature.id}" >${feature.companyNo!''}--${feature.name!''}</option>

            </#list>
        </#if>

        </select>


        <button id="createReconciliationBtn" type="button" class="btn btn-primary" >
            建立对账系统
        </button>

    <#--                     <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLineModal">
                             添加
                         </button>-->

        <script type="text/javascript">


            function refresh() {
                $('#station-table').bootstrapTable('refresh');
            }

            function selectedRow_transfer() {
                var row =  $('#transfer-table').bootstrapTable('getSelections');
                return row;
            }


            $("#createReconciliationBtn").click(function() {
                //  var row = selectedRow_transfer();
                $('#reconciliationModal').modal();
                alert(JSON.stringify(row[0]));
                $('#instanceId').val(row[0].id);

                /*                                 if (row != '') {

                                                 }*/
            });



        </script>


    </div>
    <table class=" table-striped" id="transfer-table" data-url="${transferUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
           data-toolbar="#transfer_toolbar">
        <thead>
        <tr>





            <th data-field="state" data-checkbox="true"></th>

            <th data-field="createDate">时间</th>
        <#--        <th data-field="outboundTime">出战时间</th>-->
            <th data-field="plateNumber">车牌号</th>
            <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>
            <th data-field="weight">重量</th>
            <th data-field="unitPrice">单价</th>
            <th data-field="inventoryId" >产品</th>

                            <th data-field="tareWeight">皮重</th>
                         <th data-field="grossWeight" >毛重</th>
            <th data-field="netWeight" >净重</th>
            <th data-field="unitPrice" >单价</th>


            <th data-field="status" >状态</th>
            <th data-field="reconcileStatus" >对账状态</th>



        </tr>
        </thead>
    </table>

</div>



<div class="tab-pane " id="reconciliation">

    <div id="reconciliation_toolbar" class="" >

        <select class="selectpicker" data-max-options="2"  id="feature" name="feature" class="form-control" placeholder="开户分销商">

        <#if distributors??>
            <#list distributors as feature>
                <option value="${feature.id}" >${feature.companyNo!''}--${feature.name!''}</option>

            </#list>
        </#if>

        </select>


    <#--                         <button id="createReconciliationBtn" type="button" class="btn btn-primary" >
                                 建立对账系统
                             </button>

                         &lt;#&ndash;                     <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLineModal">
                                                  添加
                                              </button>&ndash;&gt;

                             <script type="text/javascript">


                                 function refresh() {
                                     $('#station-table').bootstrapTable('refresh');
                                 }

                                 function selectedRow_transfer() {
                                     var row =  $('#transfer-table').bootstrapTable('getSelections');
                                     return row;
                                 }


                                 $("#createReconciliationBtn").click(function() {
                                     var row = selectedRow_transfer();

                                     if (row != '') {
                                         $('#reconciliationModal').modal();
                                         alert(JSON.stringify(row[0]));
                                         $('#instanceId').val(row[0].id);

                                     }
                                 });



                             </script>-->


    </div>
    <table class=" table-striped" id="transfer-table" data-url="${reconciliationUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
           data-toolbar="#reconciliation_toolbar">
        <thead>
        <tr>




            <th data-field="state" data-checkbox="true"></th>
            <th data-field="no" >编号</th>

            <th data-field="periodBeginDate">开始时间</th>
            <th data-field="periedEndDate">结束时间</th>
            <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>

        <#--    <th data-field="inventoryNo" >产品编号</th>-->
<#--            <th data-field="totalAmount" >金额</th>

            <th data-field="totalQuantity" >数量</th>-->

                                   <th data-field="tareWeight">皮重</th>
                         <th data-field="grossWeight" >毛重</th>
            <th data-field="netWeight" >净重</th>
            <th data-field="unitPrice" >单价</th>



            <th data-field="totalPrice" >总额</th>
            <th data-field="taxAmount" >开票金额</th>

            <th data-field="totalAmount" >金额</th>

            <th data-field="totalQuantity" >数量</th>

            <th data-field="status" >状态</th>

            <th data-field="no"  data-formatter="reconciliationOperationFormatter">操作</th>


        </tr>
        </thead>
    </table>

</div>

<div class="tab-pane " id="reconciliation_confirm">

    <div id="reconciliation_confirm_toolbar" class="" >

        <select class="selectpicker" data-max-options="2"  id="feature" name="feature" class="form-control" placeholder="开户分销商">

        <#if distributors??>
            <#list distributors as feature>
                <option value="${feature.id}" >${feature.no!''}--${feature.name!''}</option>

            </#list>
        </#if>

        </select>


    <#--                         <button id="createReconciliationBtn" type="button" class="btn btn-primary" >
                                 建立对账系统
                             </button>

                         &lt;#&ndash;                     <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLineModal">
                                                  添加
                                              </button>&ndash;&gt;

                             <script type="text/javascript">


                                 function refresh() {
                                     $('#station-table').bootstrapTable('refresh');
                                 }

                                 function selectedRow_transfer() {
                                     var row =  $('#transfer-table').bootstrapTable('getSelections');
                                     return row;
                                 }


                                 $("#createReconciliationBtn").click(function() {
                                     var row = selectedRow_transfer();

                                     if (row != '') {
                                         $('#reconciliationModal').modal();
                                         alert(JSON.stringify(row[0]));
                                         $('#instanceId').val(row[0].id);

                                     }
                                 });



                             </script>-->


    </div>
    <table class=" table-striped" id="transfer-table" data-url="${reconciliation_confirmedUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
           data-toolbar="#reconciliation_confirm_toolbar">
        <thead>
        <tr>




            <th data-field="state" data-checkbox="true"></th>
            <th data-field="no" >编号--</th>

            <th data-field="periodBeginDate">开始时间</th>
            <th data-field="periedEndDate">结束时间</th>
            <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>

        <#--    <th data-field="inventoryNo" >产品编号</th>-->
            <th data-field="totalAmount" >金额</th>

            <th data-field="totalQuantity" >数量</th>
            <th data-field="status" >状态</th>

            <th data-field="no"  data-formatter="reconciliationOperationFormatter">操作</th>



            <th data-field="status" >状态</th>
            <th data-field="status" >状态</th>
            <th data-field="status" >状态</th>
            <th data-field="status" >状态</th>



        </tr>
        </thead>
    </table>

</div>
             </div>


        </div>




    </div>


<div id="addLineModal" class="modal fade" role="dialog">
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

                            <form id="addLineModalForm"   novalidate="novalidate" action="{command_create_url}">
                                <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId hidden" name="companyId" value=""  >








                                <div class="form-group">
                                    <label for="username" class="control-label">名称</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="lineName" value=""  >


                                    <span class="help-block"></span>
                                </div>


                                <div class="form-group">
                                    <label for="username" class="control-label">描述</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                    <span class="help-block"></span>
                                </div>



                                <div class="form-group">
                                    <label for="username" class="control-label">位置</label>

                                    <select class="selectpicker" <#--data-max-options="2" --> id="departureStation" name="departureStation" class="form-control" placeholder="特征">
                                    <#-- <select class="form-control select2" id="userType" name="userType"  placeholder="公司类型"  multiple="multiple">-->

                                    <#if stations??>
                                        <#list stations as feature>
                                            <option value="${feature.id}" >${feature.id!''}--${feature.name!''}--${feature.description!''}</option>

                                        </#list>
                                    </#if>

                                    </select>



                                    </select>
                                    <span class="help-block"></span>
                                </div>

                                <div class="form-group">
                                    <label for="username" class="control-label">位置</label>

                                    <select class="selectpicker" <#--data-max-options="2" --> id="arrivalStation" name="arrivalStation" class="form-control" placeholder="特征">
                                    <#-- <select class="form-control select2" id="userType" name="userType"  placeholder="公司类型"  multiple="multiple">-->

                                    <#if stations??>
                                        <#list stations as feature>
                                            <option value="${feature.id}" >${feature.id!''}--${feature.name!''}--${feature.description!''}</option>

                                        </#list>
                                    </#if>

                                    </select>



                                    </select>
                                    <span class="help-block"></span>
                                </div>

                                <button id="addLineModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#addLineModalFormBtn").click(function() {
                                    alert($('#addLineModalForm').serialize());

                                    var req = $.ajax({
                                        url:  $('#addLineModalForm').attr('action'),
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
                        </div>


                    </div>
                </div>




            </div>
        </div>
    </div>
</div>

<div id="reconciliationModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">生产对账信息</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="well">

                            <form id="reconciliationModalForm"   novalidate="novalidate" action="${createTransferUrl}">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>





                                <button id="reconciliationModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#reconciliationModalFormBtn").click(function() {
                                    alert($('#reconciliationModalForm').serialize());

                                    var req = $.ajax({
                                        url:  $('#reconciliationModalForm').attr('action'),
                                        type: 'post',
                                        data:  $('#reconciliationModalForm').serialize(),
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
<div id="editLineModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">生产对账信息</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="well">

                            <form id="editLineModalForm"   novalidate="novalidate" action="${createTransferUrl}">
                                <input style="margin-bottom: 15px;" id="instanceId" type="" placeholder="Username" class="id " name="instanceId" value=""  >
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <div class="form-group">
                                    <label for="username" class="control-label">毛重</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="毛重" class="name form-control input-lg" name="grossWeight" value=""  >


                                    <span class="help-block"></span>
                                </div>


                                <div class="form-group">
                                    <label for="username" class="control-label">描述</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="description form-control" name="description" value=""  >
                                    <span class="help-block"></span>
                                </div>



                                <button id="editLineModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#editLineModalFormBtn").click(function() {
                                    alert($('#editLineModalForm').serialize());

                                    var req = $.ajax({
                                        url:  $('#editLineModalForm').attr('action'),
                                        type: 'post',
                                        data:  $('#editLineModalForm').serialize(),
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

<#include "./common/page_foot_section.ftl">

<#include "./common/hotline_section.ftl">




</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

<script>
    $(document).ready(function(){
    });
</script>
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
        //alert(JSON.stringify(sender))
        return params;
    }
    $('#supplies-table').bootstrapTable({
        onLoadSuccess: function (data) {
            $('[data-trigger="hover"]').popover();

        }
    });



    function companyInfoFormatter(value, row, index) {


        return   '<strong><a href="'+ row.distributorUrl + '">' + row.distributor +' / ' +row.distributorNo + '</a></strong>';



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

function reconciliationOperationFormatter(value, row, index) {


    return   '<strong><a href="'+ row.url + '">操作</a></strong>';



}

$(function(){

    $('.selectpicker').selectpicker();



    $('.searchBtn').bind('click', function(){
        refresh();

    });






})
</script>

</html>