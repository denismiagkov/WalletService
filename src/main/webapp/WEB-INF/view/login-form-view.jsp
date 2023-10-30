<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h3>Please, enter your login and password</h3>
<br>

<form:form action="authentication" modelAttribute="entryDto" method="post">
    <br>
    Login <form:input path="login"/>
    <br><br>
    Password <form:input path="password"/>
    <br><br>
    <input type="submit" value="Login">
</form:form>

</body>
</html>