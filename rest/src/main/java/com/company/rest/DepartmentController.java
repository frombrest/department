package com.company.rest;

import com.company.model.Department;
import com.company.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Class applies the REST-Controller of the Spring
 * Web MVC library to implement CRUD operations
 * with department objects
 *
 * Implicitly uses the Jackson-databind library for
 * converting objects to JSON format
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(DepartmentController.class);

    /**
     * Field for injection service-layer bean
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * Method processes the HTTP.GET request at
     * '/department' and return list of all departments
     *
     * @return List of all departments in JSON
     */
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    /**
     *  Method processes the HTTP.GET request at
     *  '/department/{int id}' and return department
     *  with the same id
     *
     * @param id ID of the target department
     * @return Department entity in JSON
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Department getDepartmentById(@PathVariable int id) {
        return departmentService.getDepartmentById(id);
    }

    /**
     * Method processes the HTTP.POST request at
     * '/department' and create new department
     *
     * @param department Department entity in JSON
     */
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public void addDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
    }

    /**
     * Method processes the HTTP.PUT request at
     * '/department' and update department
     *
     * @param department Department entity in JSON
     */
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public void updateDepartment(@RequestBody Department department) {
        departmentService.updateDepartment(department);
    }

    /**
     * Method processes the HTTP.DELETE request at
     * '/department/{int id}' and delete department
     *  with the same id
     *
     * @param id ID of the target department
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteDepartment(@PathVariable("id") int id) {
        departmentService.deleteDepartment(id);
    }


}
