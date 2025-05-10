<c:forEach items="${orders}" var="o">
    <div>
        <p>Заказ #${o.id}</p>
        <form action="manage-orders" method="post">
            <input type="hidden" name="order_id" value="${o.id}">
            <button type="submit" name="action" value="confirm">Подтвердить</button>
            <button type="submit" name="action" value="reject">Отклонить</button>
        </form>
    </div>
</c:forEach>