<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
<div>
    <h1>Error 500</h1>
    <h2>Internal Server Error</h2>
    <p>Exception: ${pageContext.exception}</p>
</div>
</body>
</html>