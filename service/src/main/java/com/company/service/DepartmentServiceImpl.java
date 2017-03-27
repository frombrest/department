package com.company.service;

import com.company.dao.DepartmentDAO;
import com.company.model.Department;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * This class implements methods for working
 * with departments used in the operation
 * of the web service
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
@Service(value = "departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    /**
     * Field for injection DAO bean
     */
    @Resource(name = "departmentDAO")
    private DepartmentDAO departmentDAO;

    /**
     * Method is intended for injection DAO bean
     * @param departmentDAO
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    /**
     * Method returns a list of all departments from the storage
     * @return List of all entity departments
     */
    public List<Department> getAllDepartments() throws DataAccessException {
        return departmentDAO.getAll();
    }

    /**
     * Method searches the department entity with the same id in storage
     * @param id ID of the target department
     * @return Department entity
     */
    public Department getDepartmentById(int id) throws DataAccessException {
        return departmentDAO.getById(id);
    }

    /**
     * Method add a department in the storage
     * @param department entity of the new department
     */
    public void addDepartment(Department department) throws DataAccessException {
        departmentDAO.create(department);
    }

    /**
     * Method makes changes to department in the storage
     * @param department entity of the modified department
     */
    public void updateDepartment(Department department) throws DataAccessException {
        departmentDAO.update(department);
    }

    /**
     * Method remove department with the same id from the storage
     * @param id of the deletable department
     */
    public void deleteDepartment(int id) throws DataAccessException {
        departmentDAO.delete(id);
    }
}
