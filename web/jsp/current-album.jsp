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
<body>
<%@include file="header.jsp" %>
<c:choose>
    <c:when test="${albumNotFound}">
        <ul class="breadcrumb">
            <li><a href="<c:url value="/AudioWave?command=album"/>"><fmt:message key="album.all"/></a></li>
        </ul>
        <hr>
        <div class="mainbox">
            <h1><fmt:message key="album.not.found"/></h1>
        </div>
    </c:when>
    <c:otherwise>
        <ul class="breadcrumb">
            <li><a href="<c:url value="/AudioWave?command=album"/>"><fmt:message key="album.all"/></a></li>
            <li><a href="<c:url value="/AudioWave?command=album&symbol=${symbol}"/>">${symbol}</a></li>
            <li class="active">${album.albumName}</li>
        </ul>
        <hr>
        <div class="mainbox">
            <h1>
                <a href="<c:url value="/AudioWave?command=current_singer&id=${album.singer.id}"/>">${album.singer.name}</a>
            </h1>
        </div>
        <hr>
        <div class="zag">
            <h2><fmt:message key="singer.albums"/></h2>
        </div>
        <div class="text-align-center">
            <c:choose>
                <c:when test="${albumNotFound}">
                    <div>
                        <p><fmt:message key="singer.albums.not.added"/></p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${albums}" var="item">
                        <div class="album">
                            <div class="image">
                                <img class="img1" src="${item.coverURI}" width="300px" height="300px"
                                     alt="${item.albumName}">
                            </div>
                            <div class="album-name">
                                <p>
                                    <a href="<c:url value="/AudioWave?command=current_album&id=${item.id}"/>">${item.albumName}</a>
                                </p>
                            </div>
                            <div class="year">
                                <p>${item.releaseYear}</p>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </c:otherwise>
</c:choose>
<%@include file="footer.jsp" %>
</body>
</html>
