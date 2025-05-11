<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Главная</title>
</head>
<body>
    <h1>Добро пожаловать!</h1>

    <%@ include file="header.jsp"%>

    <h2>Все заявки на продажу</h2>
    <c:if test="${empty requests}">
        <p>Нет активных заявок.</p>
    </c:if>
    <c:forEach items="${requests}" var="r">
        <div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
            <h3>${r.name}</h3>
            <p>${r.description}</p>
            <p>Цена: ${r.price} руб.</p>
            <form action="place-order" method="post">
                <input type="hidden" name="request_id" value="${r.id}">
                <button type="submit">Купить</button>
            </form>
        </div>
    </c:forEach>
</body>
</html>