package com.company.service;

import com.company.dao.EmployeeDAO;
import com.company.model.Employee;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */

@Service(value = "employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Resource(name = "employeeDAO")
    private EmployeeDAO employeeDAO;

    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.getAll();
    }

    public List<Employee> getEmployeesByDepartmentId(int id) {
        return employeeDAO.getByDepartmentId(id);
    }

    public Employee getEmployeeById(int id) {
        return employeeDAO.getById(id);
    }

    public void addEmployee(Employee employee) {
        employeeDAO.create(employee);
    }

    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    public void deleteEmployee(int id) {
        employeeDAO.delete(id);
    }
}
