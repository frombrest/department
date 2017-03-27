package com.company.dao;

import com.company.model.Department;
import org.springframework.dao.DataAccessException;
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
    Department getById(int id) throws DataAccessException;

    /**
     * Method returns a list of all departments from the storage
     * @return list of all entity departments
     */
    List<Department> getAll() throws DataAccessException;

    /**
     * Method remove department with the same id from the storage
     * @param id of the deletable department
     */
    void delete(int id) throws DataAccessException;

    /**
     * Method creates a department in the storage
     * @param department entity of the created department
     */
    void create(Department department) throws DataAccessException;

    /**
     * Method makes changes to department in the storage
     * @param department entity of the modified department
     */
    void update(Department department) throws DataAccessException;
}