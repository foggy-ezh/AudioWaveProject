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
    <c:when test="${empty singer}">
        <ul class="breadcrumb">
            <li><a href="<c:url value="/AudioWave?command=singer"/>"><fmt:message key="singer.all"/></a></li>
        </ul>
        <hr>
        <div class="mainbox">
            <h1><fmt:message key="singer.not.found"/></h1>
        </div>
    </c:when>
    <c:otherwise>
        <ul class="breadcrumb">
            <li><a href="<c:url value="/AudioWave?command=singer"/>"><fmt:message key="singer.all"/></a></li>
            <li><a href="<c:url value="/AudioWave?command=singer&symbol=${symbol}"/>">${symbol}</a></li>
            <li class="active">${singer.name}</li>
        </ul>
        <hr>
        <div class="mainbox">
            <h1>${singer.name}</h1>
        </div>
        <c:if test="${role eq 'admin'}">
            <div class="btn-add">
                <button type="button" class=" btn-add" data-toggle="modal" data-target="#AddAlbumModal">
                    <fmt:message key="button.add"/>
                </button>
            </div>
            <div class="modal fade" id="AddAlbumModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
                 aria-hidden="true">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel"><fmt:message key="add.album"/></h4>
                        </div>
                        <div class="modal-body">
                            <form name="add_album" method="post" action="AudioWave" enctype="multipart/form-data">
                                <c:choose>
                                    <c:when test="${not empty changeAlbum}">
                                        <span><fmt:message key="header.name"/></span><br>
                                        <input type="text" name="albumName" required
                                               value="${changeAlbum.albumName}"><br>
                                        <span><fmt:message key="release.year"/></span><br>
                                        <input type="text" name="releaseYear" required pattern="^[1-9][0-9]{3}$"
                                               value="${changeAlbum.releaseYear}"><br>
                                        <fmt:message key="album.cover"/><br>
                                        <input type="file" name="cover" accept=".jpg"/><br>
                                        <input type="hidden" name="albumId" value="${changeAlbum.id}"/>
                                        <input type="hidden" name="command" value="add_album"/>
                                    </c:when>
                                    <c:otherwise>
                                        <input type="hidden" name="command" value="add_album"/>
                                        <span><fmt:message key="header.name"/></span><br>
                                        <input type="text" name="albumName" required><br>
                                        <span><fmt:message key="release.year"/></span><br>
                                        <input type="text" name="releaseYear" required pattern="^[1-9][0-9]{3}$"><br>
                                        <fmt:message key="album.cover"/><br>
                                        <input type="file" name="cover" accept=".jpg" required/><br>
                                        <span><fmt:message key="album.add.info"/></span><br>
                                        <span><fmt:message key="header.name"/></span><br>
                                        <input type="text" name="audioName" required><br>
                                        <fmt:message key="download.audio"/><br>
                                        <input type="file" name="audiotrack" accept=".mp3" required/><br>
                                        <span><fmt:message key="add.cost"/></span><br>
                                        <input type="text" name="audioCost" required pattern="^\d{1,3}\.\d{0,2}$"><br>
                                        <span><fmt:message key="singer.featured"/></span><br>
                                        <input type="text" name="featured_singer[]"><br>
                                        <input type="button" class="btn" value="<fmt:message
                                                key="button.add"/>" id="form_status_added"><br>
                                        <input type="hidden" name="singerId" value="${singer.id}"/>
                                    </c:otherwise>
                                </c:choose>
                                <button type="submit" class="btn btn-buy" id="login-btn"><fmt:message
                                        key="button.send"/></button>
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
        <div class="zag">
            <h2><fmt:message key="singer.albums"/></h2>
        </div>
        <div class="text-align-center">
            <c:choose>
                <c:when test="${empty singer.albums}">
                    <div>
                        <p><fmt:message key="singer.albums.not.added"/></p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${singer.albums}" var="item">
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
                            <c:if test="${role eq 'admin'}">
                                <div class="inline-block">
                                    <form name="change_singer" method="post" action="AudioWave">
                                        <input type="hidden" name="albumName" value="${item.albumName}">
                                        <input type="hidden" name="albumId" value="${item.id}"/>
                                        <input type="hidden" name="releaseYear" value="${item.releaseYear}"/>
                                        <button type="submit" class="btn"><fmt:message
                                                key="button.change"/></button>
                                        <input type="hidden" name="command" value="change_album"/>
                                    </form>
                                </div>
                            </c:if>
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
