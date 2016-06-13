<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<h3>Fakturácia za obdobie</h3>
<form action="${pageContext.request.contextPath}/convert/create" method="post">
    <span>Začiatok obdobia:</span><input type="datetime-local" name="from"><br>
    <span>Koniec obdobia:</span><input type="datetime-local" name="to">
    <input class="button" type="submit" value="Vyfaktúruj">
</form>
<%@include file="footer.jsp" %>