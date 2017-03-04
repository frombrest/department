package com.company.webapp;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.service.WebAppService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;


/**
 * Created by frombrest on 24.2.17.
 */

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final static Logger logger = LogManager.getLogger(DepartmentController.class);

    @Autowired
    private WebAppService webappService;

    @RequestMapping
    public String allDepartments(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Department[] deps = restTemplate.getForObject("http://localhost:8080/departmentrest/department", Department[].class);
        model.addAttribute("ldep", deps);
        model.addAttribute("aversalary", webappService.getAverageSalary());
        return "departments";
    }

    @RequestMapping(value = "delete-department/{id}")
    public String deleteDepartment(@PathVariable int id){
        logger.debug("Delete department with id:" + id);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/departmentrest/department/" + id);
        return "redirect:/departments";
    }

    @RequestMapping(value = "{dep_id}/delete-employee/{emp_id}")
    public String deleteEmployee(@PathVariable int dep_id, @PathVariable int emp_id){
        logger.debug("Delete employee with id:" + emp_id + " from department " + dep_id);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/departmentrest/employee/" + emp_id);
        return "redirect:/departments/"+dep_id;
    }

    @RequestMapping(value = "{id}")
    public String showEmployees(@PathVariable int id, Model model){
        logger.debug("Show employees form department with id:" + id);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Employee[] employees = restTemplate.getForObject("http://localhost:8080/departmentrest/employee/?department=" + id, Employee[].class);
        Department department = restTemplate.getForObject("http://localhost:8080/departmentrest/department/" + id, Department.class);
        model.addAttribute("employees", employees);
        model.addAttribute("department", department);
        return "employees";
    }

    @RequestMapping(value = "add-department", method = RequestMethod.GET)
    public String newDepartment(){
        logger.debug("Add department");
        return "add-department";
    }

    @ModelAttribute("department")
    public Department addDepartment() {
        return new Department();
    }

    @ModelAttribute("employee")
    public Employee addEmployee() {
        return new Employee();
    }

    @RequestMapping(value = "add-department", method = RequestMethod.POST)
    public String newDepartment(@ModelAttribute("department") Department department) {
        RestTemplate restTemplate = new RestTemplate();
        logger.debug("New department: " + department.getName());
        restTemplate.postForObject("http://localhost:8080/departmentrest/department/", department, Department.class);
        return "redirect:/departments";
    }

    @RequestMapping(value = "edit-department/{id}", method = RequestMethod.GET)
    public String editDepartment(Model model, @PathVariable int id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Department dep = restTemplate.getForObject("http://localhost:8080/departmentrest/department/"+id, Department.class);
        model.addAttribute("dep", dep);
        return "edit-department";
    }

    @RequestMapping(value = "edit-department", method = RequestMethod.POST)
    public String editDepartment(@ModelAttribute("department") Department department) {
        RestTemplate restTemplate = new RestTemplate();
        logger.debug("Edit department with id: " + department.getId() + " and new name: " + department.getName());
        restTemplate.put("http://localhost:8080/departmentrest/department/", department, Department.class);
        return "redirect:/departments";
    }

    @RequestMapping(value = "{dep_id}/edit-employee/{emp_id}", method = RequestMethod.GET)
    public String editEmployee(Model model, @PathVariable int dep_id, @PathVariable int emp_id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Employee employee = restTemplate.getForObject("http://localhost:8080/departmentrest/employee/"+emp_id, Employee.class);
        Department[] departments = restTemplate.getForObject("http://localhost:8080/departmentrest/department", Department[].class);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        return "edit-employee";
    }



}
