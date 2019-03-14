<#--<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人中心 堆场装货 </title>

    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>


    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<div class="container" style="margin-bottom:80px; margin-top:10px">


<ol class="breadcrumb">
    <li class="active">堆场管理</li>
    <li class="active">装车信息</li>
</ol>

<div class="row">

<#if transportMap??>
<div class="">


<div class="col-lg-6 ">



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

                <#if transportMap.status?? && transportMap.status =="Valid">

                    有效
                <#else>
                    无效
                </#if>
            </td>
        </tr>
        <tr>
            <td class="active">提货单编号：</td>
            <td colspan="3">

                ${transportMap.deliveryOrderNo}
            </td>
        </tr>



            <#if transportMap.status?? && transportMap.status =="Valid">
            <tr>
                <td class="active">提货密码：</td>
                <td colspan="3"><strong>${transportMap.accessCode!''}</strong></td>
            </tr>
            </#if>
        <tr>
            <td class="active">贸易商：</td>
            <td colspan="3"><a href="${transportMap.distributorUrl!''}">${transportMap.distributor!''}</a></td>
        </tr>

        <tr>
            <td class="active">货物：</td>
            <td><a href="${transportMap.productUrl!''}" > ${transportMap.coalType!''} ${transportMap.coalType!''} ${transportMap.productNo}</a></td>
        </tr>


        <tr>
            <td class="active">库存</td>
            <td colspan="3"><a href="${transportMap.distributorUrl!''}">${transportMap.inventoryNo!''}</a></td>
        </tr>


        <tr>
            <td class="active">可用余额</td>
            <td colspan="3"><a href="${transportMap.inventoryUrl!''}">${transportMap.advancedPaymentAmount!''}</a></td>
        </tr>





        <tr>
            <th>车辆信息</th>
            <th></th>
        </tr>




        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                车牌号
            </td>
            <td>${transportMap.plateNumber!''}</td>
        </tr>
<#--        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1">
                载重
            </td>
            <td>${transportMap.carryingCapacity!''}</td>
        </tr>-->

<#--        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                类型
            </td>
            <td>${transportMap.vehicleType!'未定义'}</td>
        </tr>-->





        <tr>
            <th>司机信息</th>
            <th></th>
        </tr>




        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                名字
            </td>
            <td><a href=""> ${transportMap.carrierName!'---'}</a></td>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                电话
            </td>
            <td><a href="tel:${transportMap.carrierPhone!''}">${transportMap.carrierPhone!''}</a>
            </td>
        </tr>


        <tr>
            <th>装货情况</th>
            <th></th>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                状态
            </td>
            <td> ${transportMap.status!'---'}</td>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                入场时间
            </td>
            <td>${deliveryOrder.boundTime!'--'}
            </td>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                离场时间
            </td>
            <td>${transportMap.outboundTime!'--'}
            </td>
        </tr>
        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                装载用时
            </td>
            <td>${transportMap.loadingTime!'--'}
            </td>
        </tr>
        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                皮重
            </td>
            <td>${transportMap.tareWeight!'--'}
            </td>
        </tr>
        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                毛重
            </td>
            <td>${transportMap.grossWeight!'--'}
            </td>
        </tr>
        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                净重
            </td>
            <td>${transportMap.netWeight!'--'}
            </td>
        </tr>
        </tbody>
    </table>



</div>
<div class="col-lg-6">

    <form id="inputGrossWeightModalForm"  action="${createUrl}">
        <input  type="hidden" class="companyId " name="id" value="${transportMap.id}"  >
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div class="form-group">
            <label for="username" class="control-label">皮重</label>
            <input style="margin-bottom: 15px;"  placeholder="毛重" disabled class="companyId form-control input-lg" name="tareWeight" value="${transportMap.tareWeight}"  >

            <span class="help-block"></span>
        </div>

        <div class="form-group">
            <label for="username" class="control-label">毛重</label>
            <input style="margin-bottom: 15px;" type="" placeholder="毛重" class="companyId form-control input-lg" name="grossWeight" value=""  >
            <span class="help-block"></span>
        </div>

        <button id="inputGrossWeightFormBtn"  type="button"   class="btn btn-primary btn-lg">确定</button>
        <script  type="text/javascript">

            $("#inputGrossWeightFormBtn").click(function() {
                alert("============="+$('#inputGrossWeightModalForm').serialize());

                var req = $.ajax({
                    url:  $('#inputGrossWeightModalForm').attr('action'),
                    type: 'post',
                    data:  $('#inputGrossWeightModalForm').serialize(),
                });
                req.done(function (data) {
                    alert(JSON.stringify(data));

                    if (data.status) {
                        alert("成功:"+JSON.stringify(data));
                        window.location.reload(true);
                    } else {
                        alert(data.message);
                    }
                });



            });
        </script>
    </form>



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
                <h4 class="modal-title" id="myModalLabel">录入毛重          ${transportMap.plateNumber!''}</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form id="addLineModalForm"   novalidate="novalidate" action="${createUrl}">
                            <input style="margin-bottom: 15px;" type="" placeholder="" class="companyId hidden" name="id" value="${transportMap.id}"  >
                            <div class="form-group">
                                <label for="username" class="control-label">皮重</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="毛重" disabled class="companyId form-control input-lg" name="tareWeight" value="${transportMap.tareWeight}"  >


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