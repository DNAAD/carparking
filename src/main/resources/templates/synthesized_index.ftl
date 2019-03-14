
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
   <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>榆林煤 ${companyName!''}</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
  <#--  <link href="${rc.contextPath}/css/lightbox/ekko-lightbox.css" rel="stylesheet">-->
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
<#--    <script src="${rc.contextPath}/js/lightbox/ekko-lightbox.min.js" type="text/javascript"></script>-->

   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>



    <script type="text/javascript"    src="${rc.contextPath}/js/bootstrap3-typeahead.js"></script>



</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:5px ">

<#include "./common/front_page_header_section.ftl">
    <a class="btn btn-default pull-right" href="${selectStorageUrl}"><i class="fa fa-fw -square -circle fa-plus-square"></i> 选择堆场</a>

    <#if preference?? && preference.storageSpaceNo??>
        <a href="${preference.reportUrl}">${preference.storageSpaceName} / ${preference.storageSpaceNo}</a>

    </#if>

<div class="row">
    <div id="" class="col-lg-12"   style="margin-bottom:10px">

        <#if distributors??>
            <#list distributors as feature>
                <option value="${feature.id}" >${feature.id!''}--${feature.displayName!''}</option>

            </#list>
        </#if>

        </select>




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
                    $('#editLineModal').modal();
                    alert(JSON.stringify(row[0]));
                    $('#instanceId').val(row[0].id);

                }
            });



        </script>


    </div>
</div>



    <div class="row "  style="">



        <div class="col-sm-12">

            <div class="tab-box ">

                <ul class="nav nav-tabs add-tabs" id="ajaxTabs" role="tablist">

                                        <#if faceRemoteControl.intelligentIdentification_tab>

                                            <li class="active"><a href="#companies" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">智能识别<span class="badge"></span></a></li>
                                                   <li class=""><a href="#delivery-order" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">预提煤单（有效）<span class="badge"></span></a></li>

                                        <#else>
                                                   <li class="active"><a href="#delivery-order" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">预提煤单（有效）<span class="badge"></span></a></li>

                                        </#if>

                    <li class=""><a href="#instanceTransport" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">堆场正在装货<span class="badge"></span></a></li>
                    <li class=""><a href="#instanceTransport_loaded" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">出库<span class="badge"></span></a></li>


                </ul>

            </div>

             <div  class="tab-content">

                     <#if faceRemoteControl.intelligentIdentification_tab>


                 <div class="tab-pane active" id="companies">
<#--

                 <img style="-webkit-user-select: none;" src="http://192.168.100.13:8081/" width="296" height="242">

                 <object type='application/x-vlc-plugin' pluginspage="http://www.videolan.org/" id='vlc' events='false' width="720" height="410">
                     <param name='mrl' value='rtsp://localhost:8554/' />
                     <param name='volume' value='50' />
                     <param name='autoplay' value='true' />
                     <param name='loop' value='false' />
                     <param name='fullscreen' value='false' />
                     <param name='controls' value='false' />
                 </object>
-->

                 <div class="row col-lg-12" style="padding-top: 10px;padding-bottom: 10px">

                         <div class="panel panel-default"  >
                             <div class="panel-heading">
                                 <h4>进场车辆</h4>

                             </div>
                             <div class="panel-body">
                                 <div id="toolbar" class="hidden" >

                                     <button id="viewCapacityBtn" type="button" class="btn btn-success">
                                         <i class="">查看详情</i>
                                     </button>
                                     <button id="editBtn" type="button" class="btn btn-primary" >
                                         修改
                                     </button>

                                     <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLocationModal">
                                         添加
                                     </button>

                                     <script type="text/javascript">


                                         function refresh() {
                                             $('#location-table').bootstrapTable('refresh');
                                         }

                                         function selectedRow_() {
                                             var row =  $('#location-table').bootstrapTable('getSelections');
                                             return row;
                                         }


                                         $("#editBtn").click(function() {
                                             var row = selectedRow_();

                                             if (row != '') {
                                                 $('#editLocationModal').modal();
                                                 alert(JSON.stringify(row[0]));
                                                 $('#editLocationModal .id').val(row[0].id);

                                             }
                                         });



                                     </script>

                                 </div>



                             </div>

                         </div>

                     <table class=" table-striped" id="location-table" data-url="${entranceIntelligentUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                             <th data-field="state" data-radio="true"></th>
                             <th data-field="createDate">时间</th>
                             <th data-field="modifyDate">时间</th>

                             <th data-field="license">车牌</th>

                             <th data-field="idNumber">身份证号</th>
                             <th data-field="distributor">提货单位</th>
                             <th data-field="product">产品</th>

                             <th data-field="message">说明</th>

                             <th data-field="status">状态</th>
                             <th data-field="status" data-formatter="operationInfoFormatter">操作</th>

                         </tr>
                         </thead>
                     </table>


<hr>

                         <div class="panel panel-default"  >
                             <div class="panel-heading">
                                 <h4>出场车辆</h4>
                             </div>
                             <div class="panel-body">

                                 <div id="toolbar" class="hidden" >

                                     <button id="viewCapacityBtn" type="button" class="btn btn-success">
                                         <i class="">查看详情</i>
                                     </button>
                                     <button id="editBtn" type="button" class="btn btn-primary" >
                                         修改
                                     </button>

                                     <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLocationModal">
                                         添加
                                     </button>

                                     <script type="text/javascript">


                                         function refresh() {
                                             $('#location-table').bootstrapTable('refresh');
                                         }

                                         function selectedRow_() {
                                             var row =  $('#location-table').bootstrapTable('getSelections');
                                             return row;
                                         }


                                         $("#editBtn").click(function() {
                                             var row = selectedRow_();

                                             if (row != '') {
                                                 $('#editLocationModal').modal();
                                                 alert(JSON.stringify(row[0]));
                                                 $('#editLocationModal .id').val(row[0].id);

                                             }
                                         });



                                     </script>

                                 </div>


                             </div>

                         </div>

                     <table class=" table-striped" id="location-table" data-url="${exitIntelligentUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                             <th data-field="state" data-radio="true"></th>
                             <th data-field="createDate">时间</th>
                             <th data-field="modifyDate">时间</th>

                             <th data-field="license" data-formatter="licinseInfoFormatter" >车牌号</th>


                             <th data-field="idNumber">身份证号</th>
                             <th data-field="distributor">提货单位</th>
                             <th data-field="product">产品</th>

                             <th data-field="message">说明</th>

                             <th data-field="status">状态</th>
                             <th data-field="status" data-formatter="operationInfoFormatter">操作</th>

                         </tr>
                         </thead>
                     </table>





                     </div>

                 </div>

</#if>
                 <div class="tab-pane      <#if !faceRemoteControl.intelligentIdentification_tab>active</#if> " id="delivery-order"  >
                     <div id="delivery_toolbar" class="" >

                         <button id="inputTareWeightBtn" type="button" class="btn btn-primary" >
                             输入皮重
                         </button>


                         <script type="text/javascript">


                             $("#inputTareWeightBtn").click(function() {
                                 var row =  $('#delivery-table').bootstrapTable('getSelections');

                                 if (row != '') {
                                    // $('#inpurtTareweightModal').modal();
                                  //  alert(JSON.stringify(row[0]));
                                     $('#inpurtTareweightModal .no').val(row[0].no);
                                     $('#reportDiv').load('${fragmentDeliveryReportInfoUrl}'+'?uuid='+row[0].uuid,function(result, textStatus, XMLHttpRequest){
                                        if(XMLHttpRequest.status == 200){
                                            $('#inpurtTareweightModal').modal({show:true});

                                            $('.selectpicker').selectpicker();


                                        }
                                         if(XMLHttpRequest.status == 401){

                                             $('#empModal .modal-body').load('${fragmentUrl}',function(result){
                                                 $('#empModal').modal({show:true});
                                             });
                                            // alert(JSON.stringify(XMLHttpRequest));

                                         }

                                       //  httpObj.status==401

                                     });
/*
                                     var content = row[0].no + " "+
                                             row[0].productNo + " "+
                                             row[0].distributorNo + " "+
                                             row[0].storageNo + " "+
                                             row[0].companyName + " "+
                                             row[0].operatorName + " "+
                                             row[0].operatorNo + " "+
                                             row[0].productName + " "+
                                             row[0].lisence + " ";

                                             $('#inpurtTareweightModal .name').val(content);*/
                                   //  GetAllOrders("fee",row[0].no);


                                 }
                             });



                         </script>

                         <script>
                             function statisticShippingDistributorFormatter(value, row, index) {
                                 return '<div class="  "><a  href="'+row.distributorUrl+'">'+ row.distributor + ' / '+ row.distributorNo + '</a></div>';
                             }


                             //获得全部订单信息(订单ID,订单名称)
                             function GetAllOrders(obj,no) {
                                 $.ajax({
                                     type: 'Get',
                                     url: '${feeUrl}',
                                     dataType: "Json",
                                     data: "no="+ no,
                                     success: function (data) {
                                        // alert(JSON.stringify(data));
                                         if (!data.flag) {
                                             $.each(data, function (i, n) {
                                                 $("#" + obj).append(" <option value=\"" + n.no + "\">" + n.no + "</option>");
                                             })
                                             $("#" + obj).selectpicker('refresh');

                                         }
                                     }
                                 })

                             }
                         </script>

                         <select class="selectpicker" data-max-options="2"  id="deliveryReportDistributorId" class="form-control" placeholder="特征">
                             <option value="" >全部</option>
                         <#if collaborators??>
                             <#list collaborators as feature>
                                 <option value="${feature.id}" >${feature.companyNo!''}--${feature.name!''}</option>

                             </#list>
                         </#if>

                         </select>
                     </div>


                     <table class=" table-striped" id="delivery-table" data-url="${deliveryOrderUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                            data-toolbar="#delivery_toolbar"
                             >
                         <thead>
                         <tr>
               <#--              <th data-field="uuid">uuid</th>-->
                             <th data-field="state" data-radio="true"></th>
                             <th data-field="createDate">时间</th>

                             <th data-field="license" data-formatter="licinseInfoFormatter" >车牌号</th>

                             <th data-field="deliveryOrderNo"  data-formatter="deliveryNoInfoFormatter">提煤单编号</th>
                             <th data-field="inventoryNo">产品编号</th>
                             <th data-field="granularity" data-formatter="productInfoFormatter" >煤种/粒度      </th>

                             <th data-field="companyName" data-formatter="distributorFormatter">批发商</th>


                             <th data-field="consigneeName">提煤司机</th>
                             <th data-field="consigneePhone">提煤电话</th>

                             <th data-field="operatorNo" >发货员编号</th>
                             <th data-field="operatorName" >姓名</th>
                             <th data-field="operatorPhone" >手机号</th>

                             <th data-field="status">状态</th>

<#--
                             <th data-field="status" data-formatter="operationInfoFormatter">操作</th>
-->
                             <th data-field="storageNo">堆场编号</th>
                             <th data-field="idNumber">提煤司机身份证号</th>


                         </tr>
                         </thead>
                     </table>

                 </div>


                 <div class="tab-pane " id="instanceTransport"  >
                     <div id="transport_toolbar" class="" >


                         <button id="inputNetWeightBtn" type="button" class="btn btn-primary" >
                             输入净重
                         </button>


                         <script type="text/javascript">

                             $("#inputTareWeightBtn").click(function() {
                                 var row =  $('#delivery-table').bootstrapTable('getSelections');

                                 if (row != '') {
                                     // $('#inpurtTareweightModal').modal();
                                     //  alert(JSON.stringify(row[0]));
                                     $('#inpurtTareweightModal .no').val(row[0].no);
                                     $('#reportDiv').load('${fragmentDeliveryReportInfoUrl}'+'?uuid='+row[0].uuid,function(result, textStatus, XMLHttpRequest){
                                         if(XMLHttpRequest.status == 200){
                                             $('#inpurtTareweightModal').modal({show:true});

                                             $('.selectpicker').selectpicker();


                                         }
                                         if(XMLHttpRequest.status == 401){

                                             $('#empModal .modal-body').load('${fragmentUrl}',function(result){
                                                 $('#empModal').modal({show:true});
                                             });
                                             // alert(JSON.stringify(XMLHttpRequest));

                                         }

                                         //  httpObj.status==401

                                     });
                                     /*
                                                                          var content = row[0].no + " "+
                                                                                  row[0].productNo + " "+
                                                                                  row[0].distributorNo + " "+
                                                                                  row[0].storageNo + " "+
                                                                                  row[0].companyName + " "+
                                                                                  row[0].operatorName + " "+
                                                                                  row[0].operatorNo + " "+
                                                                                  row[0].productName + " "+
                                                                                  row[0].lisence + " ";

                                                                                  $('#inpurtTareweightModal .name').val(content);*/
                                     //  GetAllOrders("fee",row[0].no);


                                 }
                             });



                             $("#inputNetWeightBtn").click(function() {
                                 var row =  $('#instance-table').bootstrapTable('getSelections');

                                 if (row != '') {

                                     $('#inpurtNetweightModal .no').val(row[0].no);
                                     $('#inpurtNetweightModalDiv').load('${fragmentDeliveryReportInfoNetweightUrl}'+'?uuid='+row[0].uuid,function(result, textStatus, XMLHttpRequest){
                                         if(XMLHttpRequest.status == 200){
                                             $('#inpurtNetweightModal').modal({show:true});

                                             $('.selectpicker').selectpicker();


                                         }
                                         if(XMLHttpRequest.status == 401){

                                             $('#empModal .modal-body').load('${fragmentUrl}',function(result){
                                                 $('#empModal').modal({show:true});
                                             });
                                             // alert(JSON.stringify(XMLHttpRequest));

                                         }

                                         //  httpObj.status==401

                                     });

   /*
                                     $('#inpurtNetweightModal').modal();
                                     //alert(JSON.stringify(row[0]));
                                     $('#inpurtNetweightModal .id').val(row[0].id);

                                     var content = row[0].no + " "+
                                             row[0].productNo + " "+
                                             row[0].distributorNo + " "+
                                             row[0].storageNo + " "+
                                             row[0].companyName + " "+
                                             row[0].operatorName + " "+
                                             row[0].operatorNo + " "+
                                             row[0].productName + " "+
                                             row[0].lisence + " ";

                                     $('#inpurtNetweightModal .name').val(content);
                                     GetAllOrders("fee",row[0].no);*/


                                 }
                             });



                         </script>

                         <select class="selectpicker" data-max-options="2"  id="transportDistributorId" class="form-control" placeholder="特征">
                             <option value="" >全部</option>
                         <#if collaborators??>
                             <#list collaborators as feature>
                                 <option value="${feature.id}" >${feature.companyNo!''}--${feature.name!''}</option>

                             </#list>
                         </#if>

                         </select>
                     </div>

                     <table class=" table-striped" id="instance-table" data-url="${instanceTransportUrl!''}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                            data-toolbar="#transport_toolbar"

                             >
                         <thead>
                         <tr>
        <#--                     <th data-field="uuid" >uuid</th>-->
                             <th data-field="state" data-radio="true"></th>
                             <th data-field="boundTime">入库时间</th>
                             <th data-field="plateNumber" data-formatter="licinseInfoFormatter">车牌号</th>

                             <th data-field="no"  data-formatter="instanceDeliveryNoInfoFormatter">提煤单编号</th>
                             <th data-field="inventoryNo">库存编号</th>
                             <th data-field="granularity" data-formatter="productInfoFormatter" >煤种/粒度      </th>

                             <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>


                             <th data-field="tareWeight">皮重</th>
                             <th data-field="unitPrice">单价</th>

                         <#--                             <th data-field="grossWeight" >毛重</th>
                                                      <th data-field="netWeight" >净重</th>-->
                             <th data-field="status" >状态</th>
                             <th data-field="description"   data-formatter="instanceOperationFormatter">描述</th>

                         </tr>
                         </thead>
                     </table>

                 </div>
                 <div class="tab-pane " id="instanceTransport_loaded"  >
                     <div id="transfer_toolbar" class="" >

                         <button id="correctBtn" type="button" class="btn btn-default" >
                             更正
                         </button>


                         <script type="text/javascript">


                             $("#correctBtn").click(function() {
                                 var row =  $('#transfer_no_reconciliation_table').bootstrapTable('getSelections');

                                 if (row != '') {
                                     $('#correctModalDiv').load('${inventory_transfer_correctUrl}'+'?uuid='+row[0].uuid,function(result, textStatus, XMLHttpRequest){
                                         if(XMLHttpRequest.status == 200){
                                             $('#correctModal').modal({show:true});
                                             $('.selectpicker').selectpicker();
                                         }
                                         if(XMLHttpRequest.status == 401){

                                             $('#empModal .modal-body').load('${fragmentUrl}',function(result){
                                                 $('#empModal').modal({show:true});
                                             });
                                         }
                                     });
                                 }
                             });



                         </script>
                     </div>


                     <table class=" table-striped" id="transfer_no_reconciliation_table" data-url="${transfer_no_reconciliation_Url}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                            data-toolbar="#transfer_toolbar"


                     >
                         <thead>
                         <tr>
                  <#--           <th data-field="uuid">uuid</th>-->

                             <th data-field="state" data-radio="true"></th>
                             <th data-field="no" data-formatter="inventoryTransferNoInfoFormatter">出库编号</th>
                             <th data-field="deliveryOrderNo">提煤单编号</th>

                             <th data-field="createDate">出库时间</th>
                             <th data-field="license" data-formatter="licinseInfoFormatter" >车牌号</th>



                         <#--                             <th data-field="tareWeight">皮重</th>
                                                      <th data-field="grossWeight" >毛重</th>-->
                             <th data-field="netWeight" >净重</th>

                             <th data-field="inventoryNo">库存编号</th>
                             <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>
                      <th data-field="tax" >税</th>
                             <th data-field="unitPrice" >单价</th>
                             <th data-field="amount" >总额</th>
                             <th data-field="taxAmount" >开票金额</th>

                             <th data-field="status" >状态</th>
                             <th data-field="duration">装车耗时</th>

                             <th data-field="reconcileStatus" >对账状态</th>
                             <th data-field="description"   data-formatter="instanceOperationFormatter">描述</th>

                         </tr>
                         </thead>
                     </table>

                 </div>



             </div>


        </div>




    </div>

<div id="addLocationModal" class="modal fade" role="dialog">
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

                            <form id="addLocationModalForm_"   novalidate="novalidate" action="{command_create_url}">
                                <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId hidden" name="companyId" value=""  >

                                <div class="form-group">
                                    <label for="username" class="control-label">名称</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="name" value=""  >


                                    <span class="help-block"></span>
                                </div>

                                <div class="form-group">
                                    <label for="username" class="control-label">邮编</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="postalCode" value=""  >
                                    <span class="help-block"></span>
                                </div>

                                <div class="form-group">
                                    <label for="username" class="control-label">描述</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                    <span class="help-block"></span>
                                </div>



                                <div class="form-group">
                                    <label for="username" class="control-label">特征</label>

                                    <select class="selectpicker" data-max-options="2"  id="feature_" name="feature" class="form-control" placeholder="特征">
                                    <#-- <select class="form-control select2" id="userType" name="userType"  placeholder="公司类型"  multiple="multiple">-->

                                     <#if featuresEnum??>
                                         <#list featuresEnum as feature>
                                             <option value="${feature.id}" >${feature.id!''}--${feature.displayName!''}</option>

                                         </#list>
                                     </#if>

                                    </select>



                                    </select>
                                    <span class="help-block"></span>
                                </div>


                                <button id="addLocationModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#addLocationModalFormBtn").click(function() {
                                    alert($('#addLocationModalForm').serialize());

                                    var req = $.ajax({
                                        url:  $('#addLocationModalForm').attr('action'),
                                        type: 'post',
                                        data:  $('#addLocationModalForm').serialize(),
                                    });
                                    req.done(function (data) {
                                        if (data.status) {
                                            alert("成功:"+data.message);
                                            $('#transfer_no_reconciliation_table').bootstrapTable('refresh');

                                           // window.location.reload(true);
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

<div id="inpurtTareweightModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content" id="reportDiv">

        </div>

    </div>
</div>
    <div id="editLocationModal" class="modal fade" role="dialog">
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

                                <form id="addLocationModalForm"   novalidate="novalidate" action="${command_edit_url}">
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="id hidden" name="id" value=""  >

                                    <div class="form-group">
                                        <label for="username" class="control-label">名称</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="name form-control" name="name" value=""  >


                                        <span class="help-block"></span>
                                    </div>

                                    <div class="form-group">
                                        <label for="username" class="control-label">邮编</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="postalCode form-control" name="postalCode" value=""  >
                                        <span class="help-block"></span>
                                    </div>

                                    <div class="form-group">
                                        <label for="username" class="control-label">描述</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="description form-control" name="description" value=""  >
                                        <span class="help-block"></span>
                                    </div>



                                    <div class="form-group">
                                        <label for="username" class="control-label">特征</label>

                                        <select class="selectpicker" data-max-options="2"  id="feature" name="feature" class="form-control" placeholder="特征">
                                        <#-- <select class="form-control select2" id="userType" name="userType"  placeholder="公司类型"  multiple="multiple">-->

                                    <#if featuresEnum??>
                                        <#list featuresEnum as feature>
                                            <option value="${feature.id}" >${feature.id!''}--${feature.displayName!''}</option>

                                        </#list>
                                    </#if>

                                        </select>


                                        <span class="help-block"></span>
                                    </div>


                                    <button id="addLocationModalFormBtn_"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                                </form>
                                <script  type="text/javascript">

                                    $("#addLocationModalFormBtn_").click(function() {
                                        alert($('#addLocationModalForm').serialize());

                                        var req = $.ajax({
                                            url:  $('#addLocationModalForm').attr('action'),
                                            type: 'put',
                                            data:  $('#addLocationModalForm').serialize(),
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

    <div id="inpurtNetweightModal" class="modal fade" role="dialog">
        <div id="login-overlay" class="modal-dialog">
            <div class="modal-content" id="inpurtNetweightModalDiv">

            </div>
        </div>
    </div>

    <div id="correctModal" class="modal fade" role="dialog">
        <div id="login-overlay" class="modal-dialog">
            <div class="modal-content" id="correctModalDiv">

            </div>



        </div>
    </div>


    <div class="modal fade" id="empModal" role="dialog">
        <div class="modal-dialog">


            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">登录</h4>
                </div>
                <div class="modal-body">

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

<script>
/*    $(document).ready(function(){
    });

    $(document).on('click', '[data-toggle="lightbox"]', function(event) {
        event.preventDefault();
        $(this).ekkoLightbox();
    });*/
</script>
<script type="text/javascript">
function originToDestinationInfoFormatter(value, row, index) {



    return '<div class="">'  + row.origin  +
            '<hr style="margin-top: 0px;       margin-bottom: 0px;" >'   +'<span class="pull-right" style="   margin-bottom: 0px; padding-bottom: 0px; ">'+ row.destination  +'</span></div>';

}
function operationInfoFormatter(value, row, index) {


    return  '<div class="">'  +'<a class="" href="' + row.url+ '">查看</a></div>';

}
function deliveryNoInfoFormatter(value, row, index) {


    return  '<div class="">'  +'<a class="" href="' + row.url+ '">'+row.no+'</a></div>';

}
function instanceDeliveryNoInfoFormatter(value, row, index) {


    return  '<div class="">'  +'<a class="" href="' + row.url+ '">'+row.deliveryOrderNo+'</a></div>';

}

function distributorFormatter(value, row, index) {
    return  '<div class="">'  +'<a class="" href="' + row.url+ '">'+row.distributorName+'/'+row.distributorNo+'</a></div>';
}

function instanceOperationFormatter(value, row, index) {

    /*    return '<a href="https://unsplash.it/1200/768.jpg?image=251" data-toggle="lightbox">'+
               // '<img src="https://unsplash.it/600.jpg?image=251" class="img-fluid">'+
                '查看'+
               '</a>'*/


    return  '<div class="">'  +'<a class="" href="' + row.url+ '">查看</a></div>';

}

function inventoryTransferNoInfoFormatter(value, row, index) {

    return  '<div class="">'  +'<a class="" href="' + row.url+ '">'+row.no+'</a></div>';

}


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



    return  "   <div class=''>"+
    "<strong><a href='"+ row.distributorUrl + "'> "+ row.distributor+"<a></strong> </div>";

}
function productInfoFormatter(value, row, index) {
    return "<span>"+ row.coalType+row.granularity+"</span>";
}

function licinseInfoFormatter(value, row, index) {
    return  "   <div class=''>"+
            "<strong>"+ row.license+"</strong> </div>";
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


    $('.selectpicker').on('change', function(){
        $('#delivery-table').bootstrapTable('refresh');

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
                //    stompClient.subscribe('/topic/COALPIT_DELIVERY_workbench/1', function (greeting) {
                $('#delivery-table').bootstrapTable('refresh');

                console.log("------"+JSON.parse(greeting.body))
                console.log("------"+JSON.stringify(greeting.body));
                //  alert(JSON.stringify(greeting.body));
                var map = JSON.parse(greeting.body);
                if(map.type=="Delivery_order_auth_scan"){
                    Delivery_order_auth_scan(map)

                }else{
                    event_follower(map)

                }

            });



            stompClient.subscribe('${websocket_topic_status}', function (greeting) {
                /*
                                console.log("------"+JSON.parse(greeting.body))
                                console.log("------ event-table ");*/
                //  alert(JSON.stringify(greeting.body));
                var map = JSON.parse(greeting.body);
                status_decision(map);
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
/*        $("form").on('submit', function (e) {
            e.preventDefault();
        });*/
        $( "#connect" ).click(function() { connect(); });
        connect();

        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendName(); });
    });





    function Delivery_order_auth_scan(map) {

        $.notify({
            title: map.distributor,
            message:map.plateNumber +" "+ map.productName,
            delay: 0
        },{
            // type: 'minimalist',
            delay: 0,
            placement: {
                from: 'bottom',
                align: 'left'
            }

        });
        //    label1.setContent(JSON.parse(greeting.body).plateNumber + Math.random())


        // showGreeting(JSON.parse(greeting.body));

    }


    function event_follower(map) {

        $.notify({
            title: map.distributor,
            message:map.plateNumber +" "+ map.productName,
            delay: 0
        },{
            type: 'success',

            // type: 'minimalist',
            delay: 0
        });
        //    label1.setContent(JSON.parse(greeting.body).plateNumber + Math.random())


        // showGreeting(JSON.parse(greeting.body));

    }



   var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
</script>


</html>