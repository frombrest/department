package com.company.service;

import com.company.dao.EmployeeDAO;
import com.company.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * This class implements methods for working
 * with employees used in the operation
 * of the web service
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
@Service(value = "employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    /**
     * Field for injection DAO bean
     */
    @Resource(name = "employeeDAO")
    private EmployeeDAO employeeDAO;

    /**
     * Method is intended for injection DAO bean
     * @param employeeDAO
     */
    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    /**
     * Method returns a list of all employees from the storage
     * @return list of all entity employees
     */
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAll();
    }

    /**
     * Method returns a list of all employees from the the target department
     * @param id of the target department
     * @return list of entity employees
     */
    public List<Employee> getEmployeesByDepartmentId(int id) {
        return employeeDAO.getByDepartmentId(id);
    }

    /**
     * Method searches the employee entity with the same id in storage
     * @param id of the target employee
     * @return employee entity
     */
    public Employee getEmployeeById(int id) {
        return employeeDAO.getById(id);
    }

    /**
     * Method creates a employee in the storage
     * @param employee entity of the created employee
     */
    public void addEmployee(Employee employee) {
        employeeDAO.create(employee);
    }

    /**
     * Method makes changes to employee in the storage
     * @param employee entity of the modified employee
     */
    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    /**
     * Method remove employee with the same id from the storage
     * @param id of the deletable employee
     */
    public void deleteEmployee(int id) {
        employeeDAO.delete(id);
    }
}
