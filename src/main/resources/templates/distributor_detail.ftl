<#--<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人中心 客户详情 </title>

    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>


<#--    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/highcharts.js"></script>
    <script src="${rc.contextPath}/js/highcharts-more.js"></script>
    <script src="http://code.highcharts.com/maps/modules/map.js"></script>
    <script src="http://code.highcharts.com/maps/modules/data.js"></script>-->

</head>
<body>
<div class="container" style="margin-bottom:80px; margin-top:0px">


    <h1 class="page-header">
        <small>客户详情 / ${deliveryOrderMap.name}</small>
    </h1>
<div class=" ">
<div class="tabpanel">
    <ul class="nav nav-tabs " id="ajaxTabs" role="tablist">

        <li role="presentation" class=""><a href="#companies" aria-controls="capitalHistory" role="tab"
                                                  data-toggle="tab">基本信息 <span class="badge"></span></a></li>

        <li role="presentation" class="active"><a href="#deliveryOrer" aria-controls="deliveryOrer" role="tab"
                                                  data-toggle="tab">提煤单 <span class="badge"></span></a></li>

        <li role="presentation" class=""><a href="#transfer" aria-controls="capitalHistory" role="tab"
                                            data-toggle="tab">发货详情 <span class="badge"></span></a></li>
        <li role="presentation" class=""><a href="#reconciliation" aria-controls="capitalHistory" role="tab"
                                            data-toggle="tab">对账单 <span class="badge"></span></a></li>


        <li role="presentation" class=""><a href="#capital" aria-controls="capitalHistory" role="tab"
                                            data-toggle="tab">往来帐 <span class="badge"></span></a></li>


        <li role="presentation" class=""><a href="#employee" aria-controls="employee" role="tab"
                                            data-toggle="tab">员工 <span class="badge"></span></a></li>

        <li role="presentation" class=""><a href="#statistic" aria-controls="capitalHistory" role="tab"
                                            data-toggle="tab">统计信息 <span class="badge"></span></a></li>

        <li role="presentation" class=""><a href="#fee" aria-controls="quotation" role="tab"
                                            data-toggle="tab">价格周期 <span class="badge"></span></a></li>

    </ul>


    <div  class="tab-content" style="padding-top: 5px;padding-bottom: 10px">

    <div class="tab-pane " id="companies">


    <div class="row">
        <div class="col-lg-6" style="padding-top: 10px;padding-bottom: 10px">
            <div class="panel panel-default">
                <div class="panel-heading">基本信息</div>
                <div class="panel-body">

                </div>
                <ul class="list-group">
                    <li class="list-group-item">状态：</li>
                    <li class="list-group-item">编号： ${deliveryOrderMap.no}</li>
                    <li class="list-group-item">名称：${deliveryOrderMap.name!''}</li>

                </ul>
            </div>
        </div>
        <div class="col-lg-6" style="padding-top: 10px;padding-bottom: 10px">
            <div class="panel panel-default">
                <div class="panel-heading">财务信息</div>
                <div class="panel-body">
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
                        <td class="active">预收账款：</td>
                        <td colspan="3"><strong>${deliveryOrderMap.advancedPaymentAmount!''}</strong></td>
                    </tr>
                    <tr>
                        <td class="active">已核算账款：</td>
                        <td colspan="3"><strong>${deliveryOrderMap.advancedPaymentAmount!''}</strong></td>
                    </tr>


                    <tr>
                        <td class="active">贸易商：</td>
                        <td colspan="3"><a href="${deliveryOrderMap.companyUrl!''}">${deliveryOrderMap.abbreviationName!''}</a></td>
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

            </div>
        </div>
    </div>


    </div>

    <div class="tab-pane" id="transfer" >
        <div id="toolbar_inventory" class="" >

            <button id="editBtn" type="button" class="btn btn-success">
                <i class="">修改</i>
            </button>

            <select class="selectpicker"  id="feature" name="feature" class="form-control" placeholder="开户分销商">
                <option value="" >--</option>
            <#if distributors??>
                <#list distributors as feature>
                    <option value="${feature.id}" >${feature.id!''}--${feature.displayName!''}</option>
                </#list>
            </#if>
            </select>


            <button id="createReconciliationBtn" type="button" class="btn btn-primary hidden" >
                建立对账系统
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
               data-toolbar="#toolbar_inventory">
            <thead>
            <tr>



                <th data-field="createDate">名称</th>
                <th data-field="productName">产品</th>

                <th data-field="license">车牌号</th>
                <th data-field="amount">净重</th>


                <th data-field="balance">余额</th>
                <th data-field="reconcileStatus" >对账状态</th>
                <th data-field="description">描述</th>

            <#--
                                            <th data-field="companyName | senderCompanyName"  data-formatter="companyInfoFormatter">发送者</th>
            -->
            </tr>
            </thead>
        </table>

    </div>

    <div class="tab-pane " id="reconciliation" >

        <div id="reconciliation_toolbar" class="" >



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
                    var row = selectedRow_transfer();

                    if (row != '') {
                        $('#reconciliationModal').modal();
                        alert(JSON.stringify(row[0]));
                        $('#instanceId').val(row[0].id);

                    }
                });



            </script>


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

                <th data-field="inventoryNo" >产品编号</th>
                <th data-field="totalAmount" >金额</th>

                <th data-field="totalQuantity" >数量</th>
                <th data-field="status" >状态</th>

                <th data-field="no"  data-formatter="reconciliationOperationFormatter">操作</th>


            </tr>
            </thead>
        </table>

    </div>

    <div class="tab-pane active" id="deliveryOrer" >

        <table class=" table-striped" id="delivery-table" data-url="${orderDeliveryUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
               data-show-refresh="true" >
            <thead>
            <tr>
                <th data-field="state" data-radio="true"></th>
                <th data-field="createDate">时间</th>
                <th data-field="no">编号</th>

                <th data-field="license">车牌号</th>

                <th data-field="idNumber">身份证号</th>

                <th data-field="productName">产品</th>
                <th data-field="inventoryNo">产品编号</th>





                <th data-field="operatorNo" >发货员编号</th>
                <th data-field="operatorName" >姓名</th>
                <th data-field="operatorPhone" >手机号</th>

                <th data-field="status">状态</th>

                <th data-field="status" data-formatter="operationInfoFormatter">操作</th>

            </tr>
            </thead>
        </table>

    </div>
    <div class="tab-pane " id="capital" >
        <div id="toolbar_advancedPayment" class="btn-group" >



            <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLineModal">
                添加
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

        <table class=" table-striped" id="companies-table" data-url="${advancedPaymentTransferUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
               data-toolbar="#toolbar_advancedPayment">
            <thead>
            <tr>



                <th data-field="createDate">时间</th>




                <th data-field="amount">发生金额</th>

                <th data-field="credit">贷</th>
                <th data-field="debit">借</th>
                <th data-field="balance">余额</th>
                <th data-field="syncStatus">同步状态</th>
                <th data-field="description">描述</th>

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
        <div class="">
            <div id="advancedPaymentTrendCountContainer" style="width:100%; height: 400px"></div>
        </div>
        <script type="text/javascript">

            var chart1; // 全局变量
            $(document).ready(function() {

                var req = $.ajax({
                    url: '${getAdvancedPaymentTrendUrl}',
                    type: 'post',
                    data: {

                    }
                });
                req.done(function (data) {
                    getData = data;
                    //   alert(JSON.stringify(data));
                <#--           alert(JSON.stringify(getData));-->
                    splineIrregularTime_advancedPaymentTrendCountContainer();
                <#--       $("#priceTrendModal").modal();-->

                });

                var splineIrregularTime_advancedPaymentTrendCountContainer = function () {



                    var chart1;
                    chart1 = new Highcharts.Chart({

                        chart: {
                            renderTo: 'advancedPaymentTrendCountContainer', //图表放置的容器，DIV
                            zoomType: 'x',///   //x轴方向可以缩放
                            type: 'spline'
                        },
                        title: {
                            text: '资金流转'
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

    <div class="tab-pane " id="employee" >
    <div class="">

        <table class=" table-striped" id="companies-table" data-url="${employeeUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
               data-toolbar="#">
            <thead>
            <tr>



                <th data-field="createDate">名称</th>

                <th data-field="no">编号</th>

                <th data-field="name">名称</th>


                <th data-field="phone">手机号</th>

                <th data-field="description">描述</th>

            <#--
                                            <th data-field="companyName | senderCompanyName"  data-formatter="companyInfoFormatter">发送者</th>
            -->
            </tr>
            </thead>
        </table>

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
    <div class="">
        <div id="advancedPaymentTrendCountContainer" style="width:100%; height: 400px"></div>
    </div>
    <script type="text/javascript">

        $('.selectpicker').selectpicker();

        var chart1; // 全局变量
        $(document).ready(function() {

            var req = $.ajax({
                url: '${getAdvancedPaymentTrendUrl}',
                type: 'post',
                data: {

                }
            });
            req.done(function (data) {
                getData = data;
                //   alert(JSON.stringify(data));
            <#--           alert(JSON.stringify(getData));-->
                splineIrregularTime_advancedPaymentTrendCountContainer();
            <#--       $("#priceTrendModal").modal();-->

            });

            var splineIrregularTime_advancedPaymentTrendCountContainer = function () {



                var chart1;
                chart1 = new Highcharts.Chart({

                    chart: {
                        renderTo: 'advancedPaymentTrendCountContainer', //图表放置的容器，DIV
                        zoomType: 'x',///   //x轴方向可以缩放
                        type: 'spline'
                    },
                    title: {
                        text: '资金流转'
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
        <div role="tabpanel" class="tab-pane " id="fee">



        <#--table header row-->
            <div class="" >
                <div class="" id="fee_toolbar">
                    <a class="btn btn-default " id="completeFeeBtn"   ref="{completeFeeUrl}"> <i class="fa fa-certificate"></i> <span>完善费用设置</span> </a>
                    <a class="btn btn-default " id="addFeeBtn"   ref="{addFeeUrl}"> <i class="fa fa-certificate"></i> <span>增加费用类型</span> </a>



                    <script type="text/javascript">



                        $("#completeFeeBtn").click(function() {
                            var row =  $('#fee-table').bootstrapTable('getSelections');

                            if (row != '') {
                                $('#completeFeeModal').modal();
                                //alert(JSON.stringify(row[0]));
                                $('#completeFeeModalFormId').val(row[0].id);
                                $('#completeFeeModalFormDescription').val(row[0].id +" "+
                                        row[0].type+" "+
                                        row[0].pricingStrategy+" ");


                            }
                        });

                        $("#addFeeBtn").click(function() {
                            var row =  $('#fee-table').bootstrapTable('getSelections');

                            if (row != '') {
                                $('#addFeeModal').modal();
                                //alert(JSON.stringify(row[0]));
                                $('#addFeeModalFormId').val(row[0].id);
                                $('#addFeeModalFormDescription').val(row[0].id +" "+
                                        row[0].type+" "+
                                        row[0].pricingStrategy+" ");


                            }
                        });


                    </script>

                </div>

                <table class="table-striped" id="fee-table" data-url="${feeUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                       data-toolbar="#fee_toolbar"


                       data-search="true"
                       data-show-refresh="true"
                >

                    <thead>
                    <tr>

                        <th data-field="state" data-checkbox="true"></th>

                        <th data-field="createDate">创建时间</th>
                        <th data-field="no">编号</th>

                        <th data-field="tax">是否含税</th>
                        <th data-field="type">名称</th>
                        <th data-field="pricingStrategy">成本策略</th>


                        <th data-field="amount">金额</th>
                        <th data-field="status">状态</th>
                        <th data-field="" data-formatter="paymentOperationFormatter">操作</th>
                    </tr>
                    </thead>
                </table>


            </div>


        </div>

    </div>

</div>




</div>
<div id="boundDistributorModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">与平台帐号绑定</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form id="addLineModalForm"   novalidate="novalidate" action="${addInventoryUrl}">
                            <input style="margin-bottom: 15px;" type="" placeholder="" class="companyId hidden" name="id" value="${deliveryOrder.id}"  >


                            <div class="form-group">
                                <label for="username" class="control-label">编号</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="no" class="companyId form-control input-lg" name="no" value=""  >


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
<div id="getBoundDistributorQrcodeModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">获取绑定二维码</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form id="getBoundDistributorQrcodeModalForm"   novalidate="novalidate" action="${getBindQrcodeUrl}">
                            <input style="margin-bottom: 15px;" type="" placeholder="" class="companyId hidden" name="id" value="${deliveryOrder.id}"  >


                            <div class="form-group">
                                <label for="username" class="control-label">编号</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="no" class="companyId form-control input-lg" name="no" value=""  >


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

                            <button id="getBoundDistributorQrcodeModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                        </form>
                        <script  type="text/javascript">

                            $("#getBoundDistributorQrcodeModalFormBtn").click(function() {
                                alert($('#getBoundDistributorQrcodeModalForm').serialize());

                                var req = $.ajax({
                                    url:  $('#getBoundDistributorQrcodeModalForm').attr('action'),
                                    type: 'post',
                                    data:  $('#getBoundDistributorQrcodeModalForm').serialize(),
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



    function reconciliationOperationFormatter(value, row, index) {


        return   '<strong><a href="'+ row.url + '">操作</a></strong>';



    }

</script>
</body>
</html>