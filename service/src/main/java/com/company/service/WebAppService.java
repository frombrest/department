package com.company.service;

import com.company.model.Department;
import com.company.model.Employee;
import java.util.List;
import java.util.Map;

/**
 * Created by frombrest on 28.2.17.
 */


public interface WebAppService {

    public Map<Integer, Double> getAverageSalary();
    public List<Employee> searchEmployeesByDateOfBirth(int departmentId, String dateOfBirth);
    public List<Employee> searchEmployeesByIntervalOfBirthdates(int departmentId, String dateFrom, String dateTo);
    public void createDepartment(Department department);
    public Department getDepartmentById(int id);
    public Department[] getDepartments();
    public void updateDepartment(Department department);
    public void deleteDepartmentById(int id);
    public void createEmployee(Employee employee);
    public Employee getEmployeeById(int id);
    public Employee[] getEmployeesByDepartmentId(int id);
    public void updateEmployee(Employee employee);
    public void deleteEmployeeById(int id);

}
