<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<h3>Zoznam odoberateľov</h3>
<p>Po kliknutí na odoberateľa mu môžete priamo vykázať prácu</p>
<div class="list">
    <ol>
        <c:forEach items="${users}" var="user">
            <c:set value="${user.id}" var="uid"/>
            <li>
                <a href="<c:url value="${pageContext.request.contextPath}/user"><c:param name="id" value="${uid}"/></c:url>">
                    <c:out value="${user.name}"/>
                </a>
            </li>
        </c:forEach>
    </ol>
</div>

<h3>Pridať nového odoberateľa</h3>

<form action="${pageContext.request.contextPath}/users/add" method="post">
    <span>Meno:</span><input type="text" name="userName">
    <input class="button" type="submit" value="Pridaj">
</form>


<%@include file="footer.jsp" %>