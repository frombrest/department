package com.company.service;

import com.company.model.Department;
import com.company.model.Employee;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

/**
 * Interface describes the methods for working
 * with data used in functioning web application
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
public interface WebAppService {

    /**
     * Method returns a map of appropriate values id of the departments and the average salary
     * @return Map{Key: Department id; Value: Average salary}
     */
    Map<Integer, Double> getAverageSalary();

    /**
     * Method return an array of employees of the target
     * department were born in the target date
     * @param departmentId ID of the target department
     * @param dateOfBirth target date
     * @return list of entity employees
     */
    List<Employee> searchEmployeesByDateOfBirth(int departmentId, String dateOfBirth);

    /**
     * Method return an array of employees of the target
     * department born between the dates
     * @param departmentId ID of the target department
     * @param dateFrom start date of period
     * @param dateTo date of end of period
     * @return list of entity employees
     */
    List<Employee> searchEmployeesByIntervalOfBirthdates(int departmentId, String dateFrom, String dateTo);

    /**
     * Method perform request to REST service for create department
     * @param department entity of the created department
     */
    void createDepartment(Department department) throws RestClientException;

    /**
     * Method perform request to REST service to get department entity with the same id
     * @param id ID of the target department
     * @return Department entity
     */
    Department getDepartmentById(int id) throws RestClientException;

    /**
     * Method perform request to REST service to get an array of all departments
     * @return Array of departments entity
     */
    Department[] getDepartments() throws RestClientException;

    /**
     * Method perform request to REST service for updating department
     * @param department entity of the modified department
     */
    void updateDepartment(Department department) throws RestClientException;

    /**
     * Method perform request to REST service for delete employee
     * @param id of the deletable employee
     */
    void deleteDepartmentById(int id) throws RestClientException;

    /**
     * Method perform request to REST service for create employee
     * @param employee entity of the created employee
     */
    void createEmployee(Employee employee) throws RestClientException;

    /**
     * Method perform request to REST service to get employee entity with the same id
     * @param id ID of the target employee
     * @return employee entity
     */
    Employee getEmployeeById(int id) throws RestClientException;

    /**
     * Method perform request to REST service to get an array of employees of the target department
     * @param id ID of the target department
     * @return array of emploees entity
     */
    Employee[] getEmployeesByDepartmentId(int id) throws RestClientException;

    /**
     * Method perform request to REST service for updating employee
     * @param employee entity of the modified employee
     */
    void updateEmployee(Employee employee) throws RestClientException;

    /**
     * Method perform request to REST service for removal employee with the same id
     * @param id ID of the deletable employee
     */
    void deleteEmployeeById(int id) throws RestClientException;

}
