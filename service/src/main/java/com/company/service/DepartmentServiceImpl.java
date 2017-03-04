package com.company.service;

import com.company.dao.DepartmentDAO;
import com.company.model.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */
@Service(value = "departmentService")
public class DepartmentServiceImpl implements DepartmentService {

    @Resource(name = "departmentDAO")
    private DepartmentDAO departmentDAO;

    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public List<Department> getAllDepartments() {
        return departmentDAO.getAll();
    }

    public Department getDepartmentById(int id) {
        return departmentDAO.getById(id);
    }

    public void addDepartment(Department department) {
        departmentDAO.create(department);
    }

    public void updateDepartment(Department department) {
        departmentDAO.update(department);
    }

    public void deleteDepartment(int id) {
        departmentDAO.delete(id);
    }
}
