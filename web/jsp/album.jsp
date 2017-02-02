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
<div id="alphabet-nav">
    <span>
        <c:forEach items="${letters}" var="item">
            <a href="<c:url value="/AudioWave?command=album&symbol=${item}"/>">${item}</a>
        </c:forEach>
    </span>
</div>
<hr>
<ul class="breadcrumb">
    <c:choose>
        <c:when test="${symbol eq '%'}">
            <li class="active"><fmt:message key="album.all"/></li>
        </c:when>
        <c:otherwise>
            <li><a href="<c:url value="/AudioWave?command=album"/>"><fmt:message key="album.all"/></a></li>
            <li class="active">${symbol}</li>
        </c:otherwise>
    </c:choose>
</ul>
<hr>
<div class="text-align-center">
    <c:choose>
        <c:when test="${empty albums}">
            <div class="mainbox">
                <h1><fmt:message key="album.not.found"/></h1>
            </div>
            <hr>
        </c:when>
        <c:otherwise>
            <div class="all-singers">
                <c:forEach items="${albums}" var="item">
                    <div>
                        <p><a href="<c:url value="/AudioWave?command=current_album&id=${item.id}"/>">
                        ${item.albumName}
                            <c:if test="${item.blocked}">
                                <fmt:message key="info.blocked"/>
                            </c:if>
                        </a>
                        </p>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<c:if test="${not empty albums}">
    <div class="pagin">
        <ul class="pagination">
            <c:forEach begin="1" end="${total}" var="i">
                <c:choose>
                    <c:when test="${page eq i}">
                        <li class="active"><a href="#">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="<c:url value="/AudioWave?command=album&symbol=${symbol}&page=${i}"/>">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
</c:if>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
