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
 * Class uses the library Sprting Web MVC to implement
 * the Web application interacting with a REST Service
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
@Controller
@RequestMapping("/departments")
public class DepartmentController {

    /**
     * log4j Logger object
     */
    private final static Logger logger = LogManager.getLogger(DepartmentController.class);

    /**
     * Field for injection service-layer bean
     */
    @Autowired
    private WebAppService webappService;

    /**
     * Method for binding department data from request to model
     * @return a new object of department
     */
    @ModelAttribute("department")
    public Department addDepartment() {
        return new Department();
    }

    /**
     * Method for binding employee data from request to model
     * @return a new object of employee
     */
    @ModelAttribute("employee")
    public Employee addEmployee() {
        return new Employee();
    }

    /**
     * Method processes the HTTP.GET request at
     * '/departments' and return table with
     * all departments and average salarys
     *
     * @param model a new Model object for filling the attributes
     * @return View: departments.jsp
     */
    @RequestMapping(method = RequestMethod.GET)
    public String allDepartments(Model model) {
        model.addAttribute("departments", webappService.getDepartments());
        model.addAttribute("aversalary", webappService.getAverageSalary());
        return "departments";
    }

    /**
     * Method processes the HTTP.GET request at
     * '/departments/delete-department/{id}' and
     * delete department with same id
     *
     * @param id ID of the target department
     * @return Redirect to: /departments
     */
    @RequestMapping(value = "delete-department/{id}", method = RequestMethod.GET)
    public String deleteDepartment(@PathVariable int id){
        webappService.deleteDepartmentById(id);
        return "redirect:/departments";
    }

    /**
     * Method processes the HTTP.GET request at
     * '/departments/{dep_id}/delete-employee/{emp_id}'
     * and delete employee with id {emp_id} from department
     * with id {dep_id}
     *
     * @param dep_id ID of the target department
     * @param emp_id ID of the target employee
     * @return Redirect to: /departments/{dep_id}
     */
    @RequestMapping(value = "{dep_id}/delete-employee/{emp_id}", method = RequestMethod.GET)
    public String deleteEmployee(@PathVariable int dep_id, @PathVariable int emp_id){
        webappService.deleteEmployeeById(emp_id);
        return "redirect:/departments/"+dep_id;
    }

    /**
     * Method processes the HTTP.GET request at
     * '/departments/{id}' and return table with
     * all employees from department with id {id}
     *
     * Table contains information about the name,
     * date of birth of the employee and also about
     * his salary
     *
     * @param id ID of the target department
     * @param model a new Model object for filling the attributes
     * @return View: employees.jsp
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public String showEmployees(@PathVariable int id, Model model){
        model.addAttribute("employees", webappService.getEmployeesByDepartmentId(id));
        model.addAttribute("department", webappService.getDepartmentById(id));
        return "employees";
    }

    /**
     * Method processes the HTTP.GET request at
     * '/departments/{id}?date_of_birth={date_of_birth}'
     * and return table with employees from department
     * with id {id} with required {date of birth}
     *
     * Table contains information about the name,
     * date of birth of the employee and also about his salary
     *
     * @param id ID of the target department
     * @param model a new Model object for filling the attributes
     * @param date_of_birth required date of birth
     * @return View: employees.jsp
     */
    @RequestMapping(value = "{id}", params = "date_of_birth", method = RequestMethod.GET)
    public String showEmployees(@PathVariable int id, Model model, @RequestParam("date_of_birth") String date_of_birth){
        if (date_of_birth.length()==0)
            return "redirect:/departments/"+id;
        model.addAttribute("date_of_birth", date_of_birth);
        model.addAttribute("employees", webappService.searchEmployeesByDateOfBirth(id, date_of_birth));
        model.addAttribute("department", webappService.getDepartmentById(id));
        return "employees";
    }


    /**
     * Method processes the HTTP.GET request at
     * '/departments/{id}?date_from={date_from}&date_to={date_to}'
     * and return table with employees from department
     * with id {id} who were born in the period
     * between {date_from} and {date_to}
     *
     * Table contains information about the name,
     * date of birth of the employee and also about his salary
     *
     * @param id ID of the target department
     * @param model a new Model object for filling the attributes
     * @param date_from beginning of a search period
     * @param date_to end of a search period
     * @return View: employees.jsp
     */
    @RequestMapping(value = "{id}", params = {"date_from","date_to"}, method = RequestMethod.GET)
    public String showEmployees(@PathVariable int id, Model model, @RequestParam("date_from") String date_from, @RequestParam("date_to") String date_to){
        if ((date_from.length()==0) & (date_to.length()==0))
            return "redirect:/departments/"+id;
        model.addAttribute("date_from", date_from);
        model.addAttribute("date_to", date_to);
        model.addAttribute("employees", webappService.searchEmployeesByIntervalOfBirthdates(id, date_from, date_to));
        model.addAttribute("department", webappService.getDepartmentById(id));
        return "employees";
    }

    /**
     * Method processes the HTTP.GET request at '/departments/add-department'
     * and return a form to create a new department
     *
     * @return View: add-department.jsp
     */
    @RequestMapping(value = "add-department", method = RequestMethod.GET)
    public String newDepartment(){
        return "add-department";
    }

    /**
     * Method processes the HTTP.POST request at '/departments/add-department'
     * and creates a new department on the basis of data from the form
     *
     * @param department a new object of department
     * @return Redirect to: /departments
     */
    @RequestMapping(value = "add-department", method = RequestMethod.POST)
    public String newDepartment(@ModelAttribute("department") Department department) {
        webappService.createDepartment(department);
        return "redirect:/departments";
    }

    /**
     * Method processes the HTTP.GET request at '/departments/edit-department/{id}'
     * and return a form to edit department with id {id}
     *
     * @param model a new Model object for filling the attributes
     * @param id ID of the target department
     * @return  View: edit-department.jsp
     */
    @RequestMapping(value = "edit-department/{id}", method = RequestMethod.GET)
    public String editDepartment(Model model, @PathVariable int id) {
        model.addAttribute("dep", webappService.getDepartmentById(id));
        return "edit-department";
    }

    /**
     * Method processes the HTTP.POST request at '/departments/edit-department/{id}'
     * and update department on the basis of data from the form
     *
     * @param department edited object of department
     * @return Redirect to: /departments
     */
    @RequestMapping(value = "edit-department", method = RequestMethod.POST)
    public String editDepartment(@ModelAttribute("department") Department department) {
        webappService.updateDepartment(department);
        return "redirect:/departments";
    }

    /**
     * Method processes the HTTP.GET request at '/departments/{dep_id}/edit-employee/{emp_id}'
     * and return a form to edit emplyee with id {emp_id} form department
     * with id {dep_id}
     *
     * @param model a new Model object for filling the attributes
     * @param dep_id ID of the target department
     * @param emp_id ID of the target employee
     * @return  View: edit-employee.jsp
     */
    @RequestMapping(value = "{dep_id}/edit-employee/{emp_id}", method = RequestMethod.GET)
    public String editEmployee(Model model, @PathVariable int dep_id, @PathVariable int emp_id) {
        model.addAttribute("employee", webappService.getEmployeeById(emp_id));
        model.addAttribute("departments", webappService.getDepartments());
        return "edit-employee";
    }

    /**
     * Method processes the HTTP.POST request at '/departments/{dep_id}/edit-employee/{emp_id}'
     * and update employee on the basis of data from the form
     *
     * @param employee edited object of employee
     * @param dep_id ID of the target department
     * @param emp_id ID of the target employee
     * @return Redirect to: /departments/{dep_id}
     */
    @RequestMapping(value = "{dep_id}/edit-employee/{emp_id}", method = RequestMethod.POST)
    public String editEmployee(@ModelAttribute("employee") Employee employee, @PathVariable int dep_id, @PathVariable int emp_id) {
        webappService.updateEmployee(employee);
        return "redirect:/departments/" + dep_id;
    }

    /**
     * Method processes the HTTP.GET request at '/departments/{dep_id}/add-employee'
     * and return a form to create a new employee in department
     * with id {dep_id}
     *
     * @param model a new Model object for filling the attributes
     * @param dep_id ID of the target department
     * @return  View: add-employee.jsp
     */
    @RequestMapping(value = "{dep_id}/add-employee", method = RequestMethod.GET)
    public String newEmployee(Model model, @PathVariable int dep_id){
        model.addAttribute("departments", webappService.getDepartments());
        model.addAttribute("current_department", dep_id);
        return "add-employee";
    }

    /**
     * Method processes the HTTP.POST request at '/departments/{dep_id}/add-employee'
     * and creates a new employee id department with id {dep_id} on the basis
     * of data from the form
     *
     * @param employee a new object of employee
     * @param dep_id ID of the target department
     * @return  Redirect to: /departments/{dep_id}
     */
    @RequestMapping(value = "{dep_id}/add-employee", method = RequestMethod.POST)
    public String newEmployee(@ModelAttribute("employee") Employee employee, @PathVariable int dep_id){
        webappService.createEmployee(employee);
        return "redirect:/departments/" + dep_id;
    }

}
