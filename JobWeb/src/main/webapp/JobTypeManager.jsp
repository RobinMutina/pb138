<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
    <h3>Pridanie nového druhu práce</h3>
    <form action="">
        <span>Názov:</span><input type="text" name="jobName">
    </form>
    <form action="">
        <span>Mzda za hodinu:</span><input type="text" name="jobPrice">
    </form>
    <input class="button" type="submit" value="Pridaj">
    <h3>Zoznam druhov práce</h3>
    <div class="list"></div>
<%@include file="footer.jsp" %>