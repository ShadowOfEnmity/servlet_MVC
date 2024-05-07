<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: stas
  Date: 06.05.2024
  Time: 02:49
  To change this template use File | Settings | File Templates.
--%>
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
            <th>id</th>
            <th>Product name</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <c:forEach var="item" items="${goods}">
            <tr>
                <td>${item.id}</td>
                <td>${item.name}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
                <td><input type="number" name="quantity_${item.id}" value="0" max="${item.quantity}" required></td>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" value="add to cart">
</form>
</body>
</html>
