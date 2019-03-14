<#--
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人中心 库存详情 </title>

    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

  <script src="http://code.highcharts.com/highcharts.js"></script>
<#--    <script type="text/javascript" src="${rc.contextPath}/js/highcharts.js"></script>
    <script src="${rc.contextPath}/js/highcharts-more.js"></script>-->


<#--    <script type="text/javascript" src="${rc.contextPath}/js/highcharts.js"></script>-->
<#--    <script src="${rc.contextPath}/js/highcharts-more.js"></script>
    <script src="http://code.highcharts.com/maps/modules/data.js"></script>-->
</head>
<body>
<div class="container" style="margin-bottom:80px; margin-top:10px">

    <h1 class="page-header">
        <small>库存详情  / ${deliveryOrderMap.name!''}</small>
    </h1>



<div class=" ">
<div class="tabpanel">
    <ul class="nav nav-tabs " id="ajaxTabs" role="tablist">

        <li role="presentation" class=""><a href="#companies" aria-controls="capitalHistory" role="tab"
                                                  data-toggle="tab">基本信息 <span class="badge"></span></a></li>
        <li role="presentation" class="active"><a href="#transfer" aria-controls="capitalHistory" role="tab"
                                            data-toggle="tab">发货详情 <span class="badge"></span></a></li>
        <li role="presentation" class=""><a href="#transfer" aria-controls="priceTransfer" role="tab"
                                                  data-toggle="tab">价格变化 <span class="badge"></span></a></li>


        <li role="presentation" class=""><a href="#statistic" aria-controls="capitalHistory" role="tab"
                                            data-toggle="tab">统计信息 <span class="badge"></span></a></li>


    </ul>


    <div  class="tab-content">

    <div class="tab-pane " id="companies">

    <#if deliveryOrderMap??>
    <div class="">


    <div class="">



    <table class="table" data-show-header="false">
        <thead>
        <tr>
            <th colspan="4">基本信息</th>
        </tr>
        </thead>
        <tbody>

        <tr>
            <td class="active">状态：</td>
            <td colspan="3">

                <#if deliveryOrderMap.status?? && deliveryOrderMap.status =="Valid">

                    有效
                <#else>
                    无效
                </#if>
            </td>
        </tr>

            <#if  deliveryOrderMap.status?? && deliveryOrderMap.status =="Valid">

            <tr>
                <td class="active">提货密码：</td>
                <td colspan="3"><strong>${deliveryOrderMap.accessCode!''}</strong></td>
            </tr>

            </#if>



        <tr>
            <td class="active">贸易商：</td>
            <td colspan="3"><a href="${deliveryOrderMap.companyUrl!''}">${deliveryOrderMap.companyName!''}</a></td>
        </tr>

        <tr>
            <td class="active">货物：</td>
            <td><a href="${deliveryOrderMap.productUrl!''}" > ${deliveryOrderMap.productName!''}</a></td>
        </tr>


        <tr>
            <td class="active">来源：</td>
            <td><a href="${deliveryOrderMap.productSourceUrl!''}" > ${deliveryOrderMap.productSource!''}</a></td>
        </tr>


        </tbody>
    </table>


        <table  class="table table-condensed">
            <thead>
            <tr>
                <th>装货信息</th>
                <th></th>
            </tr>
            </thead>
            <tbody>



            <tr id="tr-id-1" class="tr-class-1">


                <td id="td-id-1" class="td-class-1">
                    堆场情况
                </td>
                <td>
                    <#if yard??>
                    ${yard.loaction!'--'}

                        <h3>等待装车车辆：${yardStatistic.countShipmentsLoading!'--'}</h3>
                        <h3>正在装车车辆：${yardStatistic.countShipmentsCreated!'--'}</h3>

                    </#if>
                </td>
            </tr>
                <#if deliveryOrderMap.storage??>
                <tr id="tr-id-1" class="tr-class-1">
                    <td id="td-id-1" class="td-class-1">
                        对场地
                    </td>
                    <td><a href="${deliveryOrderMap.storage.url}">${deliveryOrderMap.storage.name!''}</a></td>
                </tr>
                <tr id="tr-id-1" class="tr-class-1">
                    <td id="td-id-1" class="td-class-1">
                        地点
                    </td>

                    <td>${deliveryOrderMap.storage.province!'---'}${deliveryOrderMap.storage.city!'---'}${deliveryOrderMap.storage.district!'---'}${deliveryOrderMap.storage.street!'---'} </td>
                </tr>
                </#if>

            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    堆场负责人
                </td>
                <td>${deliveryOrderMap.shipperPrincipalName!'---'}</td>

            </tr>
            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1 success">
                    堆场负责人电话
                </td>
                <td><a href="tel:${deliveryOrderMap.shipperPrincipalName!'---'}">${deliveryOrderMap.shipperPrincipalPhone!'---'}</a></td>
            </tr>


            <tr>
                <th>车辆信息</th>
                <th></th>
            </tr>




            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1">
                    当前库存
                </td>
                <td>${deliveryOrder.quantityOnHand!''}</td>
            </tr>
            <tr id="tr-id-1" class="tr-class-1">
                <td id="td-id-1" class="td-class-1">
                    产品
                </td>
                <td>${deliveryOrder.coalType!''}/${deliveryOrder.granularity!'未定义'}</td>
            </tr>






            </tbody>
        </table>





    </div>



    </div>
    <#else>
    <div class="col-lg-12">
        <form id="longitudeLatitudeForm" class="navbar-form" role="search" action="${getUrl}">
            <input  type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <div class="form-group">
                <input class="input-lg" type="text" class="form-control" name="code" placeholder="" value="">
            </div>
        <#--            <div class="form-group">
                        <input type="text" class="form-control" name="latitude" placeholder="latitude 纬度" value="${destinationLatAndLng.bd09Lat!''}">
                    </div>-->
            <button id="longitudeLatitudeFormFormBtn" type="button" class="btn btn-primary  btn-lg">提取</button>
        </form>

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

    </div>

    </#if>

    </div>
    <div class="tab-pane active" id="transfer"  style="padding-top: 10px;padding-bottom: 10px">
        <div id="toolbar_transfer" class="btn-group">

   <#--         <button id="viewCapacityBtn" type="button" class="btn btn-success">
                <i class="">查看详情</i>
            </button>


            <button id="editBtn" type="button" class="btn btn-primary" >
                修改
            </button>-->

            <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLineModal">
                添加库存
            </button>

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
                        $('#addStationModal').modal();
                        alert(JSON.stringify(row[0]));
                        $('#addStationModalForm .companyId').val(row[0].id);

                    }
                });



            </script>


        </div>

        <table class=" table-striped" id="companies-table" data-url="${transferUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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


                <th data-field="distributor" data-formatter="distributorInfoFormatter">分销商</th>
                <th data-field="plateNumber">车牌号</th>
                <th data-field="amount">数量</th>
                <th data-field="balance">当前剩余库存</th>
                <th data-field="description">描述</th>
              <#--  <th data-field=""  data-formatter="operationInfoFormatter">操作</th>-->
            <#--
                                            <th data-field="companyName | senderCompanyName"  data-formatter="companyInfoFormatter">发送者</th>
            -->
            </tr>
            </thead>
        </table>

    </div>

    <div class="tab-pane " id="priceTransfer"  style="padding-top: 10px;padding-bottom: 10px">
        <div id="toolbar_transfer" class="btn-group">

        <#--         <button id="viewCapacityBtn" type="button" class="btn btn-success">
                     <i class="">查看详情</i>
                 </button>


                 <button id="editBtn" type="button" class="btn btn-primary" >
                     修改
                 </button>-->

            <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLineModal">
                添加库存
            </button>

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
                        $('#addStationModal').modal();
                        alert(JSON.stringify(row[0]));
                        $('#addStationModalForm .companyId').val(row[0].id);

                    }
                });



            </script>


        </div>

        <table class=" table-striped" id="companies-table" data-url="${priceTransferUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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


                <th data-field="distributor" data-formatter="distributorInfoFormatter">分销商</th>
                <th data-field="plateNumber">车牌号</th>
                <th data-field="amount">数量</th>
                <th data-field="balance">当前剩余库存</th>
                <th data-field="description">描述</th>
            <#--  <th data-field=""  data-formatter="operationInfoFormatter">操作</th>-->
            <#--
                                            <th data-field="companyName | senderCompanyName"  data-formatter="companyInfoFormatter">发送者</th>
            -->
            </tr>
            </thead>
        </table>

    </div>


    <div class="tab-pane " id="statistic">
        <div class="">
            <div id="tradeCountContainer" style="width:100%; height: 400px"></div>
        </div>
        <script type="text/javascript">

            var chart1; // 全局变量
            $(document).ready(function() {

                var req = $.ajax({
                    url: '${requestTradeCountTrendUrl}',
                    type: 'post',
                    data: {

                    }
                });
                req.done(function (data) {
                    getData = data;
                    //   alert(JSON.stringify(data));
                <#--           alert(JSON.stringify(getData));-->
                    splineIrregularTime();
                <#--       $("#priceTrendModal").modal();-->

                });

                var splineIrregularTime = function () {
                    Highcharts.setOptions({

                        global: {

                            useUTC: false

                        },

                        lang:{

                            months:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月','九月',  '十月','十一月', '十二月'],

                            weekdays:['星期日',  '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']

                        }

                    });


                    var chart1;
                    chart1 = new Highcharts.Chart({

                        chart: {
                            renderTo: 'tradeCountContainer', //图表放置的容器，DIV
                            zoomType: 'x',///   //x轴方向可以缩放
                            type: 'spline'
                        },
                        title: {
                            text: '平台请求成交数量统计'
                        },
                        subtitle: {
                            text: ''
                        },
                        xAxis: {
                            type: 'datetime',
                            dateTimeLabelFormats: { // don't display the dummy year

                                year: '%b',
                                month: '%b. %e  ',
                                month: '%Y-%m',
                                year: '%Y'
                            },
                            title: {
                                text: '时间'
                            }
                        },
                        yAxis: {
                            title: {
                                text: '元/吨'
                            },
                            min: 0
                        },
                        tooltip: {

                            shared: true,
                            useHTML: true,
                            headerFormat: '<small>{series.name}:</small><table>',
                            pointFormat: '<tr><td ">访问次数：<b>{point.y}</b> </td></tr>' ,
                            footerFormat: '</table>',
                            valueDecimals: 2

                        },

                        plotOptions: {
                            spline: {
                                marker: {
                                    enabled: true
                                }
                            }
                        },

                        series: getData
                    });
                };
                $(window).trigger("resize");
            });
        </script>
        <hr>


    </div>
    </div>

</div>




</div>
<div id="addLineModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">输入当前库存</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="well">

                            <form id="addLineModalForm"   novalidate="novalidate" action="${addInventoryUrl}">
                                <input style="margin-bottom: 15px;" type="" placeholder="" class="companyId hidden" name="id" value="${deliveryOrder.id}"  >


                                <div class="form-group">
                                    <label for="username" class="control-label">数量</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="数量" class="companyId form-control input-lg" name="amount" value=""  >


                                    <span class="help-block"></span>
                                </div>


                            <#--
                                                            <div class="form-group">
                                                                <label for="username" class="control-label">位置</label>

                                                                <select class="selectpicker" &lt;#&ndash;data-max-options="2" &ndash;&gt; id="departureStation" name="departureStation" class="form-control" placeholder="特征">
                                                                &lt;#&ndash; <select class="form-control select2" id="userType" name="userType"  placeholder="公司类型"  multiple="multiple">&ndash;&gt;

                                                                <#if stations??>
                                                                    <#list stations as feature>
                                                                        <option value="${feature.id}" >${feature.id!''}--${feature.name!''}--${feature.description!''}</option>

                                                                    </#list>
                                                                </#if>

                                                                </select>



                                                                </select>
                                                                <span class="help-block"></span>
                                                            </div>

                                -->

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





<script type="text/javascript">
    function distributorInfoFormatter(value, row, index) {

        return '<div class=""><a href="'+row.distributorUrl+'">'+row.distributor+'</a></div>';

    }


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