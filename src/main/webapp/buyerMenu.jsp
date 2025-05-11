<%@ include file="header.jsp"%>
<h2>Мои покупки</h2>
<c:if test="${empty orders}">
    <p>У вас ещё нет заказов</p>
</c:if>
<c:if test="${not empty orders}">
    <ul>
        <c:forEach var="o" items="${orders}">
            <li>Заявка ID: ${o.sellRequestId}, статус: ${o.status}</li>
        </c:forEach>
    </ul>
</c:if>