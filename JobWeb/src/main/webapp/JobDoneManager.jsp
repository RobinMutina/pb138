<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <c:if test="${not empty ErrMsg}">
            <div class="error">
                <c:out value="${ErrMsg}"/>
            </div>
        </c:if>
        <c:if test="${not empty ScsMsg}">
                    <div class="succes">
                        <c:out value="${ScsMsg}"/>
                    </div>
                </c:if>

        <h3>Pridanie odpracovanej práce</h3>
        <form action="${pageContext.request.contextPath}/jobmanager/add" method="post">

            <span>Odoberateľ: </span>
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
            <span>Začiatok práce:</span><input name="start" type="datetime-local" value="Submit"><br>
            <span>Koniec práce:</span><input name="end" type="datetime-local" value="Submit"><br>
            <input class="button" type="submit" value="Pridaj">
        </form>

        <ol>
        <c:forEach items="${jobs}" var="job">
            <li>
                <c:out value="${job.user.name}"/>
                <c:out value="${job.jobType.name}"/>
                <input type="datetime-local" value="${job.jobDone.startTime}" disabled/>
                <input type="datetime-local" value="${job.jobDone.endTime}" disabled/>
                </li>
        </c:forEach>
        </ol>

<%@include file="footer.jsp" %>