<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h2>Register</h2>
<form action="register" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <label for="role">Role:</label>
    <select id="role" name="role" required>
        <option value="EMPLOYEE">Buyer</option>
        <option value="EMPLOYER">Seller</option>
        <option value="ADMIN">Admin</option>
    </select><br>

    <div id="employee-fields">
        <label for="buyerFullName">Full Name:</label>
        <input type="text" id="buyerFullName" name="buyerFullName"><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email"><br>
    </div>
    <div id="employer-fields" style="display:none;">
        <label for="sellerFullName">Contact Person:</label>
        <input type="text" id="sellerFullName" name="sellerFullName"><br>

        <label for="companyName">Company Name:</label>
        <input type="text" id="companyName" name="companyName"><br>
    </div>
    <button type="submit">Register</button>
</form>

<script>
    document.getElementById('role').addEventListener('change', function () {
        var employeeFields = document.getElementById('employee-fields');
        var employerFields = document.getElementById('employer-fields');
        if (this.value === 'EMPLOYEE') {
            employeeFields.style.display = 'block';
            employerFields.style.display = 'none';
        } else if (this.value === 'EMPLOYER') {
            employeeFields.style.display = 'none';
            employerFields.style.display = 'block';
        } else {
            employeeFields.style.display = 'none';
            employerFields.style.display = 'none';
        }
    });
</script>
</body>
</html>