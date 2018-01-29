<#--<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />-->

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>个人中心 提货单 </title>

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
<#--<div class="col-lg-6">
<div id="toolbar" class="btn-group" style="padding-top: 10px;padding-bottom: 10px">

&lt;#&ndash;
    <button id="viewCapacityBtn" type="button" class="btn btn-success">
        <i class="">查看详情</i>
    </button>
    <button id="deleteBtn_" type="button" class="btn btn-primary" >
        添加
    </button>
&ndash;&gt;


&lt;#&ndash;    <button id="editBtn" type="button" class="btn btn-primary" >
        录入毛重
    </button>&ndash;&gt;




</div>

</div>-->

<#if deliveryOrderMap??>
<div class="col-lg-6">



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

                <#if deliveryOrderMap.status =="Valid">

                    有效
                <#else>
                    无效
                </#if>
            </td>
        </tr>

            <#if deliveryOrderMap.status =="Valid">
            <tr>
                <td class="active">提货密码</td>
                <td colspan="3"><strong>${deliveryOrderMap.accessCode!''}</strong></td>
            </tr>
            </#if>
        <tr>
            <td class="active">贸易商</td>
            <td colspan="3"><a href="${deliveryOrderMap.distributorUrl!''}">${deliveryOrderMap.companyName!''}</a></td>
        </tr>


        <tr>
            <td class="active">库存</td>
            <td colspan="3"><a href="${deliveryOrderMap.distributorUrl!''}">${deliveryOrderMap.inventory!''}</a></td>
        </tr>


        <tr>
            <td class="active">可用余额</td>
            <td colspan="3"><a href="${deliveryOrderMap.inventoryUrl!''}">${deliveryOrderMap.advancedPaymentAmount!''}</a></td>
        </tr>

        <tr>
            <td class="active">货物：</td>
            <td><a href="${deliveryOrderMap.productUrl!''}" > ${deliveryOrderMap.productName!''}</a></td>
        </tr>




<#--

        <tr>
            <th>堆场情况</th>
            <th></th>
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
        -->

        <tr>
            <th>车辆信息</th>
            <th></th>
        </tr>




        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                车牌号
            </td>
            <td>${deliveryOrderMap.plateNumber!''}</td>
        </tr>
<#--        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1">
                载重
            </td>
            <td>${deliveryOrderMap.carryingCapacity!''}</td>
        </tr>-->

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                类型
            </td>
            <td>${deliveryOrderMap.vehicleType!'未定义'}</td>
        </tr>





        <tr>
            <th>司机信息</th>
            <th></th>
        </tr>




        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                名字
            </td>
            <td><a href=""> ${deliveryOrderMap.carrierName!'---'}</a></td>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                电话
            </td>
            <td><a href="tel:${deliveryOrderMap.carrierPhone!''}">${deliveryOrderMap.carrierPhone!''}</a>
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
            <td> ${deliveryOrderMap.operationStatus!'---'}</td>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                入场时间
            </td>
            <td>${deliveryOrderMap.boundTime!'--'}
            </td>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                离场时间
            </td>
            <td>${deliveryOrderMap.outboundTime!'--'}
            </td>
        </tr>

        <tr id="tr-id-1" class="tr-class-1">
            <td id="td-id-1" class="td-class-1 success">
                皮重/净重
            </td>
            <td>${deliveryOrderMap.tareWeight!'--'}/${deliveryOrderMap.netWeight!'--'}
            </td>
        </tr>

        </tbody>
    </table>



</div>


<label>扫一扫微信获得信息 </label>

<div class="thumbnail">
    <img src="${qrcodeUrl!'/logo.png'}">
</div>

    <#if verification??>

    <table  class="table table-condensed">
        <thead>
        <tr>
            <th>验证信息</th>
            <th></th>


        </tr>
        </thead>
        <tbody>
        <tr  >
            <td class="active">揽货验证密码：</td>
            <td >    <input type="text" id="demandNum" name="demandNum"  placeholder="测试-揽货密码" value="${verification.code}"/></td>
        </tr>
        <tr>
            <td class="active">验证二维码</td>
            <td>


                <script type="text/javascript" src="/qrcode.js"></script>
                <input id="text" type="text" value="${qrcodeContent!''}" style="width:80%" />
                <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                    <g id="qrcode_div"/>
                </svg>
                <script type="text/javascript">
                    var qrcode = new QRCode(document.getElementById("qrcode_div"), {
                        width : 100,
                        height : 100,
                        useSVG: true
                    });

                    function makeCode () {
                        var elText = document.getElementById("text");

                        /*                           if (!elText.value) {
                                                       alert("Input a text");
                                                       elText.focus();
                                                       return;
                                                   }*/

                        qrcode.makeCode(elText.value);
                    }

                    makeCode();

                    $("#text").
                            on("blur", function () {
                                makeCode();
                            }).
                            on("keydown", function (e) {
                                if (e.keyCode == 13) {
                                    makeCode();
                                }
                            });
                </script>
            </td>
        </tr>
        </tbody>
    </table>
    </#if>

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
<div class="col-lg-6">
    <button id="addBtn" type="button" class="btn btn-primary btn-lg hidden"  data-toggle="modal" data-target="#addLineModal">
        录入皮重
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
    <form id="addLineModalForm"   novalidate="novalidate" action="${createUrl}">
        <input style="margin-bottom: 15px;" type="" placeholder="" class="companyId hidden" name="id" value="${deliveryOrderMap.id}"  >

        <div class="form-group">
            <label for="username" class="control-label">基本信息</label>
            <input style="margin-bottom: 15px;" type="" placeholder="皮重" disabled class="companyId form-control input-lg" name="tareWeight" value="${deliveryOrderMap.plateNumber!''} / ${deliveryOrderMap.productName!''}"  >


            <span class="help-block"></span>
        </div>




        <div class="form-group">
            <label for="username" class="control-label">皮重</label>
            <input style="margin-bottom: 15px;" type="" placeholder="皮重" class="companyId form-control input-lg" name="tareWeight" value=""  >


            <span class="help-block"></span>
        </div>


    <#--                          <div class="form-group">
                                  <label for="username" class="control-label">净重</label>
                                  <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                  <span class="help-block"></span>
                              </div>
-->

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
<div id="addLineModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">输入皮重</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                            <form id="addLineModalForm"   novalidate="novalidate" action="${createUrl}">
                                <input style="margin-bottom: 15px;" type="" placeholder="" class="companyId hidden" name="id" value="${deliveryOrderMap.id}"  >

                                <div class="form-group">
                                    <label for="username" class="control-label">基本信息</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="皮重" disabled class="companyId form-control input-lg" name="tareWeight" value="${deliveryOrderMap.plateNumber!''} / ${deliveryOrderMap.productName!''}"  >


                                    <span class="help-block"></span>
                                </div>




                                <div class="form-group">
                                    <label for="username" class="control-label">皮重</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="皮重" class="companyId form-control input-lg" name="tareWeight" value=""  >


                                    <span class="help-block"></span>
                                </div>


      <#--                          <div class="form-group">
                                    <label for="username" class="control-label">净重</label>
                                    <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                    <span class="help-block"></span>
                                </div>
-->

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