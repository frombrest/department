package com.company.dao;

import com.company.model.Employee;
import org.springframework.dao.DataAccessException;
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
    Employee getById(int id) throws DataAccessException;

    /**
     * Method returns a list of all employees from the the target department
     * @param id of the target department
     * @return list of entity employees
     */
    List<Employee> getByDepartmentId(int id) throws DataAccessException;

    /**
     * Method returns a list of all employees from the storage
     * @return list of all entity employees
     */
    List<Employee> getAll() throws DataAccessException;

    /**
     * Method remove employee with the same id from the storage
     * @param id of the deletable employee
     */
    void delete(int id) throws DataAccessException;

    /**
     * Method creates a employee in the storage
     * @param employee entity of the created employee
     */
    void create(Employee employee) throws DataAccessException;

    /**
     * Method makes changes to employee in the storage
     * @param employee entity of the modified employee
     */
    void update(Employee employee) throws DataAccessException;
}
