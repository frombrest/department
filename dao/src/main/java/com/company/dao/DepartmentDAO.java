package com.company.dao;

import com.company.model.Department;

import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */

public interface DepartmentDAO {
    public Department getById(int id);
    public List<Department> getAll();
    public void delete(int id);
    public void create(Department department);
    public void update(Department department);
}