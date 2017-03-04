package com.company.rest;

import com.company.model.Department;
import com.company.service.DepartmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final static Logger logger = LogManager.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Department> getAllDepartments() {
        logger.debug("Get all departments");
        return departmentService.getAllDepartments();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Department getDepartmentById(@PathVariable int id) {
        logger.debug("Get department with id:" + id);
        return departmentService.getDepartmentById(id);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public void addDepartment(@RequestBody Department department) {
        departmentService.addDepartment(department);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public void updateDepartment(@RequestBody Department department) {
        departmentService.updateDepartment(department);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteDepartment(@PathVariable("id") int id) {
        departmentService.deleteDepartment(id);
    }


}
