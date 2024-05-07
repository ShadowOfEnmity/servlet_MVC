<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User: ${requestScope.user.name}</title>
    <%@include file="styles.jsp" %>
</head>
<body>
<%@include file="menuheader.jsp" %>
<br/>
<form action="${pageContext.request.contextPath}/user" method="post">
    <input type="hidden" name="id" value="${requestScope.user.id}">
    <label for="name">Name:
        <input type="text" name="name" id="name" value="${requestScope.user.name}">
    </label></br>
    <label for="age">Age:
        <input type="text" name="age" id="age" value="${requestScope.user.age}">
    </label></br>
    <label for="email">Email:
        <input type="text" name="email" id="email" value="${requestScope.user.email}">
    </label></br>
    <div style="text-align: left">
        <input type="submit" name="action" value="Edit">
        <input type="submit" name="action" value="Delete">
    </div>
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
