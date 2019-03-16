Department
===================

The project consists of two cooperating applications, designed to work with information about employees and departments.

**departmentrest.war** — REST service providing access to a database of employees and departments.

**departmentweb.war** — Web application providing an interface for working with the REST service.

Usage
-----
Build the project:

    $ mvn package

Access addresses:

**departmentrest.war**

    http://localhost:8080/departmentrest/department
    http://localhost:8080/departmentrest/employee

**departmentweb.war**

    http://localhost:8080/departmentweb/departments
