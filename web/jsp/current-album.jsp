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
    <c:when test="${empty album}">
        <ul class="breadcrumb">
            <li><a href="<c:url value="/AudioWave?command=album"/>"><fmt:message key="album.all"/></a></li>
        </ul>
        <hr>
        <div class="mainbox">
            <h1><fmt:message key="album.not.found"/></h1>
        </div>
        </div>
    </c:when>
    <c:otherwise>
        <ul class="breadcrumb">
            <li><a href="<c:url value="/AudioWave?command=album"/>"><fmt:message key="singer.albums"/></a></li>
            <li><a href="<c:url value="/AudioWave?command=album&symbol=${symbol}"/>">${symbol}</a></li>
            <li class="active">${album.albumName}</li>
        </ul>
        <hr>
        <div>
            <div class="inline-block"><img src="${album.coverURI}" width="300" height="300">
            </div>
            <div class="inline-block album-info">
                <h2><span class="not-bold"><fmt:message key="singer.one"/> </span>
                    <a href="<c:url value="/AudioWave?command=current_singer&id=${album.singer.id}"/>">${album.singer.name}</a>
                </h2>
                <h2><span class="not-bold"><fmt:message key="album.one"/> </span>${album.albumName}</h2>
                <h3><fmt:message key="release.year"/> ${album.releaseYear}</h3>
            </div>
        </div>
        <hr>
        <div class="audio-list-container">
            <c:choose>
                <c:when test="${empty album.audiotracks}">
                    <div>
                        <h3><fmt:message key="album.song.not.found"/></h3>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${album.audiotracks}" var="item">
                        <div class="audio-list">
                            <c:choose>
                                <c:when test="${role eq 'admin'}">
                                    <c:choose>
                                        <c:when test="${item.blocked}">
                                            <div class="inline-block">
                                                <form>
                                                    <button type="submit" class="btn-buy" id="unblock-btn"><fmt:message
                                                            key="button.unblock"/></button>
                                                    <input type="hidden" name="command" value="unblock_audio"/>
                                                    <input type="hidden" name="id" value="${item.id}"/>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="inline-block">
                                                <form>
                                                    <button type="submit" class="btn-buy" id="block-btn"><fmt:message
                                                            key="button.block"/></button>
                                                    <input type="hidden" name="command" value="block_audio"/>
                                                    <input type="hidden" name="id" value="${item.id}"/>
                                                </form>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="inline-block">
                                        <form>
                                            <button type="submit" class="btn-buy" id="change-btn"><fmt:message
                                                    key="button.change"/></button>
                                            <input type="hidden" name="command" value="change_audio"/>
                                            <input type="hidden" name="id" value="${item.id}"/>
                                        </form>
                                    </div>
                                    <div class="inline-block audio-info cost">
                                            ${item.cost}$
                                    </div>
                                    <div class="audio-div">
                                        <audio src="${item.location}" controls preload style=""></audio>
                                    </div>
                                    <div class="inline-block audio-info">${item.name}
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
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${!item.blocked}">
                                        <div class="inline-block">
                                            <form>
                                                <button type="submit" class="btn-buy" id="buy-btn"><fmt:message
                                                        key="button.buy"/></button>
                                                <input type="hidden" name="command" value="buy"/>
                                            </form>
                                        </div>
                                        <div class="inline-block audio-info cost">
                                                ${item.cost}$
                                        </div>
                                        <div class="audio-div">
                                            <audio src="${item.location}" controls preload style=""></audio>
                                        </div>
                                        <div class="inline-block audio-info">${item.name}
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
                                    </c:if>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <div>
            <c:choose>
                <c:when test="${not empty album.albumComments}">
                    <div class="text-align-center ">
                        <h3><fmt:message key="album.comments"/></h3>
                    </div>
                    <c:forEach items="${album.albumComments}" var="curcomment">
                        <div class="comment-container">
                            <hr>
                            <p><b>${curcomment.userLogin}</b><br>
                                    ${curcomment.comment}
                            </p>
                            <c:if test="${(curcomment.userId eq currentUser.id) or(role eq 'admin')}">
                                <form>
                                    <button type="submit" class="btn-buy"><fmt:message
                                            key="btn.delete"/></button>
                                    <input type="hidden" name="command" value="delete_comment"/>
                                    <input type="hidden" name="userId" value="${curcomment.userId}"/>
                                    <input type="hidden" name="albumId" value="${album.id}"/>
                                    <input type="hidden" name="commentId" value="${curcomment.id}"/>
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="text-align-center ">
                        <h3><fmt:message key="album.comments.not.added"/></h3>
                    </div>
                </c:otherwise>
            </c:choose>
            <hr>
            <div class="text-align-center">
                <c:choose>
                    <c:when test="${role eq 'guest'}">
                        <h4><fmt:message key="album.login.to.comment."/></h4>
                    </c:when>
                    <c:otherwise>
                        <form>
                            <h4><fmt:message key="album.comment.your"/></h4>
                            <textarea name="text" title="Comment" required></textarea><br>
                            <button type="submit" class="btn" id="btn">
                                <fmt:message key="album.comment.add"/>
                            </button>
                            <input type="hidden" name="command" value="add_comment"/>
                            <input type="hidden" name="albumId" value="${album.id}"/>
                            <input type="hidden" name="userId" value="${currentUser.id}"/>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </c:otherwise>
</c:choose>
<%@include file="footer.jsp" %>
</body>
</html>
