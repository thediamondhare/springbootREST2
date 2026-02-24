package com.springbootREST.springbootREST.service;

import com.springbootREST.springbootREST.entities.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    void deleteById(int theId);

}
