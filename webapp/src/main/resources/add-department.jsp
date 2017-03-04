<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Departments</title>
    </head>
    <body>
        <h1>New department</h1>
               <f:form method="POST" commandName="department" action="add-department" accept-charset="UTF-8">
            <table>
                <tr>
                    <td>
                        <i>Name:</i>
                    </td>
                    <td>
                        <f:input path="name"/>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Create"/></td>
                </tr>
            </table>
        </f:form>
        <br/>
        <a href="/departmentweb/departments">Cancel</a>
    </body>
</html>


