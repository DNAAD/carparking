<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
    <h3 class="modal-title" id="myModalLabel">录入皮重   <strong> ${reportDeliveryOrder.license}</strong>         ${product.coalType}/${product.granularity}</h3>

    <strong><a href="">${distributor.name} </a> / <a href="">${distributor.no}</a></strong>
</div>


            <div class="modal-body">
                <div class="">
                    <div class="well">
                        <strong>${reportDeliveryOrder.no}</strong>     <br>

                    ${product.coalType}/${product.granularity}库存：<strong>${inventory.quantityOnHand}</strong>
                        <br>
                        正在装载：<strong>{liveInforInventory.loadingCount!''}</strong>       <br>
                        堆场：<strong>${storageSpace.name}</strong>       <br>

                        分销商：<strong>${distributor.name} / ${distributor.no}</strong>       <br>

                       下单人： <a href="">${reportDeliveryOrder.operatorName}</a>  / <a href="">${reportDeliveryOrder.operatorNo}</a>
                    </div>
                    <form id="createInstanceModalForm"   novalidate="novalidate" action="${createInstanceUrl}">

                    <div class="well">
                        <div class="form-group">
                            <label for="username" class="control-label">费用</label>

                            <select class="selectpicker" data-max-options="2"  id="fee" name="feeUuid" class="form-control" placeholder="特征">
                                    <#if fees??>
                                        <#list fees as feature>
                                            <option value="${feature.uuid}" >${feature.pricingStrategy!''}--${feature.pricingStrategy!''}</option>

                                        </#list>
                                    </#if>
                            </select>

                            <span class="help-block"></span>
                        </div>
                        <strong>${priceCategory.name}</strong>     <strong>${priceCategory.value}</strong>

                    </div>

                    <div class="well">
                            <input style="margin-bottom: 15px;" type="hidden" placeholder="Username" class="uuid " name="uuid" value="${reportDeliveryOrder.uuid}"  >

                            <div class="form-group">
                                <label for="username" class="control-label">皮重</label>
                                <input style="margin-bottom: 15px;" type="" placeholder="TareWeight" class="postalCode form-control" name="tareWeight" value=""  >
                                <span class="help-block"></span>
                            </div>








                    </div>

                    </form>

                </div>




            </div>


            <div class="modal-footer">
                <button id="createInstanceModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>

                <script  type="text/javascript">

                    $("#createInstanceModalFormBtn").click(function() {
                       /* alert($('#createInstanceModalForm').serialize());*/

                        var req = $.ajax({
                            url:  $('#createInstanceModalForm').attr('action'),
                            type: 'POST',
                            data:  $('#createInstanceModalForm').serialize(),
                        });
                        req.done(function (data) {
                            if (data.status) {
                               // alert("成功:"+data.message);
                                $('#instance-table').bootstrapTable('refresh');
                                $('#delivery-table').bootstrapTable('refresh');
                            } else {
                                alert(data.message);
                            }
                        });
                    });
                </script>
            </div>
