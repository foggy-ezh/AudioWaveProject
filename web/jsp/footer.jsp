<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 16.01.2017
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.languageText"/>
<html lang="en">
<head>
    <link rel="shortcut icon" href="../media/images/title_logo.png">
</head>
<body>

<div class=" text-align-center application_footer footer ">
    &copy;Copyright
    <a href="../AudioWave?command=change_language&lang=en_US"/>
    <img class="imgLang usa" src="../media/images/usa.png" alt="alt">
    </a>
    <a href="../AudioWave?command=change_language&lang=ru_RU">
        <img class="imgLang rus" src="../media/images/russia.png" alt="alt">
    </a>
</div>

<script src="../js/vendor/angular.min.js"></script>
<script src="../js/vendor/angular-animate.min.js"></script>
<script src="../js/vendor/ui-bootstrap-tpls.js"></script>
<script src="../js/app.js"></script>
<script src="../js/vendor/jquery-3.1.1.min.js"></script>
<script src="../js/vendor/bootstrap.min.js"></script>
<script src="../js/wowslider.js"></script>
<script src="../js/script.js"></script>
<script src="../js/audioplayer.js"></script>
<script>
    $(function () {
        $('audio').audioPlayer();
    });
</script>
<script type="text/javascript">
    <c:if test="${script == 'true'}">
    $("#myModal").modal('show');
    document.getElementById("login-err").innerHTML = '<fmt:message key="header.login.error"/>';
    </c:if>
    <%--${script}--%>
</script>
</body>
</html>