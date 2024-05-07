<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Goods</title>
    <%@include file="styles.jsp" %>
</head>
<body>
<%@include file="menuheader.jsp" %>
<form action="${pageContext.request.contextPath}/cart" method="post">
    <table>
        <tr>
            <th>Product name</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <c:forEach var="item" items="${requestScope.cartInfo}">
            <tr>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="buy">
</form>
