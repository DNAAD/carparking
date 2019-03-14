<#--<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人中心 出库详情 </title>

    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>


    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
</head>
<body>
<div class="container" style="margin-bottom:80px; margin-top:10px">


<ol class="breadcrumb">
    <li class="active">堆场管理</li>
    <li class="active">出库详情</li>
</ol>

<div class="">


<#if true>
<div class="tabpanel">
<ul class="nav nav-tabs " id="ajaxTabs" role="tablist">

    <li role="presentation" class="active"><a href="#companies" aria-controls="capitalHistory" role="tab"
                                        data-toggle="tab">基本信息 <span class="badge"></span></a></li>

    <li role="presentation" class=""><a href="#statistic" aria-controls="capitalHistory" role="tab"
                                        data-toggle="tab">提煤单 <span class="badge"></span></a></li>
    <li role="presentation" class=""><a href="#transfer" aria-controls="capitalHistory" role="tab"
                                              data-toggle="tab">堆场装车 <span class="badge"></span></a></li>
    <li role="presentation" class=""><a href="#transfer" aria-controls="capitalHistory" role="tab"
                                        data-toggle="tab">对账信息 <span class="badge"></span></a></li>

</ul>


<div  class="tab-content">

<div class="tab-pane active " id="companies">

    <div class="col-lg-6 ">



        <table class="table" data-show-header="false">
            <thead>
            <tr>
                <th colspan="4">出库信息</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td class="active">编号：</td>
                <td colspan="3">

                    ${transferMap.no}
                </td>
            </tr>

            <tr>
                <td class="active">状态：</td>
                <td colspan="3">

                    <#if transferMap.status?? && transferMap.status =="Valid">

                        有效
                    <#else>
                        无效
                    </#if>
                </td>
            </tr>
            <tr>
                <td class="active">提货单编号：</td>
                <td colspan="3">

                ${transferMap.deliveryOrderNo}
                </td>
            </tr>



                <#if transferMap.status?? && transferMap.status =="Valid">
                <tr>
                    <td class="active">提货密码：</td>
                    <td colspan="3"><strong>${transferMap.accessCode!''}</strong></td>
                </tr>
                </#if>
            <tr>
                <td class="active">贸易商：</td>
                <td colspan="3"><a href="${transferMap.distributorUrl!''}">${transferMap.distributor!''}</a></td>
            </tr>

            <tr>
                <td class="active">货物：</td>
                <td><a href="${transferMap.productUrl!''}" > ${transferMap.coalType!''} ${transferMap.coalType!''} ${transferMap.productNo}</a></td>
            </tr>


            <tr>
                <td class="active">库存</td>
                <td colspan="3"><a href="${transferMap.distributorUrl!''}">${transferMap.inventoryNo!''}</a></td>
            </tr>

            <tr>
                <td class="active">单价</td>
                <td colspan="3">${transferMap.unitPrice!''}</td>
            </tr>
            <tr>
                <td class="active">数量</td>
                <td colspan="3">${transferMap.weight!''} </td>
            </tr>


            <tr>
                <td class="active">价格策略</td>
                <td >${inventoryTransfer.picingStrategy!''}</td>
            </tr>
            <tr>
                <td class="active">折扣</td>
                <td >${inventoryTransfer.discount!''} </td>
            </tr>
            <tr>
                <td class="active">总额--</td>
                <td colspan="3">${inventoryTransfer.amount!''} </td>
            </tr>

            <tr>
                <td class="active">是否含税</td>
                <td colspan="3">${transferMap.tax!''}</td>
            </tr>
            <tr>
                <td class="active">开票金额</td>
                <td colspan="3">${inventoryTransfer.taxAcount!''} </td>
            </tr>

            <tr>
                <td class="active">过磅员</td>
                <td colspan="3">${transferMap.weighmanName!''} / ${transferMap.weighmanNo!''}</td>
            </tr>
            <tr>
                <td class="active">过磅员联系方式</td>
                <td colspan="3">${inventoryTransfer.weighmanPhone!''}</td>
            </tr>




            <tr>
                <th>车辆信息</th>
                <th></th>
            </tr>




            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    车牌号
                </td>
                <td>${transferMap.plateNumber!''}</td>
            </tr>
            <#--        <tr id="tr-id-1" class="tr-class-1">
                        <td id="td-id-1" class="td-class-1">
                            载重
                        </td>
                        <td>${transferMap.carryingCapacity!''}</td>
                    </tr>-->

            <#--        <tr id="tr-id-1" class="tr-class-1">
                        <td id="td-id-1" class="td-class-1 success">
                            类型
                        </td>
                        <td>${transferMap.vehicleType!'未定义'}</td>
                    </tr>-->





            <tr>
                <th>司机信息</th>
                <th></th>
            </tr>




            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    名字
                </td>
                <td><a href=""> ${transferMap.carrierName!'---'}</a></td>
            </tr>

            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    电话
                </td>
                <td><a href="tel:${transferMap.carrierPhone!''}">${transferMap.carrierPhone!''}</a>
                </td>
            </tr>


            <tr>
                <th>装货情况</th>
                <th></th>
            </tr>


<#--            modelAndView.addObject("reportDeliveryOrder",reportDeliveryOrder);



            InstanceTransport instanceTransport1 = instanceTransportRepository.findById(instanceTransport.getInstanceId()).get();

            modelAndView.addObject("instanceTransport",instanceTransport1);-->


            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    状态
                </td>
                <td> ${instanceTransport.status!'---'}</td>
            </tr>

            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    入场时间
                </td>
                <td>${instanceTransport.boundTime!'--'}
                </td>
            </tr>

            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    离场时间
                </td>
                <td>${instanceTransport.outboundTime!'--'}
                </td>
            </tr>
            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    装载用时
                </td>
                <td>${instanceTransport.loadingTime!'--'}
                </td>
            </tr>
            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    皮重
                </td>
                <td>${instanceTransport.tareWeight!'--'}
                </td>
            </tr>
            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    毛重
                </td>
                <td>${instanceTransport.grossWeight!'--'}
                </td>
            </tr>
            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    净重
                </td>
                <td>${instanceTransport.netWeight!'--'}
                </td>
            </tr>
            </tbody>
        </table>



    </div>
    <div class="col-lg-6 ">



        <table class="table" data-show-header="false">
            <thead>
            <tr>
                <th colspan="4">提煤单信息</th>
            </tr>
            </thead>
            <tbody>

            <tr>
                <td class="active">状态：</td>
                <td colspan="3">
                    ${reportDeliveryOrder.status}

                </td>
            </tr>
            <tr>
                <td class="active">提货单编号：</td>
                <td colspan="3">

                    ${reportDeliveryOrder.no}
                </td>
            </tr>

            <tr>
                <td class="active">下单时间：</td>
                <td colspan="3">

                    ${reportDeliveryOrder.createDate}
                </td>
            </tr>

                <#if transferMap.status?? && transferMap.status =="Valid">
                <tr>
                    <td class="active">提货密码：</td>
                    <td colspan="3"><strong>${transferMap.accessCode!''}</strong></td>
                </tr>
                </#if>
            <tr>
                <td class="active">生产商：</td>
                <td colspan="3">${reportDeliveryOrder.producerNo!''}</td>
            </tr>
            <tr>
                <td class="active">分销商：</td>
                <td colspan="3"><a href="${transferMap.distributorUrl!''}">${transferMap.distributor!''}</a></td>
            </tr>

            <tr>
                <td class="active">货物：</td>
                <td><a href="${transferMap.productUrl!''}" > ${reportDeliveryOrder.productName!''}  ${reportDeliveryOrder.productNo}</a></td>
            </tr>

            <tr>
                <td class="active">堆场</td>
                <td colspan="3">${reportDeliveryOrder.storageNo!''}</td>
            </tr>
            <tr>
                <td class="active">库存</td>
                <td colspan="3"><a href="${transferMap.distributorUrl!''}">${reportDeliveryOrder.inventoryNo!''}</a></td>
            </tr>






            <tr>
                <th>车辆信息</th>
                <th></th>
            </tr>




            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    车牌号
                </td>
                <td>${reportDeliveryOrder.license!''}</td>
            </tr>
            <#--        <tr id="tr-id-1" class="tr-class-1">
                        <td id="td-id-1" class="td-class-1">
                            载重
                        </td>
                        <td>${transferMap.carryingCapacity!''}</td>
                    </tr>-->

            <#--        <tr id="tr-id-1" class="tr-class-1">
                        <td id="td-id-1" class="td-class-1 success">
                            类型
                        </td>
                        <td>${transferMap.vehicleType!'未定义'}</td>
                    </tr>-->





            <tr>
                <th>司机信息</th>
                <th></th>
            </tr>




            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    名字
                </td>
                <td><a href=""> ${reportDeliveryOrder.consigneeName!'---'}</a></td>
            </tr>

            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    电话
                </td>
                <td><a href="tel:${reportDeliveryOrder.consigneePhone!''}">${reportDeliveryOrder.consigneePhone!''}</a>
                </td>
            </tr>

            <tr>
                <th>操作员</th>
                <th></th>
            </tr>




            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    名字
                </td>
                <td><a href=""> ${reportDeliveryOrder.operatorName!'---'}</a></td>
            </tr>

            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    电话
                </td>
                <td><a href="tel:${reportDeliveryOrder.operatorPhone!''}">${reportDeliveryOrder.operatorPhone!''}</a>
                </td>
            </tr>

            </tbody>
        </table>



    </div>
</div>
<div class="tab-pane " id="statistic"  style="padding-top: 10px;padding-bottom: 10px">
    <div id="toolbar_transfer_statistic" class="btn-group">



    </div>
    <table class=" table-striped" id="companies-table" data-url="{statisticUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
           data-toolbar="#toolbar_transfer_statistic">
        <thead>
        <tr>


            <th data-field="no">库存编号</th>
            <th data-field="coalType">类型</th>
            <th data-field="granularity">粒度</th>
            <th data-field="count">个数</th>

            <th data-field="totalQuantity">数量</th>
            <th data-field="totalPrice">总价</th>

        </tr>
        </thead>
    </table>


</div>
<div class="tab-pane " id="transfer"  style="padding-top: 10px;padding-bottom: 10px">

    <table class=" table-striped" id="companies-table" data-url="{reconciliationItemsUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
           data-toolbar="#toolbar_transfer">
        <thead>
        <tr>



            <th data-field="createDate">名称</th>
            <th data-field="deliveryOrderNo">提煤单编号</th>


            <th data-field="inventoryNo">库存编号</th>


            <th data-field="distributor" data-formatter="distributorInfoFormatter">分销商</th>
            <th data-field="license">车牌号</th>
            <th data-field="amount">数量</th>
            <th data-field="unitPrice">单价</th>
            <th data-field="totalPrice">总价</th>


            <th data-field="quality">负责人</th>
            <th data-field="description">描述</th>

        </tr>
        </thead>
    </table>

</div>

</div>

</div>



<#else>
<div class="col-lg-12">
    <form id="longitudeLatitudeForm" class="navbar-form" role="search" action="{getUrl}">
        <input  type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <div class="form-group">
        <input class="input-lg" type="text" class="form-control" name="code" placeholder="" value="">
    </div>

    <button id="longitudeLatitudeFormFormBtn" type="button" class="btn btn-primary  btn-lg">提取</button>
        <script  type="text/javascript">

            $("#longitudeLatitudeFormFormBtn").click(function() {
                alert($('#longitudeLatitudeForm').serialize());
                var req = $.ajax({
                    url:  $('#longitudeLatitudeForm').attr('action'),
                    type: 'POST',
                    data:  $('#longitudeLatitudeForm').serialize(),
                });
                req.done(function (data) {
                    alert("成功:"+data.message);

                    if (data.status) {
                        alert("成功:"+data.message);

                        window.location.href = data.url;
                    } else {
                        alert(data.message);
                    }
                });
            });
        </script>

    </form>


</div>

</#if>



</div>
<div id="addLineModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">录入毛重          ${transferMap.plateNumber!''}</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form id="addLineModalForm"   novalidate="novalidate" action="${createUrl}">
                            <input style="margin-bottom: 15px;" type="" placeholder="" class="companyId hidden" name="id" value="${transferMap.id}"  >
                            <div class="form-group">
                                <label for="username" class="control-label">皮重</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="毛重" disabled class="companyId form-control input-lg" name="tareWeight" value="${transferMap.tareWeight!''}"  >


                                <span class="help-block"></span>
                            </div>

                            <div class="form-group">
                                <label for="username" class="control-label">毛重</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="毛重" class="companyId form-control input-lg" name="grossWeight" value=""  >


                                <span class="help-block"></span>
                            </div>


                            <div class="form-group">
                                <label for="username" class="control-label">净重</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="netWeight" class="netWeight form-control" name="netWeight" value=""  >
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







<script type="text/javascript">
    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;
        var status = $.trim($("#status").val());


        if (status) {
            params.status = status;
        }

        return params;
    }


    function handleResponse(original) {
        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;

        return res;
    }




</script>
</body>
</html>