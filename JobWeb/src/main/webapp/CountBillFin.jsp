<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<div class="succes">Faktura byla vygenerovana</div>
<form action="${pageContext.request.contextPath}/convert/download" method="post">
    <input class="button" type="submit" value="Stahnout">
</form>
<%@include file="footer.jsp" %>