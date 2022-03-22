package com.dev.explore.spring.springbootmonitor.service;

import com.dev.explore.spring.springbootmonitor.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeService extends JpaRepository<Employee, Integer>{
}
