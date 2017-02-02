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
        <c:when test="${symbol eq '%'}">
            <li class="active"><fmt:message key="singer.all"/></li>
        </c:when>
        <c:otherwise>
            <li><a href="<c:url value="/AudioWave?command=singer"/>"><fmt:message key="singer.all"/></a></li>
            <li class="active">${symbol}</li>
        </c:otherwise>
    </c:choose>
</ul>
<c:if test="${role eq 'admin'}">
    <div class="btn-add">
        <button type="button" class="btn-add" data-toggle="modal" data-target="#AddSingerModal">
            <fmt:message key="button.add"/>
        </button>
    </div>
    <div class="modal fade" id="AddSingerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel"><fmt:message key="add.singer"/></h4>
                </div>
                <div class="modal-body">
                    <form name="add_singer" method="post" action="AudioWave">
                        <span><fmt:message key="header.name"/></span><br>
                        <c:choose>
                            <c:when test="${not empty changeSinger}">
                                <input type="text" name="singerName" required value="${changeSinger.name}"><br>
                                <input type="hidden" name="singerId" value="${changeSinger.id}"/>
                                <button type="submit" class="btn" id="login-btn"><fmt:message
                                        key="button.change"/></button>
                            </c:when>
                            <c:otherwise>
                                <input type="text" name="singerName" required><br>
                                <button type="submit" class="btn" id="login-btn"><fmt:message
                                        key="button.add"/></button>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" name="command" value="add_singer"/>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message
                            key="header.login.close"/></button>
                </div>
            </div>
        </div>
    </div>
</c:if>
<hr>
<div class="text-align-center">
    <c:choose>
        <c:when test="${empty singers}">
            <div class="mainbox">
                <h1><fmt:message key="singer.not.found"/></h1>
            </div>
            <hr>
        </c:when>
        <c:otherwise>
            <div class="all-singers">
                <c:forEach items="${singers}" var="item">
                    <div>
                        <p><a href="<c:url value="/AudioWave?command=current_singer&id=${item.id}"/>">${item.name}</a>
                        </p>
                        <c:if test="${role eq 'admin'}">
                            <form name="change_singer" method="post" action="AudioWave">
                                <input type="hidden" name="singerName" value="${item.name}">
                                <input type="hidden" name="singerId" value="${item.id}"/>
                                <button type="submit" class="btn inline-block"><fmt:message
                                    key="button.change"/></button>
                                <input type="hidden" name="command" value="change_singer"/>
                            </form>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
<c:if test="${not empty singers}">
    <div class="pagin">
        <ul class="pagination">
            <c:forEach begin="1" end="${total}" var="i">
                <c:choose>
                    <c:when test="${page eq i}">
                        <li class="active"><a href="#">${i}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="<c:url value="/AudioWave?command=singer&symbol=${symbol}&page=${i}"/>">${i}</a>
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
