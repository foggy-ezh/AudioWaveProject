<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="../media/images/title_logo.png">
</head>
<body>

<div class=" text-align-center application_footer footer ">
    <span>&copy;Copyright</span>
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
<script type="text/javascript">
    document.addEventListener('play', function(e){
        var audios = document.getElementsByTagName('audio');
        for(var i = 0, len = audios.length; i < len;i++){
            if(audios[i] != e.target){
                audios[i].pause();
            }
        }
    }, true);
</script>
<script>
    $(function () {
        $('audio').audioPlayer();
    });
</script>
<script type="text/javascript">
    <c:if test="${loginErr eq 'true'}">
    $("#myModal").modal('show');
    document.getElementById("login-err").innerHTML = '<fmt:message key="header.login.error"/>';
    <c:set var="loginErr" value="false" scope="session"/>
    </c:if>
    <c:if test="${regErr eq 'login'}">
    $("#myModall").modal('show');
    document.getElementById("reg-err").innerHTML = '<fmt:message key="header.reg.login.error"/>';
    <c:set var="regErr" value="false" scope="session"/>
    </c:if>
    <c:if test="${regErr eq 'true'}">
    $("#myModall").modal('show');
    document.getElementById("reg-err").innerHTML = '<fmt:message key="header.reg.error"/>';
    <c:set var="regErr" value="false" scope="session"/>
    </c:if>
    <c:if test="${regErr eq 'success'}">
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
<script type="text/javascript">
    <c:if test="${not empty changeSinger}">
    $("#AddSingerModal").modal('show');
    <c:remove var="changeSinger"/>
    </c:if>
    <c:if test="${not empty changeAlbum}">
    $("#AddAlbumModal").modal('show');
    <c:remove var="changeAlbum"/>
    </c:if>
</script>
</body>
</html>