<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name">
    </label></br>
    <label for="age">Age:
        <input type="text" name="age" id="age">
    </label></br>
    <label for="email">Email:
        <input type="text" name="email" id="email">
    </label></br>
    <label for="login">Login:
        <input type="text" name="login" id="login">
    </label></br>
    <label for="pwd">Password:
        <input type="password" name="pwd" id="pwd">
    </label></br>
    <input type="submit" value="Send">
    <c:if test="${not empty requestScope.errors}">
        <div style="color: red">
            <c:forEach var="error" items="${requestScope.errors}">
                <p>${error.message}</p>
            </c:forEach>
        </div>
    </c:if>
</form>
</body>
</html>
