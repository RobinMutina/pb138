<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>

     <c:if test="${not empty ErrMsg}">
         <div class="error">
             <c:out value="${ErrMsg}"/>
         </div>
     </c:if>
     <c:if test="${not empty ScsMsg}">
         <div class="succes">>
             <c:out value="${ScsMsg}"/>
         </div>
     </c:if>

    <h3>Zoznam druhov práce</h3>

    <div class="">
        <table style="margin: 0 auto;">
            <c:forEach items="${jobTypes}" var="jobType">
            <c:set value="${jobType.id}" var="jtid"/>
                <form action="${pageContext.request.contextPath}/jobtypes/update" method="post">
                <input type="hidden"  name="jobtypeid" value="${jtid}"/>
                    <tr>
                        <td><input name="jobtypename" type="text" value="${jobType.name}"/></td>
                        <td><input name="jobtypepph" type="text" value="${jobType.pricePerHour}"/></td>
                        <td><input name="formButton" class="button" type="submit" value="Update"/></td>
                        <td><input name="formButton" class="buttondel" type="submit" value="Delete"/></td>
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