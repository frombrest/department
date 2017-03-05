<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Departments</title>
    </head>
    <body>
        <h1>Departments</h1>
                <table>
                <thead>
                	<tr bgcolor="gray">
                		<td width="20">Id</td>
                		<td width="350">Department</td>
                		<td>Average salary</td>
                		<td></td>
                		<td></td>
                	</tr>
                </thead>
                <tbody>
                    <c:forEach var="dep" items="${ldep}">
                	<tr>
                		<td>${dep.id}</td>
                		<td><a href="/departmentweb/departments/${dep.id}">${dep.name}</a></td>
                		<td><c:out value="${aversalary[(dep.id).intValue()]}"/></td>
                		<td><a href="/departmentweb/departments/edit-department/${dep.id}">Edit</a></td>
                		<td><a href="/departmentweb/departments/delete-department/${dep.id}">Delete</a></td>
                	</tr>
                	</c:forEach>
                </tbody>
                </table>
        <br/>
        <a href="/departmentweb/departments/add-department">New department</a>
    </body>
</html>


