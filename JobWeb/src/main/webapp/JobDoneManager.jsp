<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
        <h3>Pridanie odpracovanej práce</h3>
        <form action="">
            <span>Užívateľské ID:</span><input type="number" name="userName">
        </form>
        <form action="">
            <span>Typ práce:</span><input type="text" name="jobType">
        </form>
        <form action="">
            <span>Začiatok práce:</span><input type="datetime-local" value="Submit"><br>
            <span>Koniec práce:</span><input type="datetime-local" value="Submit">
        </form>
        <input class="button" type="submit" value="Pridaj">
<%@include file="footer.jsp" %>