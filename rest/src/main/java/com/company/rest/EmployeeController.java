package com.company.rest;

import com.company.model.Employee;
import com.company.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Class applies the REST-Controller of the Spring
 * Web MVC library to implement CRUD operations
 * with employee objects
 *
 * Implicitly uses the Jackson-databind library for
 * converting objects to JSON format
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(EmployeeController.class);

    /**
     * Field for injection service-layer bean
     */
    @Autowired
    private EmployeeService employeeService;

    /**
     * Method processes the HTTP.GET request at
     * '/employee' and return list of all employees
     *
     * @return List of all employees in JSON
     */
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    /**
     *  Method processes the HTTP.GET request at
     *  '/employee?department={int id}' and return
     *  list of all employees from department
     *  with the same id
     *
     * @param id ID of the target department
     * @return List of employees in JSON
     */
    @RequestMapping(params = "department", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Employee> getEmployeesByDepartmentId(@RequestParam("department") int id) {
        return employeeService.getEmployeesByDepartmentId(id);
    }

    /**
     *  Method processes the HTTP.GET request at
     *  '/employee/{int id}' and return employee
     *  with the same id
     *
     * @param id ID of the target employee
     * @return Employee entity in JSON
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    /**
     * Method processes the HTTP.POST request at
     * '/employee' and create new employee
     *
     * @param employee Employee entity in JSON
     */
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    /**
     * Method processes the HTTP.PUT request at
     * '/employee' and update employee
     *
     * @param employee Employee entity in JSON
     */
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public void updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
    }

    /**
     * Method processes the HTTP.DELETE request at
     * '/employee/{int id}' and delete employee
     *  with the same id
     *
     * @param id ID of the target employee
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
    }
}
