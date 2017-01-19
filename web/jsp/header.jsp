<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 16.01.2017
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
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
    <link rel="stylesheet" type="text/css" href="../css/slider.style.css" />
    <link rel="stylesheet" type="text/css" href="../css/audio.css" />
    <link rel="stylesheet" type="text/css" href="../css/hottest.css" />
    <title>AudioWave</title>
</head>

<body>
<div class="container application">
    <div class="content">
        <nav role="navigation" class="navbar navbar-default .navbar-fixed-top">
            <!-- Логотип и мобильное меню -->
            <div class="navbar-header">
                <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="#" class="navbar-brand logo"></a>
            </div>
            <!-- Навигационное меню -->
            <div id="navbarCollapse" class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#"><fmt:message key="header.main"/></a></li>
                    <li><a href="#"><fmt:message key="header.singer"/></a></li>
                    <li><a href="#"><fmt:message key="header.album"/></a></li>
                    <!-- Выподающие меню с подпунктами -->

                </ul>
                <!-- Поиск по сайту -->
                <form role="search" class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" placeholder="<fmt:message key="header.search"/>" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-default search"><img src="../media/images/search.png" width="19" height="19" /></button>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#myModal" data-toggle="modal"><fmt:message key="header.auth"/></a></li>
                    <!-- 					<li class="dropdown">
                                            <a data-toggle="dropdown" class="dropdown-toggle" href="#");">
                                                Вы вошли как 12345678912345678912
                                                <b class="caret"></b>
                                            </a>
                                            <ul role="menu" class="dropdown-menu">
                                                <li><a href="#">Моя аудиотека</a></li>
                                                <li><a href="#">Настройки</a></li>
                                                <li class="divider"></li>
                                                <li><a href="#">Выход</a></li>
                                            </ul>
                                        </li> -->
                </ul>
            </div>
        </nav>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel">Авторизация</h4>
                        <p>Введите данные для входа</p>
                    </div>
                    <div class="modal-body">
                        <form name = "signin" method="post" action="AudioWave">
                            <span>Логин</span><br>
                            <input type="text" name="login" required title="Используйте от 5 символов = 'a-zA-Z_0-9'" pattern="[\w]{5,}"> <span class="err" id="err-login"></span><br>
                            <p></p>
                            <span>Пароль</span><br>
                            <input type="password" name="pwd1" required title="Используйте от 6 символов""> <span class="err" id="err-pwd1"></span><br>
                            <br/><button type="submit" class="btn">Войти</button>
                            <input type="hidden" name="command" value="signin"/>
                        </form>
                        <p></p>
                        <p> Нет аккаунта?  <a href="#myModall" data-toggle="modal">Зарегистрироваться</a></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="myModall" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabell">Название модали111</h4>
                    </div>
                    <div class="modal-body">
                        <a href="#myModal" data-toggle="modal">Авторизация</a>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        <button type="button" class="btn btn-primary">Сохранить изменения</button>
                    </div>
                </div>
            </div>
        </div>


</body>
</html>
