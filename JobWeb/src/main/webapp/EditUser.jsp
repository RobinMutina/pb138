<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/user/update" method="post">
    <span>Meno:</span><input type="text" name="userName" value="${user.name}">
    <input type="hidden" name="id" value="${user.id}"/>
    <input class="button" type="submit" value="Edit">
</form>

<%@include file="footer.jsp" %>