package com.springbootREST.springbootREST.repositories;

import com.springbootREST.springbootREST.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  EmployeeRepository extends JpaRepository<Employee, Integer> {

}
