<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit employee</title>
    </head>
    <body>
        <h1>Edit employee</h1>
               <f:form method="POST" commandName="department" action="/departmentweb/departments/${employee.department_id}/edit-employee/${employee.id}" accept-charset="UTF-8" modelAttribute="employee">
            <table>
                <tr>
                    <td>
                        <i>Id:</i>
                    </td>
                    <td>
                        <b>${employee.id}</b><f:input path="id" hidden="true" value="${employee.id}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Name:</i>
                    </td>
                    <td>
                        <f:input path="full_name" value="${employee.full_name}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Date of birth:</i>
                    </td>
                    <td>
                        <f:input path="date_of_birth" value="${employee.date_of_birth}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Salary:</i>
                    </td>
                    <td>
                        <f:input path="salary" value="${employee.salary}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Department:</i>
                    </td>
                    <td>
                        <select path="department_id">
                        <c:forEach var="department" items="${departments}">
                            <option value="${department.id}" ${(department.id==employee.department_id)?"selected":""}>${department.name}</option>
                        </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Edit"/></td>
                </tr>
            </table>
        </f:form>
        <br/>

        <a href="/departmentweb/departments/${employee.department_id}">Cancel</a>
    </body>
</html>