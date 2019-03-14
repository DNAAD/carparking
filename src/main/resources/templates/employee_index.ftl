<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤 员工管理</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet">
    <link href="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.css" rel="stylesheet">

    <script src="${rc.contextPath}/js/jquery/jquery.js" type="text/javascript"></script>
   <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>




</head>

<body>
<div class="container" style="margin-bottom:80px; margin-top:10px ">
    <h1 class="page-header">
        <small>员工管理</small>
    </h1>




    <div class="row "  style="">


        <div class="col-sm-12">

            <div class=" ">

                <ul class="nav nav-tabs add-tabs" id="ajaxTabs" role="tablist">
                    <li class="active"><a href="#companies" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">员工 <span class="badge"></span></a></li>

                    <li class=""><a href="#addCompanies" data-url="ajax/2.html" role="tab" data-toggle="tab" aria-expanded="false">添加员工 <span class="badge"></span></a></li>

                </ul>
            </div>
             <div  class="tab-content" style="padding-top: 5px">


                    <div class="tab-pane active" id="companies">

                        <div id="employee_toolbar" class="hidden" >

                            <button id="viewCapacityBtn" type="button" class="btn btn-success">
                                <i class="">查看详情</i>
                            </button>
<#--                            <button id="deleteBtn_" type="button" class="btn btn-primary" >
                                添加
                            </button>


                            <button id="editBtn" type="button" class="btn btn-primary" >
                                修改
                            </button>

                            <button id="addBtn" type="button" class="btn btn-primary"  data-toggle="modal" data-target="#addLineModal">
                                添加
                            </button>-->

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
                               data-toolbar="#employee_toolbar"
                               data-search="true"
                               data-show-refresh="true">
                            <thead>
                            <tr>



                                <th data-field="no">名称</th>

                                <th data-field="givenName">名</th>
                                <th data-field="familyName">姓</th>

                                <th data-field="realName">姓名</th>
                                <th data-field="gender">性别</th>
                                <th data-field="mobile">电话</th>

                                <th data-field="status">状态</th>

                                <th data-field="description">描述</th>


<#--
                                <th data-field="companyName | senderCompanyName"  data-formatter="companyInfoFormatter">发送者</th>
-->
                            </tr>
                            </thead>
                        </table>

                 </div>



                 <div class="tab-pane " id="addCompanies">



                 </div>
             </div>



        </div>




    </div>


<div id="addLineModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">添加员工</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form id="addLineModalForm"   novalidate="novalidate" action="${command_create_url}">

                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                            <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId hidden" name="companyId" value=""  >








                            <div class="form-group">
                                <label for="username" class="control-label">名称</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="lineName" value=""  >


                                <span class="help-block"></span>
                            </div>


                            <div class="form-group">
                                <label for="username" class="control-label">描述</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="Username" class="companyId form-control" name="description" value=""  >
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

<div id="editLineModal" class="modal fade" role="dialog">
    <div id="login-overlay" class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">添加员工</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-12">
                        <form id="editLineModalForm"   novalidate="novalidate" action="${command_edit_url}">
                            <input style="margin-bottom: 15px;" type="" placeholder="Username" class="id hidden" name="id" value=""  >
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

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
                                alert($('#editLineModalForm').serialize());

                                var req = $.ajax({
                                    url:  $('#editLineModalForm').attr('action'),
                                    type: 'put',
                                    data:  $('#editLineModalForm').serialize(),
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

<#--<#include "../common/page_foot_section.ftl">

<#include "../common/hotline_section.ftl">-->




</body>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap_table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/components/bootstrap-select/js/bootstrap-select.min.js"></script>

<script>
    $(document).ready(function(){
    });
</script>
<script type="text/javascript">
function originToDestinationInfoFormatter(value, row, index) {



    return '<div class="">'  + row.origin  +
            '<hr style="margin-top: 0px;       margin-bottom: 0px;" >'   +'<span class="pull-right" style="   margin-bottom: 0px; padding-bottom: 0px; ">'+ row.destination  +'</span></div>';

}
    function queryParams_company(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }
      //  alert(JSON.stringify(sender))
        return params;
    }

    function queryParams(params) {
        params.page = params.pageNumber - 1;
        params.size = params.pageSize;

        var sender = $.trim($("#search_param").val());

        if (sender) {
            params.q = sender;
        }
        //alert(JSON.stringify(sender))
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

  /*  function companyInfoFormatter(value, row, index) {



        var inventories = "";
        $.each(row.inventories, function (n, value) {
            //  alert(n + ' ' + value);
            var trs = "";
            trs += "<a href='"+value.storageUrl+"'>" + value.storageName + "</a> 存 " + value.quantityOnHand + " 吨 ";
            inventories += trs;
        });

        var indicators = "";
        $.each(row.indicators, function (n, value) {
            //  alert(n + ' ' + value);
            var trs = "";
            trs += "" + value.symbol + " " + value.value + "  "+value.unit;
            indicators += trs;
        });

        return "    <div class='col-sm-3 no-padding photobox'>"+
        '                <div class=" carousel-row">'+
                ' <div class=" slide-row">'+
                ' <div id="carousel-1" class="carousel slide slide-carousel" data-ride="carousel">'+
                '  <!-- Indicators -->'+
        '  <ol class="carousel-indicators">'+
        '  <li data-target="#carousel-1" data-slide-to="0" class="active"></li>'+
        '  <li data-target="#carousel-1" data-slide-to="1"></li>'+
        '  <li data-target="#carousel-1" data-slide-to="2"></li>'+
        '  </ol>'+

        '      <!-- Wrapper for slides -->'+
        '   <div class="carousel-inner">'+
        '          <div class="item active">'+
        '           <img src="'+row.companyLogoUrl+'" alt="Image">'+
        '             </div>'+

        '  </div>'+
        '   </div>'+
        '    <div class="slide-content">'+
        '          <h4><a href=" '+ row.companyUrl + '">'+ row.companyName+'<a></h4>'+

        '  </div>'+
        '  </div>'+

        '    </div>'+
        '    </div>'+

        '    </div>'+

            //row.companyDesc+


    '<div class="col-sm-7 add-desc-box">'+
               ' <div class="add-details">'+
               ' <h4 class="add-title"> <a href="'+row.url+'"> '+ row.granularity+' </a> <small>'+  row.companyAddress+'</small> </h4>'+
    ' <span class="info-row"> <span class="add-type business-ads tooltipHere" data-toggle="tooltip" data-placement="right" title="Business Ads"> </span> <span class="date"><i class=" icon-clock"> </i> '+row.updateTime+' </span>  <span class="category"> </span> <span class="item-location"><i class="fa fa-map-marker"> '+   row.companyAddress+'</i>  </span> </span> </div>'+
       '<hr>'+
        inventories+'<br>'+indicators+'<br>'+


       ' </div>'+
        '  <div class="slide-footer hidden">'+
        '        <span class="pull-right buttons">'+
        '         <button class="btn btn-sm btn-default"><i class="fa fa-fw fa-eye"></i> Show</button>'+
        '    <button class="btn btn-sm btn-primary"><i class="fa fa-fw fa-shopping-cart"></i> Buy</button>'+
        '    </span>'+
        '    </div>';

    }

*/
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