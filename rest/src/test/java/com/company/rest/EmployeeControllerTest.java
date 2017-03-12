package com.company.rest;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by frombrest on 12.3.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:test-rest.xml"})
public class EmployeeControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void prepare() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getAllEmployees() throws Exception {
        mockMvc.perform(get("/employee").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void getEmployeesByDepartmentId() throws Exception {
        mockMvc.perform(get("/employee").contentType(MediaType.APPLICATION_JSON).param("department","0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void getEmployeeById() throws Exception {
        mockMvc.perform(get("/employee/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());
    }

    @Test
    public void addEmployee() throws Exception {
        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON).content("{\"id\":0,\"full_name\":\"Михаленя Андрей Сергеевич\",\"date_of_birth\":\"1989-02-24\",\"department_id\":2,\"salary\":475.14}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void updateEmployee() throws Exception {
        mockMvc.perform(put("/employee").contentType(MediaType.APPLICATION_JSON).content("{\"id\":0,\"full_name\":\"Михаленя Андрей Сергеевич\",\"date_of_birth\":\"1989-02-24\",\"department_id\":2,\"salary\":475.14}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void deleteEmployee() throws Exception {
        mockMvc.perform(delete("/employee/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}