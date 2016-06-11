<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1px">
    <tr>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="Home">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="GuestManager">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="JobManager">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="JobTypeManager">
            </form>
        </th>
        <th>
            <form method="post" action="${pageContext.request.contextPath}"
                  style="margin-bottom: 0;"><input type="submit" value="ConvertToPDF">
            </form>
        </th>
    </tr>
</table>

<

</body>
</html>