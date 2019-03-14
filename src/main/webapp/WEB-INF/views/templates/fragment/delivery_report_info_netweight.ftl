<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
    <h3 class="modal-title" id="myModalLabel">出库录入净重   <strong> ${reportDeliveryOrder.license}</strong>         ${product.coalType}/${product.granularity}</h3>

    <strong><a href="">${distributor.name} </a> / <a href="">${distributor.no}</a></strong>
</div>
            <div class="modal-body">
                <div class="">
                    <div class="well">
                        皮重：<strong>${instanceTransport.tareWeight}</strong> 吨    <br>
                        入场时间：<strong>${instanceTransport.createDate}</strong>     <br>

                        <strong>${reportDeliveryOrder.no}</strong>     <br>

                    ${product.coalType}/${product.granularity}库存：<strong>${inventory.quantityOnHand!''}</strong>
                        <br><#if liveInforInventory??>
                                                    正在装载：<strong>${liveInforInventory.loadingCount!''}</strong>       <br>

                    </#if>
                        堆场：<strong>${storageSpace.name}</strong>       <br>

                        分销商：<strong>${distributor.name} / ${distributor.no}</strong>       <br>

                        下单人： <a href="">${reportDeliveryOrder.operatorName}</a>  / <a href="">${reportDeliveryOrder.operatorNo}</a>
                    </div>


                    <form id="inpurtNetweightModalForm"   novalidate="novalidate" action="${createTransferUrl}">
                    <div class="well">
                        <div class="form-group">
                            <label for="username" class="control-label">费用</label>

                            <select class="selectpicker" data-max-options="2"  id="fee" name="feeUuid" class="form-control" placeholder="特征">
                                    <#if fees??>
                                        <#list fees as feature>
                                            <option value="${feature.uuid}" >${feature.pricingStrategy!''}--${feature.type!''} --${feature.tax!''} </option>

                                        </#list>
                                    </#if>
                            </select>

                            <span class="help-block"></span>
                        </div>

                                                                <#list priceCategories as priceCategory>
                        <strong>${priceCategory.name}</strong>     <strong>${priceCategory.value}</strong>

                                                                </#list>

                    </div>

                    <div class="well">
                            <input style="margin-bottom: 15px;" type="hidden" placeholder="Username" class="uuid " name="uuid" value="${instanceTransport.uuid}"  >


                        <div class="form-group">
                            <label for="username" class="control-label">净重</label>
                            <input style="margin-bottom: 15px;" type="" placeholder="netWeight" class="postalCode form-control" name="netWeight" value=""  >
                            <span class="help-block"></span>
                        </div>







                    </div>

                    </form>

                </div>




            </div>


            <div class="modal-footer">
                <button id="inpurtNetweightModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>

                <script  type="text/javascript">

                    $("#inpurtNetweightModalFormBtn").click(function() {


                        /*
                                                    $.ajaxSetup({
                                                        error: function(event, request, options, error) {
                                                            switch (event.status) {
                                                                case 401: common.setLocation('/sign-in'); break;
                                                                ...
                                                            }
                                                        }
                                                    });*/

                        alert($('#inpurtNetweightModalForm').serialize());


                        $.ajax({
                            type: "POST",
                            url: $('#inpurtNetweightModalForm').attr('action'),
                            data: $('#inpurtNetweightModalForm').serialize(),
                            cache: false,
                            async: false,
                            success: function(result) {
                                alert("登录返回"+JSON.stringify(result));

                                if (result.status) {
                                    alert("成功:"+result.message);
                                    $('#instance-table').bootstrapTable('refresh');
                                    $('#transfer_no_reconciliation_table').bootstrapTable('refresh');


                                    /*
                                                                        window.location.reload(true);
                                    */
                                } else {
                                    alert("登录错误啊啊");

                                    alert(result.message);
                                };
                            },


  /*                          error: function(httpObj, textStatus) {
                                if(httpObj.status==401)
                                    alert("没有授权，请登录后操作");


                                $('#empModal .modal-body').load('{fragmentUrl}',function(result){
                                    $('#empModal').modal({show:true});
                                });

                            },*/
                        });

                        /*
                                var req = $.ajax({
                                    url:
                                    type: 'post',
                                    data:
                                });
                                req.done(function (data) {
                                    if (data.status) {
                                        alert("成功:"+data.message);
                                        $('#instance-table').bootstrapTable('refresh');
                                        $('#transfer_no_reconciliation_table').bootstrapTable('refresh');


    /!*
                                        window.location.reload(true);
    *!/
                                    } else {
                                        alert(data.message);
                                    };
                                    success: function(result) {
                                        if (!$.trim(result)==='error') {
                                            // remove #ratelinks element to prevent another rate
                                            $("#rate").remove();
                                            // get rating after click
                                            getRating();
                                            getRatingAvg();
                                            getRatingText2();
                                            getRatingText();
                                        } else {
                                            // not login, do something...
                                            alert('login first please...');
                                        }
                                    },
                                });*/
                    });
                </script>
            </div>
