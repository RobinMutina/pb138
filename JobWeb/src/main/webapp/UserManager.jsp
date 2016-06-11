<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%@ page import="cz.muni.fi.pb138.project.Entities.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<h3>Pridanie nového používateľa</h3>
<form action="">
    <span>Meno:</span><input type="text" name="userName">
</form>
<input class="button" type="submit" value="Pridaj">

<h3>Zoznam používateľov</h3>
<div class="list"></div>
<ol> <%
    @SuppressWarnings("unchecked")
    List<User> users = (List<User>)request.getAttribute("users");
    if (users != null) {
        for (User user : users) { %>
    <li> <%= user %> </li> <%
            }
        } %>
</ol>
<%@include file="footer.jsp" %>