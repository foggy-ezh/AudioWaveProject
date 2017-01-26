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
            <a href="<c:url value="/AudioWave?command=singer&symbol=${item}"/>">${item}</a>
        </c:forEach>
    </span>
</div>
<hr>
<ul class="breadcrumb">
    <c:choose>
        <c:when test="${symbol== '%'}">
            <li class="active"><fmt:message key="singer.all"/></li>
        </c:when>
        <c:otherwise>
            <li><a href="/AudioWave?command=singer"><fmt:message key="singer.all"/></a></li>
            <li class="active">${symbol}</li>
        </c:otherwise>
    </c:choose>
</ul>
<hr>
<div class="all-singers">
    <c:choose>
        <c:when test="${notFound== 'true'}">
            <div>
                <p><fmt:message key="singer.not.found"/></p>
            </div>
        </c:when>
        <c:otherwise><c:forEach items="${singers}" var="item">
            <div>
                <p><a href="<c:url value="/AudioWave?command=current_singer&symbol=${item.id}"/>">${item.name}</a></p>
            </div>
        </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
<c:if test="${empty notFound}">
    <div class="pagin">
        <ul class="pagination">
            <c:forEach begin="1" end="${total}" var="i">
                <c:choose>
                    <c:when test="${page eq i}">
                        <li class="active"><a href="#">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="active"><a
                                href="<c:url value="/AudioWave?command=singer&symbol=${symbol}&page=${i}"/>">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
</c:if>
<%@include file="footer.jsp" %>
</body>
</html>