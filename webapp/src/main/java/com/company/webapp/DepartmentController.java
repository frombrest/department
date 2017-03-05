package com.company.webapp;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.service.WebAppService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @RequestMapping(value = "{id}", params = "date_of_birth")
    public String showEmployees(@PathVariable int id, Model model, @RequestParam("date_of_birth") String date_of_birth){
        logger.debug("Show employees form department:" + id + " with filter by date of birth:" + date_of_birth);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Employee[] employees = restTemplate.getForObject("http://localhost:8080/departmentrest/employee/?department=" + id, Employee[].class);
        Department department = restTemplate.getForObject("http://localhost:8080/departmentrest/department/" + id, Department.class);
        if (date_of_birth.length()==0)
            return "redirect:/departments/"+id;
        List<Employee> result = new ArrayList<Employee>();
        for (Employee employee:employees){
            if (employee.getDate_of_birth().toString().equals(date_of_birth)){
                result.add(employee);
            }
        }
        model.addAttribute("date_of_birth", date_of_birth);
        model.addAttribute("employees", result);
        model.addAttribute("department", department);
        return "employees";
    }


    @RequestMapping(value = "{id}", params = {"date_from","date_to"})
    public String showEmployees(@PathVariable int id, Model model, @RequestParam("date_from") String date_from, @RequestParam("date_to") String date_to){

        logger.debug("Show employees form department:" + id + " with filter by interval of birth dates from:" + date_from + " to:"+ date_to);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Employee[] employees = restTemplate.getForObject("http://localhost:8080/departmentrest/employee/?department=" + id, Employee[].class);
        Department department = restTemplate.getForObject("http://localhost:8080/departmentrest/department/" + id, Department.class);
        List<Employee> result = new ArrayList<Employee>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if ((date_from.length()==0) & (date_to.length()==0))
            return "redirect:/departments/"+id;

        if ((date_from.length()==0) & (date_to.length()!=0)){
            //все работники старше чем date_to
            for (Employee employee:employees){
                java.util.Date employerDate = new java.util.Date();
                java.util.Date toDate = new java.util.Date();
                try {
                    employerDate = sdf.parse(employee.getDate_of_birth().toString());
                    toDate = sdf.parse(date_to);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                logger.debug(employerDate.compareTo(toDate));
                if (employerDate.compareTo(toDate)<=0){
                    result.add(employee);
                }
            }
            model.addAttribute("date_to", date_to);
        } else if ((date_from.length()!=0) & (date_to.length()==0)){
            //все работники младше чем date_from
            for (Employee employee:employees){
                java.util.Date employerDate = new java.util.Date();
                java.util.Date fromDate = new java.util.Date();
                try {
                    employerDate = sdf.parse(employee.getDate_of_birth().toString());
                    fromDate = sdf.parse(date_from);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (employerDate.compareTo(fromDate)>=0){
                    result.add(employee);
                }
            }
            model.addAttribute("date_from", date_from);
        } else {
            //все работники в промежутке от date_from до date_to
            for (Employee employee:employees){
                java.util.Date employerDate = new java.util.Date();
                java.util.Date toDate = new java.util.Date();
                java.util.Date fromDate = new java.util.Date();
                try {
                    employerDate = sdf.parse(employee.getDate_of_birth().toString());
                    fromDate = sdf.parse(date_from);
                    toDate = sdf.parse(date_to);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if ((employerDate.compareTo(fromDate)>=0) & (employerDate.compareTo(toDate)<=0)){
                    result.add(employee);
                }
            }
            model.addAttribute("date_from", date_from);
            model.addAttribute("date_to", date_to);
        }
        model.addAttribute("employees", result);
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

    @RequestMapping(value = "{dep_id}/edit-employee/{emp_id}", method = RequestMethod.POST)
    public String editEmployee(@ModelAttribute("employee") Employee employee, @PathVariable int dep_id, @PathVariable int emp_id) {
        RestTemplate restTemplate = new RestTemplate();
        logger.debug("Edit employee with id:" + emp_id + " form department: " + dep_id);
        restTemplate.put("http://localhost:8080/departmentrest/employee/", employee, Employee.class);
        return "redirect:/departments/" + dep_id;
    }

    @RequestMapping(value = "{dep_id}/add-employee", method = RequestMethod.GET)
    public String newEmployee(Model model, @PathVariable int dep_id){
        logger.debug("Add employee");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Department[] departments = restTemplate.getForObject("http://localhost:8080/departmentrest/department", Department[].class);
        model.addAttribute("departments", departments);
        model.addAttribute("current_department", dep_id);
        return "add-employee";
    }

    @RequestMapping(value = "{dep_id}/add-employee", method = RequestMethod.POST)
    public String newEmployee(@ModelAttribute("employee") Employee employee, Model model, @PathVariable int dep_id){
        logger.debug("Add employee");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject("http://localhost:8080/departmentrest/employee/", employee, Employee.class);
        return "redirect:/departments/" + dep_id;
    }



}
