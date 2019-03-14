<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">更正   </h4>                          <p><h2> ${reportDeliveryOrder.license}    <a href="">${reportDeliveryOrder.productName}</a>     <small><a href="">${reportDeliveryOrder.companyName}</a> / <a href="">${reportDeliveryOrder.distributorNo}</a></small></h2> </p>

</div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="well">
                                <strong>${reportDeliveryOrder.no}</strong>

                                <p><a href="">${reportDeliveryOrder.productNo}</a> / <a href="">${reportDeliveryOrder.storageNo}</a> | <a href="">${reportDeliveryOrder.operatorName}</a>  / <a href="">${reportDeliveryOrder.operatorNo}</a></p>




                            </div>
                            <div class="well">
                                <div class="form-group">
                                    <label for="username" class="control-label">方案</label>
                                    <select class="selectpicker" data-max-options="2"  id="fee" name="feature" class="form-control" placeholder="特征">
                                    </select>
                                    <span class="help-block"></span>
                                </div>
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

                                <form id="correctModalForm"   novalidate="novalidate" action="${command_correct_url}">

                                    <div class="form-group">
                                        <label for="username" class="control-label">名称</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="name form-control" name="name" value=""  >
                                        <span class="help-block"></span>
                                    </div>

                                    <div class="form-group">
                                        <label for="username" class="control-label">单价</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="postalCode form-control" name="unitPrice" value=""  >
                                        <span class="help-block"></span>
                                    </div>
                                    <div class="form-group">
                                        <label for="username" class="control-label">净重</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="postalCode form-control" name="weight" value=""  >
                                        <span class="help-block"></span>
                                    </div>


                                    <div class="form-group">
                                        <label for="username" class="control-label">总额</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="postalCode form-control" name="amount" value=""  >
                                        <span class="help-block"></span>
                                    </div>
                                    <div class="form-group">
                                        <label for="username" class="control-label">开票总额</label>
                                        <input style="margin-bottom: 15px;" type="" placeholder="Username" class="postalCode form-control" name="taxAmount" value=""  >
                                        <span class="help-block"></span>
                                    </div>





                                </form>






                            </div>
                        </div>
                    </div>




                </div>


            <div class="modal-footer">


                <button id="correctModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>

                <script  type="text/javascript">

                    $("#correctModalFormBtn").click(function() {
                        alert($('#correctModalForm').serialize());

                        var req = $.ajax({
                            url:  $('#correctModalForm').attr('action'),
                            type: 'post',
                            /*           contentType: "application/json",*/
                            data:  $('#correctModalForm').serialize(),
                        });
                        req.done(function (data) {
                            if (data.status) {
                                $('#transfer_no_reconciliation_table').bootstrapTable('refresh');

                            } else {
                                alert(data.message);
                            }
                        });
                    });
                </script>
            </div>







<#--
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title" id="myModalLabel">录入皮重   </h4>                          <p><h2> ${reportDeliveryOrder.license}    <a href="">${reportDeliveryOrder.productName}</a>     <small><a href="">${reportDeliveryOrder.companyName}</a> / <a href="">${reportDeliveryOrder.distributorNo}</a></small></h2> </p>

</div>
            <div class="modal-body">
                <div class="">

                    <form id="createInstanceModalForm"   novalidate="novalidate" action="${createInstanceUrl}">


                    </form>

                </div>




            </div>


            <div class="modal-footer">
                <button id="createInstanceModalFormBtn"  type="buttom" data-dismiss="modal"   class="btn btn-primary ">确定</button>

                <script  type="text/javascript">

                    $("#createInstanceModalFormBtn").click(function() {
                        alert($('#createInstanceModalForm').serialize());

                        var req = $.ajax({
                            url:  $('#createInstanceModalForm').attr('action'),
                            type: 'POST',
                            data:  $('#createInstanceModalForm').serialize(),
                        });
                        req.done(function (data) {
                            if (data.status) {
                                alert("成功:"+data.message);
                                $('#instance-table').bootstrapTable('refresh');
                                $('#delivery-table').bootstrapTable('refresh');
                            } else {
                                alert(data.message);
                            }
                        });
                    });
                </script>
            </div>
-->
