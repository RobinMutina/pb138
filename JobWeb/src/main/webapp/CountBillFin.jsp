<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<h3>Fakturá byla vygenerovana</h3>
<form action="${pageContext.request.contextPath}/convert/download" method="post">
    <input class="button" type="submit" value="Stahnout">
</form>
<%@include file="footer.jsp" %>