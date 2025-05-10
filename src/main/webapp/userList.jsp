<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User List</title>
</head>
<body>
<h2>Buyers</h2>
<c:if test="${empty buyerList}">
    <p>No buyers found.</p>
</c:if>
<c:if test="${not empty buyerList}">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
            <th>Full Name</th>
            <th>Email</th>
        </tr>
        <c:forEach var="buyer" items="${buyerList}">
            <tr>
                <td>${buyer.id}</td>
                <td>${buyer.username}</td>
                <td>${buyer.role}</td>
                <td>${buyer.buyerFullName}</td>
                <td>${buyer.email}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<h2>Sellers</h2>
<c:if test="${empty sellerList}">
    <p>No sellers found.</p>
</c:if>
<c:if test="${not empty sellerList}">
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
            <th>Contact Person</th>
            <th>Company Name</th>
        </tr>
        <c:forEach var="seller" items="${sellerList}">
            <tr>
                <td>${seller.id}</td>
                <td>${seller.username}</td>
                <td>${seller.role}</td>
                <td>${seller.sellerFullName}</td>
                <td>${seller.companyName}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<h2>Administrators</h2>
<c:if test="${empty adminList}">
    <p>No admins found.</p>
</c:if>
<c:if test="${not empty adminList}">
    <ul>
        <c:forEach var="admin" items="${adminList}">
            <li>${admin.username}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>