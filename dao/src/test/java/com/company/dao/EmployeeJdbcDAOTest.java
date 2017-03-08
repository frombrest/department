package com.company.dao;

import com.company.model.Employee;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;

import static org.junit.Assert.*;

/**
 * Created by frombrest on 8.3.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-dao.xml"})
public class EmployeeJdbcDAOTest {

    @Autowired
    private EmployeeJdbcDAO employeeJdbcDAO;

    @Before
    public void preparation() throws Exception {
        Employee employee = new Employee("Test GetById Method",
                Date.valueOf("1989-01-01"), 3, 545.15);
        employeeJdbcDAO.create(employee);
        employee = new Employee("Test Delete Method",
                Date.valueOf("1989-02-02"), 3, 645.15);
        employeeJdbcDAO.create(employee);
    }

    @Test
    public void getById() throws Exception {
        String testName = "Test GetById Method";
        int i = 0;
        for (Employee employee:employeeJdbcDAO.getAll()) {
            if(employee.getFull_name().equals(testName)){
                i = employee.getId();
            }
        }
        Assert.assertEquals(testName,employeeJdbcDAO.getById(i).getFull_name());
    }

    @Test
    public void getByDepartmentId() throws Exception {
        int count = employeeJdbcDAO.getByDepartmentId(3).size();
        employeeJdbcDAO.create(new Employee("Test GetByDepartmentId Method",
                Date.valueOf("1989-04-04"), 3, 845.15));
        Assert.assertEquals(count+1, employeeJdbcDAO.getByDepartmentId(3).size());
    }

    @Test
    public void getAll() throws Exception {
        int count = employeeJdbcDAO.getAll().size();
        employeeJdbcDAO.create(new Employee("Test GetAll Method",
                Date.valueOf("1989-03-03"), 2, 745.15));
        Assert.assertEquals(count+1, employeeJdbcDAO.getAll().size());
    }

    @Test
    public void delete() throws Exception {
        String testName = "Test Delete Method";
        int i = 0;
        for (Employee employee:employeeJdbcDAO.getAll()) {
            if(employee.getFull_name().equals(testName)){
                i = employee.getId();
            }
        }
        employeeJdbcDAO.delete(i);
        Assert.assertNull(employeeJdbcDAO.getById(i));
    }

    @Test
    public void create() throws Exception {
        Employee employee = new Employee("Test Create Method",
                Date.valueOf("1990-01-01"), 1, 525.15);
        employeeJdbcDAO.create(employee);
        int i = 0;
        for (Employee empl:employeeJdbcDAO.getAll()) {
            if(empl.getFull_name().equals(employee.getFull_name())){
                i = empl.getId();
            }
        }
        Assert.assertEquals(employee.getFull_name(),employeeJdbcDAO.getById(i).getFull_name());
    }

    @Test
    public void update() throws Exception {
        Employee employee = new Employee("Test Update Method Before",
                Date.valueOf("1991-02-03"), 1, 525.15);
        employeeJdbcDAO.create(employee);
        Employee newemployee = new Employee();
        for (Employee empl:employeeJdbcDAO.getAll()) {
            if(empl.getFull_name().equals(employee.getFull_name())){
                newemployee = empl;
                newemployee.setFull_name("Test Update Method After");
                employeeJdbcDAO.update(newemployee);
            }
        }
        employee = employeeJdbcDAO.getById(newemployee.getId());
        Assert.assertEquals(employee.getFull_name(),employeeJdbcDAO.getById(newemployee.getId()).getFull_name());
    }
}