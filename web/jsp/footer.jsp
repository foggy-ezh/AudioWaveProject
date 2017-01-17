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
</head>
<body>
</div>
<div class=" text-align-center application_footer footer " >
    &copy;Copyright
    <img class="imgLang usa" src="../media/images/usa.png" alt="alt"></a>
    <img class="imgLang rus"  src="../media/images/russia.png"   alt="alt"></a>
</div>
</div>

<script src="js/vendor/angular.min.js"></script>
<script src="js/vendor/angular-animate.min.js"></script>
<script src="js/vendor/ui-bootstrap-tpls.js"></script>
<script src="js/app.js"></script>
<script src="js/vendor/jquery-3.1.1.min.js"></script>
<script src="js/vendor/bootstrap.min.js"></script>
<script src="js/wowslider.js"></script>
<script src="js/script.js"></script>
<script src="js/audioplayer.js"></script>
<script>
    $( function()
    {
        $( 'audio' ).audioPlayer();
    });
</script>
<script type="text/javascript">
    $("#myModal").modal('show');
</script>
<script type="text/javascript">
    $('#myModall').on('show.bs.modal', function() {
        $("#myModal").modal('hide');
    })
</script>
</body>
</html>