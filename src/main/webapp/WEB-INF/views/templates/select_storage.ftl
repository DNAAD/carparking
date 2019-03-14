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
    <title>榆林煤 选择堆场</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>
    <script type="text/javascript">


        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $(document).ajaxSend(function(e, xhr, options) {
            xhr.setRequestHeader(header, token);
        });
    </script>



</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:10px ">

<h1 class="page-header">
    <small>选择煤矿堆场</small>
</h1>



    <div class="row "  style="">


        <div class="col-sm-12">

            <div class="tab-box ">

                <ul class="nav nav-tabs add-tabs" id="ajaxTabs" role="tablist">
                    <li class="active"><a href="#frequency" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">经常使用的堆场 <span class="badge"></span></a></li>


                </ul>

            </div>



             <div  class="tab-content">

                    <div class="tab-pane active" id="frequency" style="padding-top: 10px">
                        <div class="row">
                            <div class="col-md-12" >
                                <div id="frequency_toolbar" class="" >
                                    <button id="addStorageBtn" type="button" class="btn btn-primary" data-dismiss="modal">设置为默认堆场</button>
                                    <script type="text/javascript">

                                        $("#addStorageBtn").click(function() {
                                            var row =  $('#storage-table').bootstrapTable('getSelections');

                                            if (row != '') {
                                                $('#publishCapacityModal').modal();
                                                $('#company').val(row[0].name +" "+
                                                        row[0].no+" ");


                                           //     alert(row[0].uuid);

                                                var req = $.ajax({
                                                    url: "${changeDefaultStorageUrl}",
                                                    type: 'post',
                                                    data: {uuid:row[0].uuid}
                                                });
                                                req.done(function (data) {

                                               //     alert(JSON.stringify(data));

                                                    if (data.status) {
                                                        window.location.href = data.link;
                                                    } else {
                                                    //    alert(data.message);
                                                    }
                                                });


                                            }
                                        });



                                    </script>


                                </div>
                                <table class="table table-list-search" id="storage-table" data-url="${storageSearchUrl}"  data-toggle="table" data-classes="table table-hover"   data-method="GET"
                                       data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                                       data-query-params-type="unlimit"
                                       data-query-params="storage_queryParams"
                                       data-response-handler="storage_handleResponse"
                                       data-pagination="true"
                                       data-side-pagination="server"
                                       data-page-number="1"
                                       data-page-list="[10]"
                                       data-page-size="10"
                                       data-click-to-select="true"
                                       data-single-select="true"
                                       data-formatter="stateFormatter"
                                       data-search="true"
                                       data-show-refresh="true"
                                       data-toolbar="#frequency_toolbar"
                                        >
                                    <thead>
                                    <tr>
                                        <th data-field="state" data-radio="true"></th>

                                        <th data-field="no" >堆场编号</th>

                                        <th data-field="name">名称</th>

                                        <th data-field="deliveryRegionalAddress">负责人</th>
                                        <th data-field="deliveryDetailedAddress">备注</th>
                                        <th data-field="status">状态</th>

                                    </tr>
                                    </thead>

                                </table>
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
                <h4 class="modal-title" id="myModalLabel">添加区域</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="well">

                            <form id="editLineModalForm"   novalidate="novalidate" action="{command_edit_url}">
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


                                <button id="editLineModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#editLineModalFormBtn").click(function() {
                                //    alert($('#editLineModalForm').serialize());

                                    var req = $.ajax({
                                        url:  $('#editLineModalForm').attr('action'),
                                        type: 'put',
                                        data:  $('#editLineModalForm').serialize(),
                                    });
                                    req.done(function (data) {
                                        if (data.status) {
                                      //      alert("成功:"+data.message);
                                            window.location.reload(true);
                                        } else {
                                         //   alert(data.message);
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





</body>


<script type="text/javascript">

    $('#storage_company_id.selectpicker').on('change', function(){
        var selected = $(this).find("option:selected").val();
        storage_refresh();
    });



    function storage_queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;
        var status = $.trim($("#company_table_status").val());
        var name = $.trim($("#name").val());
        //var companyType = $.trim($("#company_table_companyType").val());

        var storage_company_id = $.trim($("#storage_company_id").val());
        if (storage_company_id) {
            params.producerId = storage_company_id;
        }
        if (status) {
            params.status = status;
        }
        if (name) {
            params.name = name;
        }


        //alert(JSON.stringify(params));


        return params;
    }

    function storage_handleResponse(original) {


        var res = {};
        res.rows = original.content;
        res.total = original.totalElements;
        return res;
    }

    function storage_refresh() {

        $('#storage-table').bootstrapTable('refresh');
    }



    $("#addCompanyBtn").bind('click', function(e){

        var row =  $('#storage-table').bootstrapTable('getSelections');
        var ids = $.map(row, function (item) {
            return item.id;
        });

        //     alert(JSON.stringify(row));

        var req = $.ajax({
            url: '${changeDefaultStorageUrl}',
            type: 'post',
            data: {storageId:row[0].id,${_csrf.parameterName}:'${_csrf.token}'}
    });
        req.done(function (data) {

            alert(JSON.stringify(data));

            if (data.status) {
                window.location.href = data.link;
            } else {
                alert(data.message);
            }
        });


    });






</script>
</html>