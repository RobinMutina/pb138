<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <h3>Pridanie odpracovanej práce</h3>
        <form action="">

            <span>Odoberateľ</span>
            <select name="userName">
                <c:forEach items="${users}" var="user">
                    <option value="${user.id}">${user.name}</option>
                </c:forEach>
            </select>
            <br/>
            <span>Typ práce:</span>
            <select name="jobTypeId">
                <c:forEach items="${jobtypes}" var="jobtype">
                    <option value="${jobtype.id}">${jobtype.name}</option>
                </c:forEach>
            </select>
            <br/>
            <span>Začiatok práce:</span><input type="datetime-local" value="Submit"><br>
            <span>Koniec práce:</span><input type="datetime-local" value="Submit"><br>
            <input class="button" type="submit" value="Pridaj">
        </form>

<%@include file="footer.jsp" %>