<%@ include file="header.jsp"%>
<h2>Мои заявки на продажу</h2>
<c:if test="${empty myRequests}">
    <p>Вы ещё не создали ни одной заявки</p>
</c:if>
<c:if test="${not empty myRequests}">
    <c:forEach items="${myRequests}" var="r">
        <div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
            <h3>${r.name}</h3>
            <p>${r.description}</p>
            <p>Цена: ${r.price} руб.</p>
        </div>
    </c:forEach>
</c:if>

<h2>Отклики на мои заявки</h2>
<c:if test="${empty allOrders}">
    <p>Пока никто не откликнулся</p>
</c:if>
<c:if test="${not empty allOrders}">
    <ul>
        <c:forEach var="o" items="${allOrders}">
            <li>Пользователь ID: ${o.buyerId}, Заявка ID: ${o.sellRequestId}, статус: ${o.status}</li>
        </c:forEach>
    </ul>
</c:if>