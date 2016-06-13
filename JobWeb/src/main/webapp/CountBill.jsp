<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<h3>Fakturácia za obdobie</h3>
<form action="">
    <span>Odoberateľ:</span><input type="text" name="userName">
</form>
<form action="">
    <span>Začiatok obdobia:</span><input type="datetime-local" value="Submit"><br>
    <span>Koniec obdobia:</span><input type="datetime-local" value="Submit">
</form>
<input class="button"type="submit" value="Vyfaktúruj">
<%@include file="footer.jsp" %>