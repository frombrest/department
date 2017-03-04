<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Departments</title>
    </head>
    <body>
        <h1>Edit department</h1>
               <f:form method="POST" commandName="department" action="/departmentweb/departments/edit-department" accept-charset="UTF-8" modelAttribute="department">
            <table>
                <tr>
                    <td>
                        <i>Id:</i>
                    </td>
                    <td>
                        <b>${dep.id}</b> <f:input path="id" hidden="true" value="${dep.id}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <i>Name:</i>
                    </td>
                    <td>
                        <f:input path="name" value="${dep.name}"/>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Edit"/></td>
                </tr>
            </table>
        </f:form>
        <br/>
        <a href="/departmentweb/departments">Cancel</a>
    </body>
</html>

