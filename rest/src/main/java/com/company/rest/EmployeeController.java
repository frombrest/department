package com.company.rest;

import com.company.model.Employee;
import com.company.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final static Logger logger = LogManager.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @RequestMapping(params = "department", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Employee> getEmployeesByDepartmentId(@RequestParam("department") int id) {
        return employeeService.getEmployeesByDepartmentId(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Employee getEmployeeById(@PathVariable int id) {
        return employeeService.getEmployeeById(id);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public void addEmployee(@RequestBody Employee employee) {
        employeeService.addEmployee(employee);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public void updateEmployee(@RequestBody Employee employee) {
        employeeService.updateEmployee(employee);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);
    }
}
