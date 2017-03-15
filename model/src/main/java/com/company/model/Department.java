package com.company.model;


/**
 * Class describes the entity of the department
 *
 * @author Aliaksandr Parfianiuk frombrest@gmail.com
 *
 */
public class Department {

    /**
     * Field for storing the id of the department
     */
    private int id;

    /**
     * Field for storing the name of the department
     */
    private String name;

    /**
     * Default object constructor
     */
    public Department() {
    }

    /**
     * Parameterized object constructor
     * @param name Name of the department
     */
    public Department(String name) {
        this.name = name;
    }

    /**
     * Parameterized object constructor
     * @param id ID of the department
     * @param name Name of the department
     */
    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Method for obtaining the department id
     * @return ID of the department
     */
    public int getId() {
        return id;
    }

    /**
     * Method for changing the department id
     * @param id New id of the department
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method for obtaining the department name
     * @return Name of the department
     */
    public String getName() {
        return name;
    }

    /**
     * Method for changing the department name
     * @param name New name of the department
     */
    public void setName(String name) {
        this.name = name;
    }
}
