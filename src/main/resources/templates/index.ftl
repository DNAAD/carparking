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
    <title>榆林煤 欢迎使用</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script type="text/javascript">


        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    </script>

</head>
<style>
/*    body { background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAaCAYAAACpSkzOAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABZ0RVh0Q3JlYXRpb24gVGltZQAxMC8yOS8xMiKqq3kAAAAcdEVYdFNvZnR3YXJlAEFkb2JlIEZpcmV3b3JrcyBDUzVxteM2AAABHklEQVRIib2Vyw6EIAxFW5idr///Qx9sfG3pLEyJ3tAwi5EmBqRo7vHawiEEERHS6x7MTMxMVv6+z3tPMUYSkfTM/R0fEaG2bbMv+Gc4nZzn+dN4HAcREa3r+hi3bcuu68jLskhVIlW073tWaYlQ9+F9IpqmSfq+fwskhdO/AwmUTJXrOuaRQNeRkOd5lq7rXmS5InmERKoER/QMvUAPlZDHcZRhGN4CSeGY+aHMqgcks5RrHv/eeh455x5KrMq2yHQdibDO6ncG/KZWL7M8xDyS1/MIO0NJqdULLS81X6/X6aR0nqBSJcPeZnlZrzN477NKURn2Nus8sjzmEII0TfMiyxUuxphVWjpJkbx0btUnshRihVv70Bv8ItXq6Asoi/ZiCbU6YgAAAABJRU5ErkJggg==);}*/
    .error-template {padding: 40px 15px;text-align: center;}
    .error-actions {margin-top:15px;margin-bottom:15px;}
    .error-actions .btn { margin-right:10px; }
</style>
<body>
<div class="container" style="margin-bottom:80px; margin-top:10px ">

    <h1 class="page-header">
                <a class=" " href="#"><img class="" src="${rc.contextPath}/logo_header.png" style="width:100px; ;"></a>         <small><strong>${companyName!''}</strong></small>  <small>欢迎使用</small>
        !
    </h1>
    <div class="row">

        <div class="row hidden">
            <div class="col-md-6   col-md-offset-3">
                <h2>Custom search field</h2>
                <div id="custom-search-input">
                    <div class="input-group col-md-12">
                        <input type="text" class="form-control input-lg" placeholder="Buscar" />
                        <span class="input-group-btn">
                        <button class="btn btn-info btn-lg" type="button">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </span>
                    </div>
                </div>
            </div>
        </div>



        <div class="col-sm-12">


            <div class="tab-box ">

                <ul class="nav nav-tabs add-tabs" id="ajaxTabs" role="tablist">
                    <li class="active"><a href="#companies" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">配置信息 <span class="badge"></span></a></li>
                    <li class=""><a href="#configuration" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">绑定信息 <span class="badge"></span></a></li>
                    <li class=""><a href="#auth" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">授权信息 <span class="badge"></span></a></li>

                    <li class=""><a href="#focus" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">屏幕 <span class="badge"></span></a></li>
                    <li class=""><a href="#storage" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">设置默认堆场 <span class="badge"></span></a></li>
                    <li class=""><a href="#info" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">帮助与其他 <span class="badge"></span></a></li>
                    <li class=""><a href="#gateWay" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">gateWay <span class="badge"></span></a></li>


                    <li class=""><a href="#server" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">serverUrl <span class="badge"></span></a></li>

                    <li class=""><a href="#jmdns" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">serverUrl <span class="badge"></span></a></li>


                </ul>
            </div>

            <div  class="tab-content" style="padding-top: 10px;padding-bottom: 10px">


                <div class="tab-pane active" id="companies">

                    <div id="toolbar" class="" >
                        <button id="executeBtn" type="button" class="btn btn-primary" >
                            启动展示屏幕
                        </button>
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


                            $("#executeBtn").click(function() {

                                $.post( "${executeUrl}", { '${_csrf.parameterName}': '${_csrf.token}' }, function( data ) {
                                    console.log( data.name ); // John
                                    console.log( data.time ); // 2pm
                                }, "json");
                            });



                        </script>
                    </div>
                    <table class=" table-striped" id="companies-table" data-url="${infoUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-toolbar="#toolbar">
                        <thead>
                        <tr>
                            <th data-field="name"  >名称</th>
                            <th data-field="url" >值</th>

                        </tr>
                        </thead>
                    </table>


                </div>

                <div class="tab-pane " id="configuration">
                    <div class="col-lg-12">
                        <div><marquee behavior="scroll" direction="left" scrollamount="5">
                            <label class="text-danger" id="qrcodeTitle">煤矿管理者 扫一扫，配置 提煤系统</label>

                        </marquee></div>

                        <script type="text/javascript" src="${rc.contextPath}/js/qrcode.js"></script>
            <#if permanentQrcode??>
                <input id="text" type="hidden" value="${permanentQrcode.content!""}" style="width:80%" />
            <#else>
                <input id="text" type="hidden" value="123" style="width:80%" />
            </#if>

                        <g id="qrcode_div"/>
                        <script type="text/javascript">
                            var qrcode = new QRCode(document.getElementById("qrcode_div"), {
                                width : 200,
                                height : 200,
                                //  useSVG: true
                            });
                            // qrcode.clear(); // clear the code.
                            // qrcode.makeCode("http://naver.com"); // make another code.
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
                    </div>
                </div>


                <div class="tab-pane " id="auth">
                    <div class="container">
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6  ">

                                <form id="setOutProductionModalForm">

                                    <div class="form-group">
                                        <label for="exampleInputPassword1">输入授权码</label>
                                        <input  class="form-control" name="imei" id="imei" placeholder="imei">
                                    </div>
                                    <div class="form-check">
                                        <button class="btn btn-info" ref="${authUrl}" type="button" name="showpassword" id="authBtn" value="Show Password">提交</button>

                                        <script type="text/javascript">




                                            $("#authBtn").click(function() {
                                                $.post($(this).attr('ref'),$('#setOutProductionModalForm').serialize(),function(resultJSONObject){
                                                    if(resultJSONObject.status){
                                                        alert("成功:"+data.message);
                                                        window.location.reload(true);
                                                    }else{
                                                        alert(data.message);

                                                        $.messager.alert("系统提示","添加失败","error");
                                                    }
                                                },"json");
                                            });



                                        </script>

                                    </div>

                                </form>


                            </div>


                        </div>
                    </div>
                </div>

                <div class="tab-pane " id="focus">

                    <div id="focus_toolbar" class="" >
                        <button id="focusBtn" type="button" class="btn btn-primary" >
                            更换屏幕
                        </button>

                        <script type="text/javascript">




                            $("#focusBtn").click(function() {
                                var row =  $('#focus-table').bootstrapTable('getSelections');

                                if(row!= null){
                                    $.post( "${focuseCommandUrl}", { '${_csrf.parameterName}': '${_csrf.token}','uuid': row[0].uuid }, function( data ) {
                                        console.log( data.name ); // John
                                        console.log( data.time ); // 2pm
                                    }, "json");
                                }

                            });



                        </script>
                    </div>

                    <table class=" table-striped" id="focus-table" data-url="${focusUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-toolbar="#focus_toolbar">
                        <thead>
                        <tr>
                            <th data-field="state" data-radio="true"></th>
                            <th data-field="name"  >名称</th>
                            <th data-field="url" >值</th>

                        </tr>
                        </thead>
                    </table>


                </div>
                <div class="tab-pane " id="info">
                    <a href="${configurationUrl}" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-home"></span>
                        进行配置 </a>

                <#--
                                            <a href="http://www.jquery2dotnet.com" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-envelope"></span> 获取帮助 </a>
                -->
                    <div class="error-actions">

                    </div>

                    <div class="error-template">

                    <#--       <h2>
                               404 Not Found</h2>-->
<#--                        <div class="error-details">
                            欢迎首次使用

                            !
                        </div>-->


                    </div>
                    <div class="col-md-12">
                        <div class="span4">
                            <div class="error-details">
                                兰花科技

                            </div>
                            <h4>兰花科技</h4>
                            <address>
                                <strong>榆林市 榆阳区 镇北台</strong><br>
                                汇智协同<br>
                                西216<br>

                                <abbr title="Phone">P:</abbr> 13468801683
                            </address>
                        </div>
                        <div class="error-template">


                        </div>
                    </div>


                </div>
                <div class="tab-pane " id="storage">
                    <table class=" table-striped" id="storage-table" data-url="${storageUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-toolbar="#storage_toolbar">
                        <thead>
                        <tr>
                            <th data-field="state" data-radio="true"></th>

                            <th data-field="name"  >名称</th>
                            <th data-field="url" >值</th>

                        </tr>
                        </thead>
                    </table>


                </div>
                <div class="tab-pane" id="gateWay">
                    <table class=" table-striped" id="companies-table" data-url="${gateWayUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-toolbar="#toolba">
                        <thead>
                        <tr>
                            <th data-field="name"  >名称</th>
                            <th data-field="url" >值</th>

                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="tab-pane" id="server">

                    <div id="service-toolbar">
                    <button id="stopBtn" type="button" class="btn btn-primary" >
                        关闭服务
                    </button>

                    <button id="startBtn" type="button" class="btn btn-danger"  >
                        开启服务
                    </button>
                    </div>
                    <script type="text/javascript">




                        $("#startBtn").click(function() {
                            var row =  $('#server-table').bootstrapTable('getSelections');

                            if (row != '') {
                                $('#startServiceModal').modal();
                                alert(JSON.stringify(row[0]));
                                $('#startServiceModalForm .name').val(row[0].name);

                            }
                        });
                        $("#stopBtn").click(function() {
                            var row =  $('#server-table').bootstrapTable('getSelections');

                            if (row != '') {
                                $('#stopServiceModal').modal();
                                alert(JSON.stringify(row[0]));
                                $('#stopServiceModalForm .name').val(row[0].name);

                            }
                        });




                    </script>

                    <div id="startServiceModal" class="modal fade" role="dialog">
                        <div id="login-overlay" class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                    <h4 class="modal-title" id="myModalLabel">开启服务</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="well">

                                                <form id="startServiceModalForm"   novalidate="novalidate" action="${serviceStartUrl}">
                                                    <input  type="" placeholder="Username" class="name" name="name" value=""  >

                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>




                                                    <div class="form-group">
                                                        <label for="username" class="control-label">描述</label>
                                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                                        <span class="help-block"></span>
                                                    </div>




                                                    <button id="startServiceModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                                                </form>
                                                <script  type="text/javascript">

                                                    $("#startServiceModalFormBtn").click(function() {
                                                        alert($('#startServiceModalForm').serialize());

                                                        var req = $.ajax({
                                                            url:  $('#startServiceModalForm').attr('action'),
                                                            type: 'post',
                                                            data:  $('#startServiceModalForm').serialize(),
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
                    <div id="stopServiceModal" class="modal fade" role="dialog">
                        <div id="login-overlay" class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                    <h4 class="modal-title" id="myModalLabel">关闭服务</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="well">

                                                <form id="stopServiceModalForm"   novalidate="novalidate" action="${serviceStopUrl}">
                                                    <input type="" placeholder="Username" class="name" name="name" value=""  >

                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>




                                                    <div class="form-group">
                                                        <label for="username" class="control-label">描述</label>
                                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                                        <span class="help-block"></span>
                                                    </div>




                                                    <button id="stopServiceModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                                                </form>
                                                <script  type="text/javascript">

                                                    $("#stopServiceModalFormBtn").click(function() {
                                                        alert($('#stopServiceModalForm').serialize());

                                                        var req = $.ajax({
                                                            url:  $('#stopServiceModalForm').attr('action'),
                                                            type: 'post',
                                                            data:  $('#stopServiceModalForm').serialize(),
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

                    <table class=" table-striped" id="server-table" data-url="${serverUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-toolbar="#service-toolbar">
                        <thead>
                        <tr>

                            <th data-field="state" data-radio="true"></th>

                            <th data-field="name"  >名称</th>
                            <th data-field="url" >值</th>

                        </tr>
                        </thead>
                    </table>
                </div>

                <div class="tab-pane" id="jmdns">

                    <div id="jmdns-toolbar">
                        <button id="stopBtn" type="button" class="btn btn-primary" >
                            关闭服务
                        </button>

                        <button id="startBtn" type="button" class="btn btn-danger"  >
                            开启服务
                        </button>
                    </div>
                    <script type="text/javascript">




                        $("#startBtn").click(function() {
                            var row =  $('#server-table').bootstrapTable('getSelections');

                            if (row != '') {
                                $('#startServiceModal').modal();
                                alert(JSON.stringify(row[0]));
                                $('#startServiceModalForm .name').val(row[0].name);

                            }
                        });
                        $("#stopBtn").click(function() {
                            var row =  $('#server-table').bootstrapTable('getSelections');

                            if (row != '') {
                                $('#stopServiceModal').modal();
                                alert(JSON.stringify(row[0]));
                                $('#stopServiceModalForm .name').val(row[0].name);

                            }
                        });




                    </script>

                    <div id="startServiceModal" class="modal fade" role="dialog">
                        <div id="login-overlay" class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                    <h4 class="modal-title" id="myModalLabel">开启服务</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="well">

                                                <form id="startServiceModalForm"   novalidate="novalidate" action="${jmdnsUrl}">
                                                    <input  type="" placeholder="Username" class="name" name="name" value=""  >

                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>




                                                    <div class="form-group">
                                                        <label for="username" class="control-label">描述</label>
                                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                                        <span class="help-block"></span>
                                                    </div>




                                                    <button id="startServiceModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                                                </form>
                                                <script  type="text/javascript">

                                                    $("#startServiceModalFormBtn").click(function() {
                                                        alert($('#startServiceModalForm').serialize());

                                                        var req = $.ajax({
                                                            url:  $('#startServiceModalForm').attr('action'),
                                                            type: 'post',
                                                            data:  $('#startServiceModalForm').serialize(),
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
                    <div id="stopServiceModal" class="modal fade" role="dialog">
                        <div id="login-overlay" class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                                    <h4 class="modal-title" id="myModalLabel">关闭服务</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <div class="well">

                                                <form id="stopServiceModalForm"   novalidate="novalidate" action="${serviceStopUrl}">
                                                    <input type="" placeholder="Username" class="name" name="name" value=""  >

                                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>




                                                    <div class="form-group">
                                                        <label for="username" class="control-label">描述</label>
                                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
                                                        <span class="help-block"></span>
                                                    </div>




                                                    <button id="stopServiceModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                                                </form>
                                                <script  type="text/javascript">

                                                    $("#stopServiceModalFormBtn").click(function() {
                                                        alert($('#stopServiceModalForm').serialize());

                                                        var req = $.ajax({
                                                            url:  $('#stopServiceModalForm').attr('action'),
                                                            type: 'post',
                                                            data:  $('#stopServiceModalForm').serialize(),
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

                    <table class=" table-striped" id="jmdns-table" data-url="${jmdnsUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                           data-toolbar="#jmdns-toolbar">
                        <thead>
                        <tr>

                            <th data-field="state" data-radio="true"></th>

                            <th data-field="name"  >名称</th>
                            <th data-field="name"  >名称</th>

                            <th data-field="url" >值</th>

                        </tr>
                        </thead>
                    </table>
                </div>




            </div>


        </div>

    </div>




</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

<script type="text/javascript">


    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }
        alert(JSON.stringify(sender))
        return params;
    }

    function handleResponse(original) {
        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;
       // alert(JSON.stringify(original));
        return res;
    }


    function refresh() {
        $('#companies-table').bootstrapTable('refresh');
    }




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

                console.log("------"+JSON.parse(greeting.body))
                console.log("------"+JSON.stringify(greeting.body));
                // alert(JSON.stringify(greeting.body));
                var map = JSON.parse(greeting.body);
                if(map.type=="Qrcode"){

                    $("#qrcodeTitle").html(map.qrcodeType);
                    event_default(map)

                }
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



    function event_default(map) {
        $("#qrcodeTitle").html(map.title);

        //   $('#inventory-table').bootstrapTable('refresh');
        //   $('#event-table').bootstrapTable('refresh');
        qrcode.clear(); // clear the code.
        qrcode.makeCode(map.content); // make another code.

    }

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


    }




</script>


</html>