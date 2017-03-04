<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Employees of the department: ${department.name}</title>
    </head>
    <body>
        <h1>Employees of the department:<br/>${department.name}</h1>
                <table>
                <thead>
                	<tr bgcolor="gray">
                		<td width="20">Id</td>
                		<td width="350">Full name</td>
                		<td>Date of birthday</td>
                        <td>Salary</td>
                		<td></td>
                		<td></td>
                	</tr>
                </thead>
                <tbody>
                    <c:forEach var="employee" items="${employees}">
                	<tr>
                		<td>${employee.id}</td>
                		<td>${employee.full_name}</td>
                		<td>${employee.date_of_birth}</td>
                        <td>${employee.salary}</td>
                		<td><a href="/departmentweb/departments/${employee.department_id}/edit-employee/${employee.id}">Edit</a></td>
                		<td><a href="/departmentweb/departments/${employee.department_id}/delete-employee/${employee.id}">Delete</a></td>
                	</tr>
                	</c:forEach>
                </tbody>
                </table>
        <br/>
        <a href="/departmentweb/departments">Back</a> | <a href="/departmentweb/departments/${department.id}/add-employee">New employee</a>
    </body>
</html>