<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>

<html>
<head>
    <title>Меню покупателя</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="content">
        <h2>Мои покупки</h2>
        <c:if test="${empty orders}">
            <p>У вас ещё нет заказов</p>
        </c:if>
        <c:if test="${not empty orders}">
            <ul class="order-list">
                <c:forEach var="o" items="${orders}">
                    <li class="order-item">
                        <h4>Заявка ID: ${o.sellRequestId}</h4>
                        <p class="status">Статус: ${o.status}</p>
                        <p>
                            <!-- Ищем заявку с этим ID -->
                            <c:forEach var="request" items="${allRequests}">
                                <c:if test="${request.id == o.sellRequestId}">
                                    Название товара: ${request.name}
                                </c:if>
                            </c:forEach>
                        </p>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</body>
</html>
