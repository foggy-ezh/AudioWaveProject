<%--
  Created by IntelliJ IDEA.
  User: Artem
  Date: 16.01.2017
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <c:if test="${loginErr == 'true'}">
    $("#myModal").modal('show');
    document.getElementById("login-err").innerHTML = '<fmt:message key="header.login.error"/>';
    <c:set var="loginErr" value="false" scope="session"/>
    </c:if>
    <c:if test="${regErr == 'login'}">
    $("#myModall").modal('show');
    document.getElementById("reg-err").innerHTML = '<fmt:message key="header.reg.login.error"/>';
    <c:set var="regErr" value="false" scope="session"/>
    </c:if>
    <c:if test="${regErr == 'true'}">
    $("#myModall").modal('show');
    document.getElementById("reg-err").innerHTML = '<fmt:message key="header.reg.error"/>';
    <c:set var="regErr" value="false" scope="session"/>
    </c:if>
    <c:if test="${regErr == 'success'}">
    $("#myModall").modal('show');
    document.getElementById("reg-err").innerHTML = '<fmt:message key="header.reg.success"/>';
    <c:set var="regErr" value="false" scope="session"/>
    </c:if>
    $('#myModall').on('show.bs.modal', function () {
        $("#myModal").modal('hide');
    });
    $('#myModal').on('hide.bs.modal', function() {
        document.getElementById("login-err").innerHTML='';
    })
</script>
</body>
</html>