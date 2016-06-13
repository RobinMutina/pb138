<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
    <h3>Zoznam druhov práce</h3>

    <div class="list">
        <table>
            <c:forEach items="${jobTypes}" var="jobType">
            <c:set value="${jobType.id}" var="jtid"/>
                <form action="${pageContext.request.contextPath}/jobtypes/update" method="post">
                    <tr>
                    <td><input type="text" value="${jobType.name}"/></td>
                    <td><input type="text" value="${jobType.pricePerHour}"/></td>
                    <td><input name="upadate" class="button" type="submit" value="Update"/></td>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </div>

    <h3>Pridanie nového druhu práce</h3>

    <form action="${pageContext.request.contextPath}/jobtypes/add" method="post">
        <span>Názov:</span><input type="text" name="jobName"><br>
        <span>Mzda za hodinu:</span><input type="text" name="jobPrice"><br>
        <input class="button" type="submit" value="Pridaj">
    </form>

<%@include file="footer.jsp" %>