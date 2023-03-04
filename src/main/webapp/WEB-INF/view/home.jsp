<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Data list</title>

        <link type="text/css"
              rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/style.css"/>

    </head>
    <body>

        <div id="wrapper">
            <div id="header">
                <h2>Check Match</h2>
            </div>
        </div>

        <div id="container">
            <div id="content">

                <%--input form--%>

                <form:form action="/process" modelAttribute="user" method="POST">
                    <!-- need to associate this data with customer id -->
                    <form:hidden path="id" />
                    <table id="form-table">
                        <tbody>
                            <tr>
                                <td><label>Name:</label></td>
                                <td><form:input path="name" /></td>
                            </tr>
                            <tr>
                                <td><label>Age:</label></td>
                                <td><form:input path="age" /></td>
                            </tr>
                            <tr>
                                <td><label>Address:</label></td>
                                <td><form:input path="address" /></td>
                            </tr>
                            <tr>
                                <td><label>Email:</label></td>
                                <td><form:input path="email" /></td>
                            </tr>
                            <tr>
                                <td><label>Phone:</label></td>
                                <td><form:input path="phone" /></td>
                            </tr>
                            <tr>
                                <td><label></label></td>
                                <td><input type="submit" value="Check" class="add-button"></td>
                            </tr>
                        </tbody>
                    </table>

                </form:form>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Address</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Is Match</th>
                    </tr>
                    <c:forEach var="tempUser" items="${users}">
                        <tr>
                            <td> ${tempUser.name} </td>
                            <td> ${tempUser.age} </td>
                            <td> ${tempUser.address} </td>
                            <td> ${tempUser.email} </td>
                            <td> ${tempUser.phone} </td>
                            <c:if test="${matchIds != null}">
                                <c:forEach var="id" items="${matchIds}">
                                    <c:if test="${tempUser.id == id}">
                                        <td> Match!! </td>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </body>
</html>
