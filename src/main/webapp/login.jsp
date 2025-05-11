<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход / Регистрация</title>
</head>
<body>
    <c:if test="${not empty error}">
        <div style="color:red;">
            ${error}
        </div>
    </c:if>
    <h2>Вход</h2>
    <form action="login" method="post">
        <label>Логин:<br>
            <input type="text" name="username" required><br>
        </label>
        <label>Пароль:<br>
            <input type="password" name="password" required><br>
        </label>
        <button type="submit">Войти</button>
    </form>

    <hr>

    <h2>Регистрация</h2>
    <form action="register" method="post">
        <label>Логин:<br>
            <input type="text" name="username" required><br>
        </label>
        <label>Пароль:<br>
            <input type="password" name="password" required><br>
        </label>
        <label>Роль:<br>
            <select name="role" required>
                <option value="BUYER">Покупатель</option>
                <option value="SELLER">Продавец</option>
            </select><br>
        </label>

        <!-- Поля для покупателя -->
        <div id="buyerFields">
            <label>ФИО:<br>
                <input type="text" name="buyerFullName"><br>
            </label>
            <label>Email:<br>
                <input type="email" name="email"><br>
            </label>
        </div>

        <!-- Поля для продавца -->
        <div id="sellerFields" style="display:none;">
            <label>Контактное лицо:<br>
                <input type="text" name="sellerFullName"><br>
            </label>
            <label>Название компании:<br>
                <input type="text" name="companyName"><br>
            </label>
        </div>

        <button type="submit">Зарегистрироваться</button>
    </form>

    <script>
        document.querySelector('select[name="role"]').addEventListener('change', function () {
            var buyer = document.getElementById('buyerFields');
            var seller = document.getElementById('sellerFields');

            if (this.value === 'BUYER') {
                buyer.style.display = 'block';
                seller.style.display = 'none';
            } else if (this.value === 'SELLER') {
                buyer.style.display = 'none';
                seller.style.display = 'block';
            }
        });
    </script>
</body>
</html>