<#import "/spring.ftl" as spring>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>榆林煤 注册</title>
    <link href="${rc.contextPath}/components/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style type="text/css">

        html, body {
            font: 14px/1.5 "Microsoft Yahei", "Hiragino Sans GB", Helvetica, "Helvetica Neue", "微软雅黑", Tahoma, Arial, sans-serif;
            color: #14191e;
        }
        .read-avatar {
            border-radius: 15px;
            float: left;
            margin-right: 15px;
            margin-bottom: 15px;
        }


        .box.box-primary {
            border-top-color: #3c8dbc;
        }
        .box {
            position: relative;
            border-radius: 3px;
            background: #ffffff;
            border-top: 3px solid #d2d6de;
            margin-bottom: 20px;
            width: 100%;
            box-shadow: 0 1px 1px rgba(0,0,0,0.1);
        }
        .box-body {
            border-top-left-radius: 0;
            border-top-right-radius: 0;
            border-bottom-right-radius: 3px;
            border-bottom-left-radius: 3px;
            padding: 10px;
        }
        .list-group-unbordered>.list-group-item {
            border-left: 0;
            border-right: 0;
            border-radius: 0;
            padding-left: 0;
            padding-right: 0;
        }
        .profile-user-img {
            margin: 0 auto;
            width: 100px;
            padding: 3px;
            border: 3px solid #d2d6de;
        }

        .entry-footer {
            border-top: 1px solid #ddd;
            padding: 10px;
        }

        .entry-footer .datetime {
            font-size: 12px;
        }
        .text-left {
            text-align: left;
        }


    </style>
    <script type="text/javascript" src="${rc.contextPath}/js/jquery/jquery.js"></script>
    <script src="${rc.contextPath}/components/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>


</head>

<body>


<div class="container" style="margin-bottom:60px; margin-top:20px;max-width:1200px">
    <div class="col-md-5  col-md-offset-3 " id="register-container">
    <ol class="breadcrumb">


        <li class="active">注册    新会员</li>


    </ol>
        <div class="" style="margin-top:5%;">

            <div class="register-box  style="margin-top:25%;">





            <div class="box-body box-profile">
                <p class="login-box-msg"></p>

                <form action="${registerUserUrl}" method="post"  id="myform">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <fieldset id="success_tip" class="" style="">

                        <div class="form-group has-feedback">
                            <input type="text" class="form-control " name="userName" id="userName" placeholder="名称" value="">

                            <span class="glyphicon glyphicon-user form-control-feedback"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <input type="text" class="form-control " name="password" id="password" placeholder="密码" value="">

                            <span class="glyphicon glyphicon-user form-control-feedback"></span>
                        </div>
                        <div class="form-group has-feedback">
                            <input type="text" class="form-control " name="companyName" id="userName" placeholder="供应商" value="">

                            <span class="glyphicon glyphicon-user form-control-feedback"></span>
                        </div>
                        <div class="form-group has-feedback">



                            <span class="glyphicon glyphicon-user form-control-feedback"></span>
                        </div>

                        <div class="form-group has-error" id="msg">
                            <span class="help-block hidden" id="serverMsg">{msg!''}</span>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <div class="">
                                    <label>
                                        <div class="icheckbox_square-blue" aria-checked="false"  aria-disabled="false"  value="agree"></div><input type="checkbox" name="" >
                                        我同意  <a href="#" data-toggle="modal" data-target="#t_and_c_m">条款</a>

                                    </label>
                                </div>
                            </div>



                            <div class="col-xs-6">
                                <button type="button" id="submitBtn" class="btn btn-primary btn-block btn-flat">注册</button>
                            </div>

                            <script  type="text/javascript">



                                $("#submitBtn").click(function() {


                                    alert($('#myform').serialize());
                                    var req = $.ajax({
                                        url:  $('#myform').attr('action'),
                                        type: 'post',
                                        data:  $('#myform').serialize(),
                                    });
                                    req.done(function (data) {
                                        alert("成功:"+data.message);

                                        if (data.status) {
                                            alert("成功:"+data.message);
                                            $('#loading').modal('hide');
                                            window.location.href = data.link;
                                        } else {

                                            alert(data.message);
                                            $('#loading').modal('hide');

                                        }
                                    });
                                });
                            </script>
                        </div>

                    </fieldset>





                </form>


            </div>
        </div>

        <div class="modal fade" id="t_and_c_m" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">Terms & Conditions</h4>
                    </div>
                    <div class="modal-body">
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Similique, itaque, modi, aliquam nostrum at sapiente consequuntur natus odio reiciendis perferendis rem nisi tempore possimus ipsa porro delectus quidem dolorem ad.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">I Agree</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </div>


    <#include "./common/page_foot_section.ftl">

    </div>

</div>

</body>
</html>