<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User List</title>
</head>
<body>
    <h2>Employee List</h2>
    <c:if test="${empty employeeList}">
        <p>No employees found.</p>
    </c:if>
    <c:if test="${not empty employeeList}">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Role</th>
                <th>Full Name</th>
                <th>Email</th>
            </tr>
            <c:forEach var="employee" items="${employeeList}">
                <tr>
                    <td>${employee.id}</td>
                    <td>${employee.username}</td>
                    <td>${employee.role}</td>
                    <td>${employee.fullName}</td>
                    <td>${employee.email}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <h2>Employer List</h2>
    <c:if test="${empty employerList}">
        <p>No employers found.</p>
    </c:if>
    <c:if test="${not empty employerList}">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Role</th>
                <th>Full Name</th>
                <th>Company Name</th>
            </tr>
            <c:forEach var="employer" items="${employerList}">
                <tr>
                    <td>${employer.id}</td>
                    <td>${employer.username}</td>
                    <td>${employer.role}</td>
                    <td>${employer.employerFullName}</td>
                    <td>${employer.companyName}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</body>
</html>