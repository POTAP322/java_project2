<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>

<h2>Мои покупки</h2>
<c:if test="${empty orders}">
    <p>У вас ещё нет заказов</p>
</c:if>
<c:if test="${not empty orders}">
    <ul>
        <c:forEach var="o" items="${orders}">
            <li>
                Заявка ID: ${o.sellRequestId},
                Статус: ${o.status} ;

                <!-- Ищем заявку с этим ID -->
                <c:forEach var="request" items="${allRequests}">
                    <c:if test="${request.id == o.sellRequestId}">
                        Название товара: ${request.name}
                    </c:if>
                </c:forEach>
            </li>
        </c:forEach>
    </ul>
</c:if>