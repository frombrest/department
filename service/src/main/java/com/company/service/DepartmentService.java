package com.company.service;

import com.company.model.Department;
import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */
public interface DepartmentService {

    public List<Department> getAllDepartments();
    public Department getDepartmentById(int id);
    public void addDepartment(Department department);
    public void updateDepartment(Department department);
    public void deleteDepartment(int id);

}
