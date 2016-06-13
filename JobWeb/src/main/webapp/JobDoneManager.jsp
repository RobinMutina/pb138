<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <c:if test="${not empty ErrMsg}">
            <div style="border: solid 1px red; background-color: yellow; padding: 10px">
                <c:out value="${ErrMsg}"/>
            </div>
        </c:if>
        <c:if test="${not empty ScsMsg}">
                    <div style="background-color: green; padding: 10px">
                        <c:out value="${ScsMsg}"/>
                    </div>
                </c:if>

        <h3>Pridanie odpracovanej práce</h3>
        <form action="${pageContext.request.contextPath}/jobmanager/add" method="post">

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
            <span>Začiatok práce:</span><input name="start" type="datetime-local" value="Submit"><br>
            <span>Koniec práce:</span><input name="end" type="datetime-local" value="Submit"><br>
            <input class="button" type="submit" value="Pridaj">
        </form>

<%@include file="footer.jsp" %>