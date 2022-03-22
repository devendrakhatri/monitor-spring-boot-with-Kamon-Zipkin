package com.dev.explore.spring.springbootmonitor.controller;


import com.dev.explore.spring.springbootmonitor.aop.RestTemplateInterceptor;
import com.dev.explore.spring.springbootmonitor.model.Employee;
import com.dev.explore.spring.springbootmonitor.random.SampleClass1;
import com.dev.explore.spring.springbootmonitor.service.EmployeeService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import kamon.instrumentation.executor.ExecutorInstrumentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Timed
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SampleClass1 sampleClass1;

    private RestTemplate restTemplate = new RestTemplate();

    private List<Integer> ids = new ArrayList<>();

    public EmployeeController() {
        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateInterceptor()));
    }
    public EmployeeController(MeterRegistry registry) {
        registry.gaugeCollectionSize("ids.size", Tags.empty(), this.ids);
    }

    // Select, Insert, Delete, Update Operations for an Employee

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable Integer id){
        log.info("Accessing employee {}", id);

        ExecutorInstrumentation.Settings settings = new ExecutorInstrumentation.Settings(true, true);
        ExecutorService es = ExecutorInstrumentation.instrument(Executors.newFixedThreadPool(2), "random-" + UUID.randomUUID().toString(), settings);
        es.submit(() -> sampleClass1.test2());
        es.shutdown();

        sampleClass1.test2();
        SampleClass1.test1();
        this.ids.add(id);

        log.info("response: {}", this.restTemplate.getForObject("http://localhost:8080/employees", String.class));

        return employeeService.findById(id).get();
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    String addEmployee(@RequestBody Employee employee){
        Employee savedEmployee = employeeService.save(employee);
        return "SUCCESS";
    }

    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    Employee updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee = employeeService.save(employee);
        return updatedEmployee;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.DELETE)
    Map<String, String> deleteEmployee(@RequestParam Integer id){
        Map<String, String> status = new HashMap<>();
        Optional<Employee> employee = employeeService.findById(id);
        if(employee.isPresent()) {
            employeeService.delete(employee.get());
            status.put("Status", "Employee deleted successfully");
        }
        else {
            status.put("Status", "Employee not exist");
        }
        return status;
    }

    // Select, Insert, Delete for List of Employees

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public List<Employee> getAllEmployee(){
        log.info("Fetching all the employees");
        return employeeService.findAll();
    }

    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    String addAllEmployees(@RequestBody List<Employee> employeeList){
        employeeService.saveAll(employeeList);
        return "SUCCESS";
    }

    @RequestMapping(value = "/employees", method = RequestMethod.DELETE)
    String addAllEmployees(){
        employeeService.deleteAll();
        return "SUCCESS";
    }
}
