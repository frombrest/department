package com.company.service;

import com.company.model.Employee;
import java.util.List;

/**
 * Interface describes methods for working
 * with employees used in the operation
 * of the web service
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
public interface EmployeeService {

    /**
     * Method returns a list of all employees from the storage
     * @return list of all entity employees
     */
    public List<Employee> getAllEmployees();

    /**
     * Method returns a list of all employees from the the target department
     * @param id of the target department
     * @return list of entity employees
     */
    public List<Employee> getEmployeesByDepartmentId(int id);

    /**
     * Method searches the employee entity with the same id in storage
     * @param id of the target employee
     * @return employee entity
     */
    public Employee getEmployeeById(int id);

    /**
     * Method creates a employee in the storage
     * @param employee entity of the created employee
     */
    public void addEmployee(Employee employee);

    /**
     * Method makes changes to employee in the storage
     * @param employee entity of the modified employee
     */
    public void updateEmployee(Employee employee);

    /**
     * Method remove employee with the same id from the storage
     * @param id of the deletable employee
     */
    public void deleteEmployee(int id);
}