<c:forEach items="${requests}" var="r">
    <div>
        <h3>${r.name}</h3>
        <p>${r.description}</p>
        <p>Цена: ${r.price} руб.</p>
        <form action="place-order" method="post">
            <input type="hidden" name="buyer_id" value="${user.id}">
            <input type="hidden" name="request_id" value="${r.id}">
            <button type="submit">Заказать</button>
        </form>
    </div>
</c:forEach>