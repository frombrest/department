package com.company.dao;

import com.company.model.Department;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by frombrest on 24.2.17.
 */

@Repository(value = "departmentDAO")
public class DepartmentJdbcDAO implements DepartmentDAO {

    private final static Logger logger = LogManager.getLogger(DepartmentJdbcDAO.class);

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Department getById(int id) {
        Department department = null;
        try {
            department = jdbcTemplate.queryForObject(
                    "SELECT * FROM \"TW\".\"Department\" WHERE \"id\" = ?", new Object[]{id},
                    new RowMapper<Department>() {
                        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
                            Department department = new Department();
                            department.setId(resultSet.getInt("id"));
                            department.setName(resultSet.getString("name"));
                            return department;
                        }
                    }
            );
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Department with id=" + id + " not found. Result == null");
        }
        return department;
    }

    public List<Department> getAll() {
        List<Department> listDepartment = null;
        try {
            listDepartment = jdbcTemplate.query(
                    "SELECT * FROM \"TW\".\"Department\";", new RowMapper<Department>() {
                        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
                            Department department = new Department();
                            department.setId(resultSet.getInt("id"));
                            department.setName(resultSet.getString("name"));
                            return department;
                        }
                    }
            );
        } catch (EmptyResultDataAccessException exception) {
            logger.debug("Departments not found. Result == null");
        }
        return listDepartment;
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM \"TW\".\"Department\" WHERE \"id\"= ?;", id);
        logger.debug("Delete department with id=" + id);
    }

    public void create(Department department) {
        jdbcTemplate.update("INSERT INTO \"TW\".\"Department\" (\"name\") VALUES (?);",
                department.getName());
        logger.debug("Create department: " + department.getName());
    }

    public void update(Department department) {
        jdbcTemplate.update("UPDATE \"TW\".\"Department\" SET \"name\"=? WHERE \"id\"=?;",
                department.getName(), department.getId());
        logger.debug("Update department: " + department.getName());
    }
}
