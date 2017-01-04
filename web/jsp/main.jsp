
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="properties.languageText"/>
<html>
<head>
    <title><fmt:message key="language.title"/></title>
</head>
<body>
<a href="../AudioWave?command=change_language&lang=ru_RU&jspPath=${pageContext.request.servletPath}">
    <%--<input type="hidden" name="jspPath" value="${pageContext.request.servletPath}"/>
    <input type="hidden" name="command" value="changeLanguage"/>
    <input type="hidden" name="lang" value="ru_RU" />--%>
    <img src="../media/img/russia.png">
</a>
<a href="../AudioWave?command=change_language&lang=en_US&jspPath=${pageContext.request.servletPath}">
    <%--<input type="hidden" name="jspPath" value="${pageContext.request.servletPath}"/>
    <input type="hidden" name="command" value="changeLanguage"/>
    <input type="hidden" name="lang" value="en_EN" />--%>
    <img src="../media/img/usa.png">
</a>
</body>
</html>
