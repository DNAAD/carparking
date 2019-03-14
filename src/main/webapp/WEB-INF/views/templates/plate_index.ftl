<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤 搜索</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/css/lightbox/ekko-lightbox.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
<#--
    <script src="${rc.contextPath}/js/lightbox/ekko-lightbox.min.js" type="text/javascript"></script>
-->

   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>


</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:10px ">

    <h1 class="page-header">
        <small>智能识别</small>
    </h1>



    <div class="row "  style="">


        <div class="col-sm-12">

            <div class=" ">

                <ul class="nav nav-tabs " id="" role="tablist">
                    <li class="active"><a href="#companies" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">车牌识别 <span class="badge"></span></a></li>
                    <li class=""><a href="#id" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">身份证识别 <span class="badge"></span></a></li>

                </ul>

            </div>

             <div  class="tab-content" style="padding-top: 10px;padding-bottom: 10px">






                 <div class="tab-pane active" id="companies">
                     <div id="toolbar" class="btn-group" >

                         <button id="viewCapacityBtn" type="button" class="btn btn-success">
                             <i class="">查看详情</i>
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

                     <table class=" table-striped" id="location-table" data-url="${plateUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                                <th data-field="state" data-radio="true"></th>
                                <th data-field="createDate">时间</th>

                                <th data-field="license">车牌</th>

                                <th data-field="direction">方向</th>
                                <th data-field="colourCode">车牌颜色</th>

                                <th data-field="status">状态</th>
                                <th data-field="status" data-formatter="operationInfoFormatter">操作</th>
<#--
                                <th data-field="companyName | senderCompanyName"  data-formatter="companyInfoFormatter">发送者</th>
-->
                            </tr>
                            </thead>
                        </table>

                 </div>

                 <div class="tab-pane " id="id">
                     <div id="id_toolbar" class="" >

                         <button id="viewCapacityBtn" type="button" class="btn btn-success">
                             <i class="">查看详情</i>
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

                     <table class=" table-striped" id="location-table" data-url="${plateUrl}" data-toggle="table" data-classes="table table-hover"   data-method="GET"
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
                            data-toolbar="#id_toolbar">
                         <thead>
                         <tr>
                             <th data-field="state" data-radio="true"></th>
                             <th data-field="createDate">时间</th>

                             <th data-field="license">车牌</th>

                             <th data-field="direction">方向</th>
                             <th data-field="colourCode">车牌颜色</th>

                             <th data-field="status">状态</th>
                             <th data-field="status" data-formatter="operationInfoFormatter">操作</th>

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

                            <form id="addLocationModalForm"   novalidate="novalidate" action="{command_create_url}">
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

                            <form id="addLocationModalForm"   novalidate="novalidate" action="{command_edit_url}">
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


                                <button id="addLocationModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>
                            </form>
                            <script  type="text/javascript">

                                $("#addLocationModalFormBtn").click(function() {
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


<#include "./common/page_foot_section.ftl">

<#include "./common/hotline_section.ftl">



</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

<#--<script>
    $(document).ready(function(){
    });

    $(document).on('click', '[data-toggle="lightbox"]', function(event) {
        event.preventDefault();
        $(this).ekkoLightbox();
    });
</script>-->
<script type="text/javascript">
function originToDestinationInfoFormatter(value, row, index) {



    return '<div class="">'  + row.origin  +
            '<hr style="margin-top: 0px;       margin-bottom: 0px;" >'   +'<span class="pull-right" style="   margin-bottom: 0px; padding-bottom: 0px; ">'+ row.destination  +'</span></div>';

}
function operationInfoFormatter(value, row, index) {

    return '<a href="https://unsplash.it/1200/768.jpg?image=251" data-toggle="lightbox">'+
           // '<img src="https://unsplash.it/600.jpg?image=251" class="img-fluid">'+
            '查看'+
           '</a>'


  //   '<div class="">'  +'<a class="" href="' + row.destination+ '">查看</a></div>';

}

    function queryParams_company(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }
     //   alert(JSON.stringify(sender))
        return params;
    }

    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }
    //    alert(JSON.stringify(sender))
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
        '           <img src="logo.png" alt="Image">'+
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






})
</script>

</html>