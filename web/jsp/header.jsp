<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.languageText"/>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/vendor/bootstrap.min.css">
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" type="text/css" href="../css/slider.style.css"/>
    <link rel="stylesheet" type="text/css" href="../css/audio.css"/>
    <link rel="stylesheet" type="text/css" href="../css/hottest.css"/>
    <title>AudioWave</title>
</head>

<body>
<div class="container application">
    <div class="content">
        <nav role="navigation" class="navbar navbar-default .navbar-fixed-top">
            <div class="navbar-header">
                <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="/AudioWave" class="navbar-brand logo"></a>
            </div>
            <div id="navbarCollapse" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="<c:url value="/AudioWave"/>"><fmt:message key="header.main"/></a></li>
                    <li><a href="<c:url value="/AudioWave?command=singer"/>"><fmt:message key="header.singer"/></a></li>
                    <li><a href="<c:url value="/AudioWave?command=album"/>"><fmt:message key="header.album"/></a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${role eq 'guest'}">
                            <li><a href="#myModal" data-toggle="modal"><fmt:message key="header.auth"/></a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="dropdown">
                                <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                    <fmt:message key="header.enter"/> ${sessionScope.currentUser.login}
                                    <b class="caret"></b>
                                </a>
                                <ul role="menu" class="dropdown-menu">
                                    <c:if test="${role eq 'user'}">
                                        <li><a href="<c:url value="/AudioWave?command=user_audiotracks"/>"><fmt:message
                                                key="header.my.audio"/></a></li>
                                        <li><a href="#" data-target="#changeUserModal" data-toggle="modal">
                                            <fmt:message key="header.settings"/></a></li>
                                        <li><a href="/AudioWave?command=get_funds">
                                            <fmt:message key="header.balance"/>
                                        </a></li>
                                        <li class="divider"></li>
                                    </c:if>
                                    <li><a href="<c:url value="/AudioWave?command=log_out"/>">
                                        <fmt:message key="header.exit"/>
                                    </a></li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </nav>
        <c:if test="${role eq 'guest'}">
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><fmt:message key="header.auth"/></h4>
                        <p><fmt:message key="header.login.input"/></p>
                    </div>
                    <div class="modal-body">
                        <form name="log_in" method="get" action="AudioWave">
                            <span><fmt:message key="header.login.login"/></span><br>
                            <input type="text" name="login"><br>
                            <p></p>
                            <span><fmt:message key="header.login.password"/></span><br>
                            <input type="password" name="password"><br>
                            <button type="submit" class="btn" id="login-btn"><fmt:message key="header.login"/></button>
                            <input type="hidden" name="command" value="log_in"/>
                        </form>
                        <p id="login-err"></p>
                        <p><fmt:message key="header.login.not.reg"/>
                            <a href="#myModall" data-toggle="modal" class="fire-brick">
                                <fmt:message key="header.login.register"/>
                            </a>
                        </p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                key="header.login.close"/></button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="myModall" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel1"><fmt:message key="header.reg"/></h4>
                        <p><fmt:message key="header.reg.data"/></p>
                    </div>
                    <div class="modal-body modal-body-reg">
                        <form name="reg" onsubmit='return regValidate()' method="post" action="AudioWave">
                            <div class="inline-block">
                                <span><fmt:message key="header.login.login"/></span><br>
                                <input type="text" name="login" required title="Use 'a-zA-Z_0-9'" pattern="[\w]*"><br>
                                <span><fmt:message key="header.login.password"/></span><br>
                                <input type="password" name="pwd1" required title="Use at least 6 symbols"><br>
                                <span><fmt:message key="header.reg.password"/></span><br>
                                <input type="password" name="pwd2" required><br>
                            </div>
                            <div class="inline-block">
                                <span><fmt:message key="header.mail"/></span><br>
                                <input type="text" name="mail" required><br>
                                <span><fmt:message key="header.name"/></span><br>
                                <input type="text" name="firstName" required title="Use 'a-zA-Z'"
                                       pattern="[a-zA-Z]*"><br>
                                <span><fmt:message key="header.last.name"/></span><br>
                                <input type="text" name="lastName" required title="Use 'a-zA-Z'"
                                       pattern="[a-zA-Z]*"><br>
                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary" id="reg-btn"><fmt:message
                                    key="header.login.register"/></button>
                            <input type="hidden" name="command" value="register"/>
                        </form>
                        <p id="reg-err"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                key="header.login.close"/></button>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
        <c:if test="${(role eq 'user') and (not empty currentUser)}">
        <div class="modal fade" id="changeUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog ">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="changeUser1"><fmt:message key="change.user.data"/></h4>
                    </div>
                    <div class="modal-body modal-body-reg">
                        <form name="reg" onsubmit='return regValidate()' method="post" action="AudioWave">
                            <div class="inline-block">
                                <span><fmt:message key="header.login.login"/></span><br>
                                <input type="text" name="login" required title="Use 'a-zA-Z_0-9'" pattern="[\w]*"
                                       value="${currentUser.login}"><br>
                                <span><fmt:message key="header.login.password"/></span><br>
                                <input type="password" name="pwd1" required title="Use at least 6 symbols"><br>
                                <span><fmt:message key="header.reg.password"/></span><br>
                                <input type="password" name="pwd2" required><br>
                            </div>
                            <div class="inline-block">
                                <span><fmt:message key="header.mail"/></span><br>
                                <input type="text" name="mail" required
                                       value="${currentUser.mail}"><br>
                                <span><fmt:message key="header.name"/></span><br>
                                <input type="text" name="firstName" required title="Use 'a-zA-Z'"
                                       pattern="[a-zA-Z]*" value="${currentUser.firstName}"><br>
                                <span><fmt:message key="header.last.name"/></span><br>
                                <input type="text" name="lastName" required title="Use 'a-zA-Z'"
                                       pattern="[a-zA-Z]*" value="${currentUser.lastName}"><br>
                            </div>
                            <br/>
                            <button type="submit" class="btn btn-primary" id="reg-btn"><fmt:message
                                    key="button.change"/></button>
                            <input type="hidden" name="command" value="change_user"/>
                        </form>
                        <p id="reg-err"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                key="header.login.close"/></button>
                    </div>
                </div>
            </div>
        </div>
        </c:if>


        <c:if test="${(role eq 'user') and (showFunds)}">
        <div class="modal fade" id="showFundsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="showFunds1"><fmt:message key="header.balance"/></h4>
                    </div>
                    <div class="modal-body modal-body-reg">
                        <form name="reg" method="post" action="AudioWave">
                            <span><fmt:message key="show.funds"/></span><br>
                            <span>${currentUser.amountOfMoney}$</span><br>
                            <br>
                            <span><fmt:message key="add.funds"/></span><br>
                            <input type="text" name="funds" required pattern="^\d{1,6}\.\d{2,2}$"
                                   title="<fmt:message key="value.between"/> 0.01 and 999999.99"><br>
                            <button type="submit" class="btn btn-primary" id="reg-btn"><fmt:message
                                    key="button.submit"/></button>
                            <input type="hidden" name="command" value="add_funds"/>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                                key="header.login.close"/></button>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
</body>
</html>
