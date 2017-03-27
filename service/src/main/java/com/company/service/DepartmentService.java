package com.company.service;

import com.company.model.Department;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * Interface describes methods for working
 * with departments used in the operation
 * of the web service
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
public interface DepartmentService {

    /**
     * Method returns a list of all departments from the storage
     * @return List of all entity departments
     */
    List<Department> getAllDepartments() throws DataAccessException;

    /**
     * Method searches the department entity with the same id in storage
     * @param id ID of the target department
     * @return Department entity
     */
    Department getDepartmentById(int id) throws DataAccessException;

    /**
     * Method add a department in the storage
     * @param department entity of the new department
     */
    void addDepartment(Department department) throws DataAccessException;

    /**
     * Method makes changes to department in the storage
     * @param department entity of the modified department
     */
    void updateDepartment(Department department) throws DataAccessException;

    /**
     * Method remove department with the same id from the storage
     * @param id of the deletable department
     */
    void deleteDepartment(int id) throws DataAccessException;

}
