<#--<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />-->
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <button type="button " class="navbar-toggle btn-primary" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

        <a class="navbar-brand " href="${welcomeUrl}"><img class="" src="${rc.contextPath}/logo_header.png" style="width:100px; ;"></a>

        <a class="navbar-brand " href="${synthesizedUrl}"> <strong class="h3">${companyName!''}</strong> </a>
 <#--       <span id="mqttConnect" class="label label-danger">status</span>-->








     <#include "../tip/status.ftl">
    </div>
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <ul class="nav navbar-nav">


                                <#list mainLinks as link>
                    <li class="">

                        <#if link.url??>
                            <a href="${link.url!''}">


                                ${link.name}

                            </a>


                        </#if>


                    </li>
                                </#list>


            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">更多
                    <span class="caret"></span></a>
                <ul class="dropdown-menu">
                                 <#if links??>
                                     <#list links as link>
                    <li class="">

                        <#if link.url??>
                            <a href="${link.url!''}">


                                ${link.name}

                            </a>

                        <#else>
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> ${link.name} <b class="caret"></b></a>
                                <ul class="dropdown-menu">

                                    <#list link.subMenu as menu>
                                        <li><a href="${menu.url}">${menu.name}</a></li>

                                    </#list>

                                </ul>
                        </#if>


                    </li>
                                     </#list>
                                 </#if>
                    <li class="divider"></li>
                </ul>
            </li>


        </ul>


    <ul class="nav navbar-nav pull-right">


<#--
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> <@security.authentication property="principal.username" /> <b class="caret"></b></a>
            <ul class="dropdown-menu">



                             <li><a href="${userUrl}">我的账户</a></li>

                    <li class="divider"></li>
                    <li>
                        <form action="${rc.contextPath}/logout"  method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                            <button type="submit" class="btn-link btn-block">登出</button>
                        </form>

                    </li>
                    <li class="divider"></li>



                <div sec:authorize="isAuthenticated()">

                </div>
                <div sec:authorize="isAnonymous()">
                </div>

            </ul>
        </li>
-->


        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> <b class="caret"></b></a>
            <ul class="dropdown-menu">



                <li><a href="/mobile/register/login">登录</a></li>

                <li class="divider"></li>



            </ul>
        </li>


    </ul>

    <ul class="nav navbar-nav navbar-right hidden">


        <li class="dropdown ">
<#--            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <span class="glyphicon glyphicon-user"></span> 
                <strong>Nombre</strong>
                <span class="glyphicon glyphicon-chevron-down"></span>
            </a>-->
            <ul style="background-color:white;" class="dropdown-menu">
                <li>
                    <div class="navbar-login">
                        <div class="row">
                            <div class="col-lg-4">
                                <p class="text-center">
                                    <span class="glyphicon glyphicon-user icon-size"></span>
                                </p>
                            </div>
                            <div class="col-lg-8">



                    <p class="text-left"><strong><#if user??>${user.userName}</#if> </strong></p>
                                <p class="text-left small">correoElectronico@email.com</p>
                                <p class="text-left">
                                    <a href="#" class="btn btn-primary btn-block btn-sm">Actualizar Datos</a>
                                </p>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="divider"></li>
                <li>
                    <div class="navbar-login navbar-login-session">
                        <div class="row">
                            <div class="col-lg-12">
                                <p>
                                    <a href="#" class="btn btn-danger btn-block">Cerrar Sesion</a>
                                </p>
                            </div>
                        </div>
                    </div>
                </li>

                <li class="divider"></li>
                <li>
                    <form action="http://localhost:8080/addCustomer "  method="post">
                        <button type="submit">Save</button>
                        <button type="submit" class="btn-link btn-block">登出</button>

                    </form>
                <#--
                                    <button onClick="logout()" class="btn-link ">退出</button>
                -->
                </li>
            </ul>
        </li>
        <li class="dropdown hidden">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">SIGN IN <span class="caret"></span></a>
            <ul id="login-dp" class="dropdown-menu">
                <li>
                    <div class="row">
                        <div class="col-md-12">
                            Login via
                            <div class="social-buttons">
                                <a href="#" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
                                <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i> Twitter</a>
                            </div>
                            or
                            <form class="form" role="form" method="post" action="login" accept-charset="UTF-8" id="login-nav">
                                <div class="form-group">
                                    <label class="sr-only" for="exampleInputEmail2">Email address</label>
                                    <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Email address" required="">
                                </div>
                                <div class="form-group">
                                    <label class="sr-only" for="exampleInputPassword2">Password</label>
                                    <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password" required="">
                                    <div class="help-block text-right"><a href="">Forget the password ?</a></div>
                                </div>
                                <div class="form-group">
                                    <button type="submit" class="btn btn-primary btn-block">Sign in</button>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox"> keep me logged-in
                                    </label>
                                </div>
                            </form>
                        </div>
                        <div class="bottom text-center">
                            New here ? <a href="#"><b>Join Us</b></a>
                        </div>
                    </div>
                </li>
            </ul>
        </li>



    </ul>

</nav>
