<#if status??>

<span id="mqttConnect" class="label <#if status.mqttConnect> label-success<#else>label-danger</#if>"><span id="content">online</span></span>

</#if>
        <span id="establishChanellTryCount" class="label  label-danger "></span>

<script type="text/javascript">
    function status_decision(map) {
        if(map.type=="status"){


            if(map.mqttConnect){
                $("#mqttConnect").removeClass("label-danger");
                $("#mqttConnect").addClass("label-success");
                $("#content").html(map.content);

                if(map.establishChanellTryCount != null){
                    $("#establishChanellTryCount").html(map.establishChanellTryCount);


                }
            }

            if(!map.mqttConnect){
                $("#mqttConnect").removeClass("label-succes");
                $("#mqttConnect").addClass("label-danger");
            }


        }

    }

</script>