<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤 用户登录</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">


</head>
<body>
<div class="container" style="margin-bottom:60px; margin-top:20px;max-width:1200px">

    <h1 class="page-header">
        <small>  <img class="" src="/logo_header.png" style="width:150px; ;">    <strong><a href="#">${companyName!''}</a></strong> 提煤系统登录 </small>
    </h1>
    <div class=" col-md-5  col-md-offset-3 " id="register-container">

    <div class="row col-lg-12"  id="">
        <div class="" >


            <div class="box-body box-profile " >



                <form method="post" id="login_form" action="${rc.contextPath}/login" role="login">

                    <fieldset>
                       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <input type="" name="username" id="phone" placeholder="请输入邮箱"
                                   class="form-control "/>
                        </div>
                        <div class="form-group">
                            <input type="password" name="password" class="form-control " id="password"
                                   placeholder="请输入密码"/>
                        </div>

                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-6">
                                    记住我: <input type="checkbox" name="remember-me" />
                                </div>

                            </div>
                        </div>


                       <#if error??>
                       error
                           <div class="form-group has-error" id="msg">
                               <span class="help-block" id="serverMsg">${msg!''}</span>
                           </div>
                       </#if>





                        <div class="form-group">
                            <button type="submit" id="submit" class="btn  btn-primary btn-block">登录
                            </button>
                        </div>

                    </fieldset>
                </form>


            </div>

        </div>

    </div>


<#--        <div class="col-lg-12">
        <#include "./common/page_foot_section.ftl">

        </div>-->
        </div>
</div>


<script type="text/javascript">
    var    codeLength = 4;


    $("#captcha_login").keyup(function () {
        var code_len = $("#captcha_login").val().trim().length
        if (code_len < codeLength) {
            $("#valid_failed").text("");
        } else if (code_len > codeLength) {
            $("#valid_failed").text("验证码错误");
            $("#getSMSCodeBtn").attr("disabled", true);
        } else {
            //  alert("测试含税"+verify_code());

            var req = $.ajax({
                type: 'get',
                url: '{do_verify_url}',
                data: {checkCode: $('#captcha_login').val()}
            });
            req.done(function (data) {
                if (data) {
                    //alert("验证成功");
                    $("#valid_failed").text("验证成功,已发送短信");
                    $("#getSMSCodeBtn").attr("disabled", false);
                 //   sendSMS();

                } else {
                    //alert("验证不成功");
                    $("#valid_failed").text("验证码错误");
                }
            });

        }
    });



</script>


</body>
</html>