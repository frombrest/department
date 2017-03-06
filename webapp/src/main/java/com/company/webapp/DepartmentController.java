package com.company.webapp;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.service.WebAppService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Created by frombrest on 24.2.17.
 */

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final static Logger logger = LogManager.getLogger(DepartmentController.class);

    @Autowired
    private WebAppService webappService;

    @ModelAttribute("department")
    public Department addDepartment() {
        return new Department();
    }

    @ModelAttribute("employee")
    public Employee addEmployee() {
        return new Employee();
    }

    @RequestMapping
    public String allDepartments(Model model) {
        model.addAttribute("departments", webappService.getDepartments());
        model.addAttribute("aversalary", webappService.getAverageSalary());
        return "departments";
    }

    @RequestMapping(value = "delete-department/{id}")
    public String deleteDepartment(@PathVariable int id){
        webappService.deleteDepartmentById(id);
        return "redirect:/departments";
    }

    @RequestMapping(value = "{dep_id}/delete-employee/{emp_id}")
    public String deleteEmployee(@PathVariable int dep_id, @PathVariable int emp_id){
        webappService.deleteEmployeeById(emp_id);
        return "redirect:/departments/"+dep_id;
    }

    @RequestMapping(value = "{id}")
    public String showEmployees(@PathVariable int id, Model model){
        model.addAttribute("employees", webappService.getEmployeesByDepartmentId(id));
        model.addAttribute("department", webappService.getDepartmentById(id));
        return "employees";
    }

    @RequestMapping(value = "{id}", params = "date_of_birth")
    public String showEmployees(@PathVariable int id, Model model, @RequestParam("date_of_birth") String date_of_birth){
        if (date_of_birth.length()==0)
            return "redirect:/departments/"+id;
        model.addAttribute("date_of_birth", date_of_birth);
        model.addAttribute("employees", webappService.searchEmployeesByDateOfBirth(id, date_of_birth));
        model.addAttribute("department", webappService.getDepartmentById(id));
        return "employees";
    }


    @RequestMapping(value = "{id}", params = {"date_from","date_to"})
    public String showEmployees(@PathVariable int id, Model model, @RequestParam("date_from") String date_from, @RequestParam("date_to") String date_to){
        if ((date_from.length()==0) & (date_to.length()==0))
            return "redirect:/departments/"+id;
        model.addAttribute("date_from", date_from);
        model.addAttribute("date_to", date_to);
        model.addAttribute("employees", webappService.searchEmployeesByIntervalOfBirthdates(id, date_from, date_to));
        model.addAttribute("department", webappService.getDepartmentById(id));
        return "employees";
    }

    @RequestMapping(value = "add-department", method = RequestMethod.GET)
    public String newDepartment(){
        return "add-department";
    }

    @RequestMapping(value = "add-department", method = RequestMethod.POST)
    public String newDepartment(@ModelAttribute("department") Department department) {
        webappService.createDepartment(department);
        return "redirect:/departments";
    }

    @RequestMapping(value = "edit-department/{id}", method = RequestMethod.GET)
    public String editDepartment(Model model, @PathVariable int id) {
        model.addAttribute("dep", webappService.getDepartmentById(id));
        return "edit-department";
    }

    @RequestMapping(value = "edit-department", method = RequestMethod.POST)
    public String editDepartment(@ModelAttribute("department") Department department) {
        webappService.updateDepartment(department);
        return "redirect:/departments";
    }

    @RequestMapping(value = "{dep_id}/edit-employee/{emp_id}", method = RequestMethod.GET)
    public String editEmployee(Model model, @PathVariable int dep_id, @PathVariable int emp_id) {
        model.addAttribute("employee", webappService.getEmployeeById(emp_id));
        model.addAttribute("departments", webappService.getDepartments());
        return "edit-employee";
    }

    @RequestMapping(value = "{dep_id}/edit-employee/{emp_id}", method = RequestMethod.POST)
    public String editEmployee(@ModelAttribute("employee") Employee employee, @PathVariable int dep_id, @PathVariable int emp_id) {
        webappService.updateEmployee(employee);
        return "redirect:/departments/" + dep_id;
    }

    @RequestMapping(value = "{dep_id}/add-employee", method = RequestMethod.GET)
    public String newEmployee(Model model, @PathVariable int dep_id){
        model.addAttribute("departments", webappService.getDepartments());
        model.addAttribute("current_department", dep_id);
        return "add-employee";
    }

    @RequestMapping(value = "{dep_id}/add-employee", method = RequestMethod.POST)
    public String newEmployee(@ModelAttribute("employee") Employee employee, Model model, @PathVariable int dep_id){
        webappService.createEmployee(employee);
        return "redirect:/departments/" + dep_id;
    }

}
