package com.company.webapp;

import com.company.service.WebAppService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by frombrest on 11.3.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-webapp.xml"})
public class DepartmentControllerTest {

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private WebAppService webappService;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void prepare() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void allDepartments() throws Exception {
        mockMvc.perform(get("/departments"))
                .andExpect(model().attribute("aversalary", webappService.getAverageSalary()))
                .andExpect(model().attribute("departments", webappService.getDepartments()))
                .andExpect(view().name("departments"));
    }

    @Test
    public void deleteDepartment() throws Exception {
        mockMvc.perform(get("/departments/delete-department/5"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void deleteEmployee() throws Exception {
        mockMvc.perform(get("/departments/6/delete-employee/6"))
                .andExpect(redirectedUrl("/departments/6"));
    }

    @Test
    public void showEmployees() throws Exception {
        mockMvc.perform(get("/departments/6"))
                .andExpect(model().attribute("employees", webappService.getEmployeesByDepartmentId(6)))
                .andExpect(model().attribute("department", webappService.getDepartmentById(6)))
                .andExpect(view().name("employees"));
    }

    @Test
    public void showEmployees1() throws Exception {
        mockMvc.perform(get("/departments/6?date_of_birth=1989-02-02"))
                .andExpect(model().attribute("date_of_birth", "1989-02-02"))
                .andExpect(model().attribute("employees", webappService.searchEmployeesByDateOfBirth(6, "1989-02-02")))
                .andExpect(model().attribute("department", webappService.getDepartmentById(6)))
                .andExpect(view().name("employees"));
        mockMvc.perform(get("/departments/6?date_of_birth="))
                .andExpect(redirectedUrl("/departments/6"));
    }

    @Test
    public void showEmployees2() throws Exception {
        mockMvc.perform(get("/departments/6?date_from=1989-02-03&date_to=1989-02-02"))
                .andExpect(model().attribute("date_from", "1989-02-03"))
                .andExpect(model().attribute("date_to", "1989-02-02"))
                .andExpect(model().attribute("employees", webappService.searchEmployeesByIntervalOfBirthdates(6, "1989-02-03", "1989-02-02")))
                .andExpect(model().attribute("department", webappService.getDepartmentById(6)))
                .andExpect(view().name("employees"));
        mockMvc.perform(get("/departments/6?date_from=&date_to="))
                .andExpect(redirectedUrl("/departments/6"));
    }

    @Test
    public void newDepartment() throws Exception {
        mockMvc.perform(get("/departments/add-department"))
                .andExpect(view().name("add-department"));
    }

    @Test
    public void newDepartment1() throws Exception {
        mockMvc.perform(post("/departments/add-department"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void editDepartment() throws Exception {
        mockMvc.perform(get("/departments/edit-department/6"))
                .andExpect(model().attribute("dep", webappService.getDepartmentById(6)))
                .andExpect(view().name("edit-department"));
    }

    @Test
    public void editDepartment1() throws Exception {
        mockMvc.perform(post("/departments/edit-department"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void editEmployee() throws Exception {
        mockMvc.perform(get("/departments/6/edit-employee/6"))
                .andExpect(model().attribute("employee", webappService.getEmployeeById(6)))
                .andExpect(model().attribute("departments",webappService.getDepartments()))
                .andExpect(view().name("edit-employee"));
    }

    @Test
    public void editEmployee1() throws Exception {
        mockMvc.perform(post("/departments/6/edit-employee/6"))
                .andExpect(redirectedUrl("/departments/6"));
    }

    @Test
    public void newEmployee() throws Exception {
        mockMvc.perform(get("/departments/6/add-employee"))
                .andExpect(model().attribute("departments", webappService.getDepartments()))
                .andExpect(model().attribute("current_department", 6))
                .andExpect(view().name("add-employee"));
    }

    @Test
    public void newEmployee1() throws Exception {
        mockMvc.perform(post("/departments/6/add-employee"))
                .andExpect(redirectedUrl("/departments/6"));
    }

}