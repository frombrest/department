package com.company.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.company.model.Department;

/**
 * Created by frombrest on 7.3.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-dao.xml"})
public class DepartmentJdbcDAOTest {

    @Autowired
    private DepartmentJdbcDAO departmentJdbcDAO;

    @Before
    public void preparation() throws Exception {
        Department department = new Department("TestGetByIdMethod");
        departmentJdbcDAO.create(department);
        department = new Department("TestDeleteMethod");
        departmentJdbcDAO.create(department);
    }

    @Test
    public void create() throws Exception {
        Department department = new Department("TestCreateMethod");
        departmentJdbcDAO.create(department);
        int i = 0;
        for (Department dep:departmentJdbcDAO.getAll()) {
            if(dep.getName().equals(department.getName())){
                i = dep.getId();
            }
        }
        Assert.assertEquals(department.getName(), departmentJdbcDAO.getById(i).getName());
    }

    @Test
    public void update() throws Exception {
        Department department = new Department("TestUpdateMethod_Before");
        departmentJdbcDAO.create(department);
        Department newdepartment = new Department();

        for (Department dep:departmentJdbcDAO.getAll()) {
            if(dep.getName().equals(department.getName())){
                newdepartment.setId(dep.getId());
                newdepartment.setName("TesttUpdateMethod_After");
                departmentJdbcDAO.update(newdepartment);
            }
        }

        department = departmentJdbcDAO.getById(newdepartment.getId());
        Assert.assertEquals(department.getName(), departmentJdbcDAO.getById(newdepartment.getId()).getName());
    }

    @Test
    public void getById() throws Exception {
        String testName = "TestGetByIdMethod";
        int i = 0;
        for (Department dep:departmentJdbcDAO.getAll()) {
            if(dep.getName().equals(testName)){
                i = dep.getId();
            }
        }
        Assert.assertEquals(testName,departmentJdbcDAO.getById(i).getName());
    }

    @Test
    public void getAll() throws Exception {
        int count1 = departmentJdbcDAO.getAll().size();
        departmentJdbcDAO.create(new Department("TestGetAllMethod"));
        Assert.assertEquals(count1+1,departmentJdbcDAO.getAll().size());
    }

    @Test
    public void delete() throws Exception {
        String testName = "TestDeleteMethod";
        boolean thrown = false;
        int i = 0;
        for (Department dep:departmentJdbcDAO.getAll()) {
            if(dep.getName().equals(testName)){
                i = dep.getId();
            }
        }
        departmentJdbcDAO.delete(i);
        try {
            departmentJdbcDAO.getById(i);
        } catch (EmptyResultDataAccessException exc){
            thrown = true;
        }
        Assert.assertTrue(thrown);

    }
}
