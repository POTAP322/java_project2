<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="content">
        <h1>Добро пожаловать!</h1>

        <c:if test="${not empty param.warning && param.warning == 'already_ordered'}">
            <div class="error">
                Вы уже откликнулись на эту заявку.
            </div>
        </c:if>

        <c:if test="${not empty param.error && param.error == 'order_failed'}">
            <div class="error">
                Ошибка при оформлении заказа. Попробуйте позже.
            </div>
        </c:if>

        <h2>Все заявки на продажу</h2>
        <c:if test="${empty requests}">
            <p>Нет активных заявок.</p>
        </c:if>

        <c:forEach items="${requests}" var="r">
            <div class="request">
                <h3>${r.name}</h3>
                <p>${r.description}</p>
                <p>Цена: ${r.price} руб.</p>

                <!-- Для покупателей показываем кнопку "Купить", если ещё не покупали -->
                <c:if test="${user.role == 'BUYER'}">
                    <c:set var="ordered" value="false"/>
                    <c:forEach items="${orders}" var="o">
                        <c:if test="${o.sellRequestId == r.id}">
                            <c:set var="ordered" value="true"/>
                        </c:if>
                    </c:forEach>

                    <c:if test="${ordered == 'false'}">
                        <form action="place-order" method="post">
                            <input type="hidden" name="request_id" value="${r.id}">
                            <button type="submit">Купить</button>
                        </form>
                    </c:if>

                    <c:if test="${ordered == 'true'}">
                        <p class="success">Вы уже купили это предложение</p>
                    </c:if>
                </c:if>
            </div>
        </c:forEach>
    </div>
</body>
</html>
