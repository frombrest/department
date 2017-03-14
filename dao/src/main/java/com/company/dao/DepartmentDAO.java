package com.company.dao;

import com.company.model.Department;
import java.util.List;

/**
 * The interface describes the methods for
 * working with the storage of departments
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */

public interface DepartmentDAO {

    /**
     * Method searches the department entity with the same id in storage
     * @param id of the target department
     * @return department entity
     */
    public Department getById(int id);

    /**
     * Method returns a list of all departments from the storage
     * @return list of all entity departments
     */
    public List<Department> getAll();

    /**
     * Method remove department with the same id from the storage
     * @param id of the deletable department
     */
    public void delete(int id);

    /**
     * Method creates a department in the storage
     * @param department entity of the created department
     */
    public void create(Department department);

    /**
     * Method makes changes to department in the storage
     * @param department entity of the modified department
     */
    public void update(Department department);
}