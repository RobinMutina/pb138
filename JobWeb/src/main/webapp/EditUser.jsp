<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/user/update" method="post">
    <span>Meno:</span><input type="text" name="userName" value="${user.name}">
    <input type="hidden" name="id" value="${user.id}"/>
    <input class="button" type="submit" value="Edit">
</form>
<!--<form action="${pageContext.request.contextPath}/user/delete" method="post">
    <input type="submit" class="delete" value="delete">
    <input type="hidden" name="id" value="${user.id}"/>
</form>-->

<h3>Výkaz práce</h3>

<div class="">
    <table>
        <c:forEach items="${jobs}" var="job">
            <form action="${pageContext.request.contextPath}/user/deletejob" method="post">
                <input type="hidden" name="id" value="${user.id}"/>
                <input type="hidden" name="jobid" value="${job.key.id}"/>
                <tr>
                    <td><c:out value="${job.value.name}"/></td>
                    <td><input type="datetime-local" value="${job.key.startTime}" disabled/></td>
                    <td><input type="datetime-local" value="${job.key.endTime}" disabled/></td>
                    <td><input class="buttondel" type="submit" value="Delete"/></td>
                </tr>
            </form>
        </c:forEach>
    </table>
</div>

<h3>Nová pracovná položka<h3>

<form action="${pageContext.request.contextPath}/user/addjob" method="post">
    <input type="hidden" name="id" value="${user.id}"/>
    <span>Typ práce:</span>
    <select name="jobTypeId">
        <c:forEach items="${jobtypes}" var="jobtype">
            <option value="${jobtype.id}">${jobtype.name}</option>
        </c:forEach>
    </select>
    <br>
    <span>Začiatok práce:</span><input type="datetime-local" name="from"/><br>
    <span>Koniec práce:</span><input type="datetime-local" name="to"/><br>
    <input class="button" type="submit" value="Add"/>
</form>

<%@include file="footer.jsp" %>