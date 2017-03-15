package com.company.service;

import com.company.model.Department;
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
    public List<Department> getAllDepartments();

    /**
     * Method searches the department entity with the same id in storage
     * @param id ID of the target department
     * @return Department entity
     */
    public Department getDepartmentById(int id);

    /**
     * Method add a department in the storage
     * @param department entity of the new department
     */
    public void addDepartment(Department department);

    /**
     * Method makes changes to department in the storage
     * @param department entity of the modified department
     */
    public void updateDepartment(Department department);

    /**
     * Method remove department with the same id from the storage
     * @param id of the deletable department
     */
    public void deleteDepartment(int id);

}
