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
    <link href="${rc.contextPath}/css/lightbox/ekko-lightbox.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/js/lightbox/ekko-lightbox.min.js" type="text/javascript"></script>

   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>



    <script type="text/javascript"    src="${rc.contextPath}/js/bootstrap3-typeahead.js"></script>

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
<div class="container" style="margin-bottom:80px; margin-top:5px ">

<#include "./common/front_page_header_section.ftl">

<div class="row">
    <form action="app_web.do?method=getMainList" method="post"
          class="navbar-form navbar-right" role="form">
        <div class="input-group">
            <input type="text" class="form-control" name="search" id="temp" data-provide="typeahead" autocomplete="off" placeholder="输入问题搜索" >
            <span class="input-group-btn">
                <button class="btn btn-default" type="submit">搜索</button>
            </span>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(document).ready(function() {

        $("#temp_").typeahead({
            source: function (query, process) {
                return $.ajax({
                    url: 'bzsearch.do?method=prompt_keyword',//后台访问ajax地址
                    type: 'post',
                    data: {keyword: query},//后后参数名称
                    success: function (result) {
                        return process(result);
                    },
                });
            }
        });


        var localObjectData = [{ id: 1, name: '陕K',name_hidden: 'SK'  }, { id: 2, name: '陕D',name_hidden: 'SD' }, { id: 3, name: 'guangzhou',name_hidden: 'SC' }, { id: 4,name: 'sz',name_hidden: 'SH' }];
        var objMap = {};
        parseLocalObjectData();
        function parseLocalObjectData() {
            //typeahead只能处理简单的列表，所以要构造一个array string。名称对应的id放到objMap里面；
            $("#temp").typeahead({
                display:"name",
                source:localObjectData,
             /*   source: function (query, process) {
                    var names = [];
                    $.each(localObjectData, function (index, ele) {
                        objMap[ele.name] = ele.id;
                        names.push(ele.name);
                    });

                    alert(names);
                    process(names);//调用处理函数，格式化
                },*/
               matcher: function (item) {
                    //重写matcher，文本框内容改变时，查询是否有匹配的内容，有则返回true，在这里可以做一些判断取值之类的操作。
                    return ~item.name_hidden.toLowerCase().indexOf(this .query.toLowerCase());
                },
                updater: function(item) {
                    console.log("'" + item + "' selected.");
                    return item;
                },
                afterSelect: function (item) {//选中一条数据后的回调函数，此处可以向隐藏域赋值数据id
                    console.log(objMap[item]);//取出选中项的值
                    $("id").val(objMap[item]);
                }
            });
        };

        var $input = $("#temp__");
        $input.typeahead({
            minLength: 1,//键入字数多少开始补全
            showHintOnFocus: "true",//将显示所有匹配项
            fitToElement: true,//选项框宽度与输入框一致
            items: "all",//提示数量上限
            source: [
                { id: "someId1", name: "Display name 1" },
                { id: "someId2", name: "Display name 2" },
                { id: "someId3", name: "Display name 3" },
                { id: "someId4", name: "Display name 4" },
                { id: "someId5", name: "Display name 5" },
                { id: "someId6", name: "Display name 6" },
                { id: "someId7", name: "王大声道" },
                { id: "someId8", name: "大大声道" },
                { id: "someId9", name: "王大撒旦撒旦" },
                { id: "someId10", name: "哈是的" },
                { id: "someId11", name: "恩打发打发" },
                { id: "someId12", name: "啊大声道" },
            ],
            autoSelect: true
        });

    });
</script>
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
                    <li class="active"><a href="#companies" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">智能识别<span class="badge"></span></a></li>

                    <li class=""><a href="#delivery-order" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">预提煤单（有效）<span class="badge"></span></a></li>

                    <li class=""><a href="#instanceTransport" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">堆场正在装货<span class="badge"></span></a></li>
                    <li class=""><a href="#instanceTransport_loaded" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">出库<span class="badge"></span></a></li>

                    <li class=""><a href="#statistic" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">统计信息<span class="badge"></span></a></li>

                </ul>

            </div>

             <div  class="tab-content">



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

                             </div>

                         </div>





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






                     </div>

                 </div>


                 <div class="tab-pane " id="delivery-order"  >




                     <div id="delivery_toolbar" class="" >


                         <select class="selectpicker" data-max-options="2"  id="deliveryReportDistributorId" class="form-control" placeholder="特征">
                             <option value="" >全部</option>
                         <#if collaborators??>
                             <#list collaborators as feature>
                                 <option value="${feature.id}" >${feature.companyNo!''}--${feature.name!''}</option>
                             </#list>
                         </#if>
                         </select>
                     </div>


                     <table class="table-striped" id="delivery-table" data-url="${deliveryOrderUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                             <th data-field="state" data-radio="true"></th>
                             <th data-field="createDate">时间</th>
                             <th data-field="no">提煤单编号</th>
                             <th data-field="inventoryNo">产品编号</th>
                             <th data-field="granularity" data-formatter="productInfoFormatter" >煤种/粒度      </th>
                             <th data-field="companyName" data-formatter="distributorFormatter">批发商</th>
                             <th data-field="license" data-formatter="licinseInfoFormatter" >车牌号</th>
                             <th data-field="consigneeName">提煤人</th>
                             <th data-field="consigneePhone">提煤电话</th>
                             <th data-field="idNumber">体煤人身份证号</th>

                             <th data-field="operatorNo" >发货员编号</th>
                             <th data-field="operatorName" >姓名</th>
                             <th data-field="operatorPhone" >手机号</th>

                             <th data-field="status">状态</th>

                             <th data-field="status" data-formatter="operationInfoFormatter">操作</th>
                             <th data-field="storageNo">堆场编号</th>

                         </tr>
                         </thead>
                     </table>

                 </div>


                 <div class="tab-pane " id="instanceTransport"  >
                     <div id="transport_toolbar" class="" >
                         <select class="selectpicker" data-max-options="2"  id="transportDistributorId" class="form-control" placeholder="特征">
                             <option value="" >全部</option>
                         <#if collaborators??>
                             <#list collaborators as feature>
                                 <option value="${feature.id}" >${feature.companyNo!''}--${feature.name!''}</option>

                             </#list>
                         </#if>

                         </select>
                     </div>

                     <table class=" table-striped" id="instance-table" data-url="${instanceTransportUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                             <th data-field="state" data-checkbox="true"></th>
                             <th data-field="boundTime">入库时间</th>

                             <th data-field="inventoryNo">库存编号</th>
                             <th data-field="granularity" data-formatter="productInfoFormatter" >煤种/粒度      </th>

                             <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>

                             <th data-field="plateNumber" data-formatter="licinseInfoFormatter">车牌号</th>

                             <th data-field="tareWeight">皮重</th>
<#--                             <th data-field="grossWeight" >毛重</th>
                             <th data-field="netWeight" >净重</th>-->
                             <th data-field="status" >状态</th>
                             <th data-field="description"   data-formatter="instanceOperationFormatter">描述</th>

                         </tr>
                         </thead>
                     </table>

                 </div>
                 <div class="tab-pane " id="instanceTransport_loaded"  >


                     <table class=" table-striped" id="instance_loaded_table" data-url="${instanceTransport_loaded_Url}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                            data-show-refresh="true">
                         <thead>
                         <tr>
                             <th data-field="state" data-checkbox="true"></th>
                             <th data-field="no">出库编号</th>

                             <th data-field="createDate">出库时间</th>
                             <th data-field="duration">装车耗时</th>

                             <th data-field="inventoryNo">库存编号</th>
                             <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>
                             <th data-field="license" data-formatter="licinseInfoFormatter" >车牌号</th>



<#--                             <th data-field="tareWeight">皮重</th>
                             <th data-field="grossWeight" >毛重</th>-->
                             <th data-field="netWeight" >净重</th>
                             <th data-field="unitPrice" >单价</th>
                             <th data-field="totalPrice" >总额</th>
                             <th data-field="taxAmount" >开票金额</th>

                             <th data-field="status" >状态</th>

                             <th data-field="reconcileStatus" >对账状态</th>
                             <th data-field="description"   data-formatter="instanceOperationFormatter">描述</th>

                         </tr>
                         </thead>
                     </table>

                 </div>



                 <div class="tab-pane " id="statistic" style="padding-top: 10px;padding-bottom: 10px" >



                     <div class="">
                         <div class="col-lg-6">
                         <table class="table" data-show-header="false">
                             <thead>
                             <tr>
                                 <th >基本信息</th>
                             </tr>
                             </thead>
                             <tbody>


                             <tr>
                                 <td class="active">库存数量</td>
                                 <td >


                                     ${statistic.inventoryNumber!''}
                                 </td>
                             </tr>




                             <tr>
                                 <td class="active">贸易商数量</td>
                                 <td >${statistic.distributorNumber!''}</td>
                             </tr>



                             </tbody>
                         </table>

                         </div>
                         <div class="col-lg-6">
                             <table class="table" data-show-header="false">
                                 <thead>
                                 <tr>
                                     <th >基本信息</th>
                                 </tr>
                                 </thead>
                                 <tbody>


                                 <tr>
                                     <td class="active">库存数量</td>
                                     <td >


                                     ${statistic.inventoryNumber!''}
                                     </td>
                                 </tr>




                                 <tr>
                                     <td class="active">贸易商数量</td>
                                     <td >${statistic.distributorNumber!''}</td>
                                 </tr>



                                 </tbody>
                             </table>


                         </div>

                     </div>



                     <div class="col-lg-6">

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
                         <table class="table" data-show-header="false">
                             <thead>
                             <tr>
                                 <th >产品名称<#--<a href="${statistic.inventories_management_url}" class="btn btn-primary pull-right" >管理</a>--></th>
                                 <th >税</th>

                                 <th >价格</th>

                                 <th >库存</th>
                             </tr>
                             </thead>
                             <tbody>



                             <#list statistic.inventories as distributor>
                             <tr>

                                 <td class="active">
                                     <a href="${distributor.url}">${distributor.coalType!''}  ${distributor.granularity!''}</a>

                                 </td>
                                 <td >

                                 </td>

                                 <td >
                                 ${distributor.quote!''}
                                 </td>

                                 <td >${distributor.quantityOnHand!''}</td>
                             </tr>

                             </#list>





                             </tbody>
                         </table>


                     </div>


<#--

                     <table class=" table-striped" id="instance-table" data-url="${instanceTransportUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                            data-show-refresh="true">
                         <thead>
                         <tr>
                             <th data-field="state" data-checkbox="true"></th>


                             <th data-field="outboundTime">出库时间</th>
                             <th data-field="boundTime">装车时间</th>

                             <th data-field="plateNumber">车牌号</th>
                             <th data-field="distributor" data-formatter="companyInfoFormatter">分销商</th>

                             <th data-field="tareWeight">皮重</th>
                             <th data-field="grossWeight" >毛重</th>
                             <th data-field="netWeight" >净重</th>
                             <th data-field="status" >状态</th>
                             <th data-field="description"   data-formatter="instanceOperationFormatter">描述</th>

                         </tr>
                         </thead>
                     </table>
-->

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


<#--<#include "./common/page_foot_section.ftl">

<#include "./common/hotline_section.ftl">-->



</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

<script>
    $(document).ready(function(){
    });

    $(document).on('click', '[data-toggle="lightbox"]', function(event) {
        event.preventDefault();
        $(this).ekkoLightbox();
    });
</script>
<script type="text/javascript">
function originToDestinationInfoFormatter(value, row, index) {



    return '<div class="">'  + row.origin  +
            '<hr style="margin-top: 0px;       margin-bottom: 0px;" >'   +'<span class="pull-right" style="   margin-bottom: 0px; padding-bottom: 0px; ">'+ row.destination  +'</span></div>';

}
function operationInfoFormatter(value, row, index) {

/*    return '<a href="https://unsplash.it/1200/768.jpg?image=251" data-toggle="lightbox">'+
           // '<img src="https://unsplash.it/600.jpg?image=251" class="img-fluid">'+
            '查看'+
           '</a>'*/


    return  '<div class="">'  +'<a class="" href="' + row.url+ '">查看</a></div>';

}
function distributorFormatter(value, row, index) {
    return  '<div class="">'  +'<a class="" href="' + row.url+ '">'+row.companyName+'/'+row.distributorNo+'</a></div>';
}

function instanceOperationFormatter(value, row, index) {

    /*    return '<a href="https://unsplash.it/1200/768.jpg?image=251" data-toggle="lightbox">'+
               // '<img src="https://unsplash.it/600.jpg?image=251" class="img-fluid">'+
                '查看'+
               '</a>'*/


    return  '<div class="">'  +'<a class="" href="' + row.url+ '">查看</a></div>';

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
    "<h4><a href='"+ row.distributorUrl + "'> "+ row.distributor+"<a></h4> </div>";

}
function productInfoFormatter(value, row, index) {
    return "<span>"+ row.coalType+row.granularity+"</span>";
}

function licinseInfoFormatter(value, row, index) {
    return  "   <div class=''>"+
            "<h4>"+ row.license+"</h4> </div>";
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
        '           <img src="../logo.png" alt="Image">'+
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
       // alert(JSON.stringify(original));
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
                    title: map.distributor,
                    message:map.plateNumber +" "+ map.productName,
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