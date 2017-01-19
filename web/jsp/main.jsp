<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/taglib/popularaudio.tld" prefix="mytag" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.languageText"/>
<html>
<head>
</head>
<body>
    <%@include file="header.jsp" %>
    <section class="row">
        <div style="width: 100%">
            <div class="mainbox">
                <h1><fmt:message key="main.h1"/></h1>
            </div>
            <hr>
            <div class="zag">
                <h2><fmt:message key="main.pop.albums"/></h2>
            </div>
            <div id="wowslider-container0">
                <div class="ws_images">
                    <ul>
                        <c:forEach var="albums" items="${albums}" begin="0" end="4" varStatus="loop">
                            <li><a href="#">
                                <img src="${albums.coverURI}" alt="${albums.albumName}"title="${albums.singerName} - ${albums.albumName}" id="wows0_${loop.current}"/>
                            </a></li>
                        </c:forEach></ul>
                </div>
                <div class="ws_bullets">
                    <div>
                        <c:forEach var="albums" items="${albums}" begin="0" end="4" varStatus="loop">
                            <a href="#" title="${albums.albumName}"><span>${loop.current}</span></a>
                        </c:forEach>
                        <div class="ws_shadow"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>
    <div class="hottest">
        <hr>
        <div class="text-align-center">
            <h2><fmt:message key="main.pop.audiotracks"/></h2>
        </div>
        <mytag:popaudio list="${audiotracks}"></mytag:popaudio>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>
