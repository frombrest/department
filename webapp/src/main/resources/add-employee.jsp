<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>New employee</title>
    </head>
    <body>
        <h1>New employee</h1>
               <f:form method="POST" commandName="department" action="/departmentweb/departments/${current_department}/add-employee" accept-charset="UTF-8" modelAttribute="employee">
            <table>
                <tr>
                    <td>
                        <i>Name:</i>
                    </td>
                    <td>
                        <f:input path="full_name" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Date of birth:</i>
                    </td>
                    <td>
                        <f:input path="date_of_birth" value="1950-06-06"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Salary:</i>
                    </td>
                    <td>
                        <f:input path="salary" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Department:</i>
                    </td>
                    <td>
                        <f:select path="department_id">
                        <c:forEach var="department" items="${departments}">
                            <option value="${department.id}" ${(department.id==current_department)?"selected":""}>${department.name}</option>
                        </c:forEach>
                        </f:select>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Create"/></td>
                </tr>
            </table>
        </f:form>
        <br/>
        <a href="/departmentweb/departments/${current_department}">Cancel</a>
    </body>
</html>