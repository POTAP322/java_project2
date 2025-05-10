<form action="create-request" method="post">
    <input type="hidden" name="seller_id" value="${user.id}">
    <label for="name">Название:</label>
    <input type="text" id="name" name="name" required><br>
    <label for="description">Описание:</label>
    <textarea id="description" name="description"></textarea><br>
    <label for="price">Цена:</label>
    <input type="number" id="price" name="price" required><br>
    <button type="submit">Опубликовать заявку</button>
</form>