<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1px">
    <tr>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="Home">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="GuestManager">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="JobManager">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="JobTypeManager">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="ConvertToPDF">
            </form>
        </th>
    </tr>
</table>

<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
    </tr>
    </thead>
    <c:forEach items="${guests}" var="guest">
        <tr>
            <td><c:out value="${guest.id}"/></td>
            <td><c:out value="${guest.name}"/></td>
        </tr>
    </c:forEach>
</table>

<h2>Create guest</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/guests/add" method="post">
    <table>
        <tr>
            <th>Name:</th>
            <td><input type="text" name="name" value="<c:out value='${param.name}'/>"/></td>
        </tr>

    </table>
    <input type="Submit" value="Enter" />
</form>
<h2>Update guest</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
    </tr>
    </thead>
    <c:forEach items="${guests}" var="guest1">
        <tr>
            <td><c:out value="${guest1.id}"/></td>
            <form method="post" action="${pageContext.request.contextPath}/guests/update?id1=${guest1.id}"style="margin-bottom: 0;">
                <td><input type="text" name="name1" value="<c:out value='${param.name1}'/>"/></td>
                <td><input type="submit" value="Update"></td>
            </form>
        </tr>

    </c:forEach>
</table>

</body>
</html>