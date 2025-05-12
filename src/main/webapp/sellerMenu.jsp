<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Меню продавца</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="content">
        <h2>Меню продавца</h2>

        <details>
            <summary>Добавить новую заявку</summary>
            <div class="form-container">
                <form action="create-sell-request" method="post">
                    <input type="hidden" name="seller_id" value="${user.id}" />

                    <label for="name">Название:</label><br>
                    <input type="text" id="name" name="name" required style="width: 100%; margin-bottom: 10px;"><br>

                    <label for="description">Описание:</label><br>
                    <textarea id="description" name="description" required style="width: 100%; margin-bottom: 10px;"></textarea><br>

                    <label for="price">Цена:</label><br>
                    <input type="number" id="price" name="price" required style="width: 100%; margin-bottom: 10px;"><br>

                    <button type="submit">Создать заявку</button>
                </form>
            </div>
        </details>

        <h3>Мои заявки на продажу</h3>
        <c:if test="${empty myRequests}">
            <p>Вы ещё не создали ни одной заявки</p>
        </c:if>
        <c:if test="${not empty myRequests}">
            <c:forEach items="${myRequests}" var="r">
                <div class="request">
                    <h4>${r.name}</h4>
                    <p>${r.description}</p>
                    <p>Цена: ${r.price} руб.</p>
                </div>
            </c:forEach>
        </c:if>

        <h3>Отклики на мои заявки</h3>
        <c:if test="${empty allOrders}">
            <p>Пока никто не откликнулся</p>
        </c:if>
        <c:if test="${not empty allOrders}">
            <ul class="order-list">
                <c:forEach var="o" items="${allOrders}">
                    <li class="order-item">
                        <h4>Пользователь ID: ${o.buyerId}</h4>
                        <p>Имя: ${o.buyerFullName}</p>
                        <p>Email: ${o.buyerEmail}</p>
                        <p>Заявка ID: ${o.sellRequestId}</p>
                        <p class="status">Статус: ${o.status}</p>

                        <!-- Форма для изменения статуса -->
                        <form action="update-order-status" method="post" style="margin-top: 5px;">
                            <input type="hidden" name="order_id" value="${o.id}">

                            <button type="submit" name="action" value="CONFIRMED" style="margin-right: 10px;">Подтвердить</button>
                            <button type="submit" name="action" value="REJECTED">Отклонить</button>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</body>
</html>
