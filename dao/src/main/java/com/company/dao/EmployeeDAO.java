package com.company.dao;

import com.company.model.Employee;
import java.util.List;

/**
 * The interface describes the methods for
 * working with the storage of employees
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

public interface EmployeeDAO {

    /**
     * Method searches the employee entity with the same id in storage
     * @param id of the target employee
     * @return employee entity
     */
    public Employee getById(int id);

    /**
     * Method returns a list of all employees from the the target department
     * @param id of the target department
     * @return list of entity employees
     */
    public List<Employee> getByDepartmentId(int id);

    /**
     * Method returns a list of all employees from the storage
     * @return list of all entity employees
     */
    public List<Employee> getAll();

    /**
     * Method remove employee with the same id from the storage
     * @param id of the deletable employee
     */
    public void delete(int id);

    /**
     * Method creates a employee in the storage
     * @param employee entity of the created employee
     */
    public void create(Employee employee);

    /**
     * Method makes changes to employee in the storage
     * @param employee entity of the modified employee
     */
    public void update(Employee employee);
}
