<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/taglib/popularaudio.tld" prefix="mytag" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.languageText"/>
<html>
<head>
    <link rel="shortcut icon" href="../media/images/title_logo.png">
</head>
<div>
<%@include file="header.jsp" %>
<c:if test="${role eq 'user'}">
    <c:choose>
        <c:when test="${empty audiotracks}">
            <div class="mainbox">
                <h1><fmt:message key="audio.not.found"/></h1>
            </div>
        </c:when>
        <c:otherwise>
            <div class="audio-list-container">
                <c:forEach items="${audiotracks}" var="item">
                    <div class="audio-list">
                        <div class="audio-div">
                            <audio src="${item.location}" controls preload style=""></audio>
                        </div>
                        <div class="inline-block audio-info">
                            <a href="<c:url value="/AudioWave?command=current_singer&id=${item.singer.id}"/>">
                            ${item.singer.name}
                            </a>
                        </div>
                        <div class="inline-block audio-info">
                         — ${item.name}
                        </div>
                        <c:if test="${not empty item.featuredSinger}">
                            <div class="inline-block audio-info">
                                <fmt:message key="singer.feat"/>
                                <c:forEach items="${item.featuredSinger}" var="featured">
                                    <a href="<c:url value="/AudioWave?command=current_singer&id=${featured.id}"/>"
                                    >${featured.name}
                                    </a>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
