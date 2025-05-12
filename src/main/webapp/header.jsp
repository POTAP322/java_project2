<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <c:if test="${not empty user}">
        <strong>Вы вошли как:</strong> ${user.username} (${user.role}) |
        <a href="home">Домой</a> |
        <c:if test="${user.role == 'BUYER'}">
            <a href="buyer-menu">Меню покупателя</a>
        </c:if>
        <c:if test="${user.role == 'SELLER'}">
            <a href="seller-menu">Меню продавца</a>
        </c:if>
        | <a href="logout">Выйти</a>
    </c:if>
</header>
