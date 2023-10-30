<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h3>Player info</h3>
<br>

<form:form action="registration" modelAttribute="playerDto" method="post">
    <br>
    Name <form:input path="name"/>
    <br><br>
    Surname <form:input path="surname"/>
    <br><br>
    Email <form:input path="email"/>
    <br><br>
    Login <form:input path="login"/>
    <br><br>
    Password <form:input path="password"/>
    <br><br>
    <input type="submit" value="Register">
</form:form>

</body>
</html>