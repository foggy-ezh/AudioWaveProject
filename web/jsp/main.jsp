
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.languageText"/>
<html>
<head>
    <title><fmt:message key="language.title"/></title>
</head>
<div>
<%@include file="header.jsp"%>
<section class="row">
    <div style="width: 100%">
        <div class="mainbox">
            <h1>ОНЛАЙН МАГАЗИН АУДИОТРЕКОВ</h1>
        </div>
        <hr>
        <div class="zag">
            <h2>Популярные альбомы</h2>
        </div>
        <!-- Start WOWSlider.com BODY section --> <!-- add to the <body> of your page -->
        <div id="wowslider-container0">
            <div class="ws_images"><ul>
            <c:forEach var="albums" items="${albums}" begin="0" end="3" varStatus="loop">
                <li><a href="#"><img src="${albums.coverURI}" alt="${albums.albumName}" title="${albums.albumName}" id="wows0_${loop.current}"/></a></li>
<%--                <li><a href="https://www.youtube.com/"><img src="../media/images/zz_cover.png" alt="zz cover" title="zz cover" id="wows0_0"/></a></li>
                <li><img src="../media/images/cover.jpg" alt="Cover" title="Cover" id="wows0_1"/></li>
                <li><a href="http://wowslider.com/vi"><img src="../media/images/cover_0.jpg" alt="image slider jquery" title="cover_0" id="wows0_2"/></a></li>
                <li><img src="../media/images/folder.jpg" alt="Folder" title="Folder" id="wows0_3"/></li>--%>
            </c:forEach></ul></div>
            <div class="ws_bullets"><div>
                <a href="#" title="zz cover"><span>1</span></a>
                <a href="#" title="Cover"><span>2</span></a>
                <a href="#" title="cover_0"><span>3</span></a>
                <a href="#" title="Folder"><span>4</span></a>
                <div class="ws_shadow"></div>
            </div>
                <!-- End WOWSlider.com BODY section -->
            </div>
        </div>
    </div>
</section>
<a href="../AudioWave?command=change_language&lang=ru_RU">
    <%--<input type="hidden" name="jspPath" value="${pageContext.request.servletPath}"/>
    <input type="hidden" name="command" value="changeLanguage"/>
    <input type="hidden" name="lang" value="ru_RU" />--%>
    <img src="../media/image/russia.png">
</a>
<a href="../AudioWave?command=change_language&lang=en_US" />
    <%--<input type="hidden" name="jspPath" value="${pageContext.request.servletPath}"/>
    <input type="hidden" name="command" value="changeLanguage"/>
    <input type="hidden" name="lang" value="en_EN" />--%>
    <img src="../media/image/usa.png">
</a>
<audio src="/project/music/i61 - АЙ(2017)/1.GLUTAMATE ALPHA (Prod. by UNICORN WAVES).mp3"  controls preload></audio>

    <audio src="/project/music/Beyonce/Beyonce - LEMONADE/11 ALL NIGHT.mp3"  controls preload></audio>
<%@include file="footer.jsp"%>
</body>
</html>
