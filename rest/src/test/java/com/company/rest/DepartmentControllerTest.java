package com.company.rest;

import com.company.service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by frombrest on 12.3.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-rest.xml"})
public class DepartmentControllerTest {

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private DepartmentService departmentService;
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void prepare() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getAllDepartments() throws Exception {
        mockMvc.perform(get("/department").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void getDepartmentById() throws Exception {
        mockMvc.perform(get("/department/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void addDepartment() throws Exception {
        mockMvc.perform(post("/department").contentType(MediaType.APPLICATION_JSON).content("{\"id\":6,\"name\":\"Test\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateDepartment() throws Exception {
        mockMvc.perform(put("/department").contentType(MediaType.APPLICATION_JSON).content("{\"id\":5,\"name\":\"Снабжения\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteDepartment() throws Exception {
        mockMvc.perform(delete("/department/5").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }



}