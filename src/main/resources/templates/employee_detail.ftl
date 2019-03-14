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


    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/highcharts.js"></script>
    <script src="${rc.contextPath}/js/highcharts-more.js"></script>
    <script src="http://code.highcharts.com/maps/modules/map.js"></script>
    <script src="http://code.highcharts.com/maps/modules/data.js"></script>

</head>
<body>
<div class="container" style="margin-bottom:80px; margin-top:10px">


    <h1 class="page-header">
        <small>员工详情 / ${employee.name!''}</small>
    </h1>
<div class=" ">
<div class="tabpanel">
    <ul class="nav nav-tabs " id="ajaxTabs" role="tablist">

        <li role="presentation" class="active"><a href="#companies" aria-controls="capitalHistory" role="tab"
                                                  data-toggle="tab">基本信息 <span class="badge"></span></a></li>




    </ul>


    <div  class="tab-content" style="padding-top: 5px;padding-bottom: 10px">

    <div class="tab-pane active" id="companies">


    <div class="row">
        <div class="col-lg-6" style="padding-top: 10px;padding-bottom: 10px">
            <div class="panel panel-default">
                <div class="panel-heading">基本信息</div>
                <div class="panel-body">

                </div>
                <ul class="list-group">
                    <li class="list-group-item">状态：</li>
                    <li class="list-group-item">编号： ${employee.companyNo!''}</li>
                    <li class="list-group-item">名称：${employee.name!''}</li>

                </ul>
            </div>
        </div>
        <div class="col-lg-6" style="padding-top: 10px;padding-bottom: 10px">
            <div class="panel panel-default">
                <div class="panel-heading">财务信息</div>
                <div class="panel-body">
                <#if employee??>
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
                        <td colspan="3"><strong>${employee.advancedPaymentAmount!''}</strong></td>
                    </tr>
                    <tr>
                        <td class="active">已核算账款：</td>
                        <td colspan="3"><strong>${employee.advancedPaymentAmount!''}</strong></td>
                    </tr>


                    <tr>
                        <td class="active">贸易商：</td>
                        <td colspan="3"><a href="${employee.companyUrl!''}">${employee.abbreviationName!''}</a></td>
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
                        <form id="addLineModalForm"   novalidate="novalidate" action="{addInventoryUrl}">
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

                            <form id="addLineModalForm"   novalidate="novalidate" action="{addInventoryUrl}">
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