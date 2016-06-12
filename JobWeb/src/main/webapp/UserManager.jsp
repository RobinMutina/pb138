<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h3>Pridanie nového používateľa</h3>
<form action="${pageContext.request.contextPath}/users/add" method="post">
    <span>Meno:</span><input type="text" name="userName">
    <input class="button" type="submit" value="Pridaj">
</form>


<h3>Zoznam používateľov</h3>
<div class="list"></div>
<ol>
    <c:forEach items="${guests}" var="user">
        <li> <c:out value="${user.name}"/> </li>
    </c:forEach>
</ol>
<%@include file="footer.jsp" %>