package com.tpe.controller;

import com.tpe.domain.Customer;
import com.tpe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 1- Spring Boot u selamlama -> http://localhost:8080/customers/greet
    @GetMapping("/greet")
    public String greetSpringBoot(){
        return "Hello Spring Boot: ";
    }

    // 2- customer olusturma/kaydetme -> http://localhost:8080/customers/save
    @PostMapping("/save")
    public ResponseEntity<String> saveCustomer(@Valid @RequestBody Customer customer){
        customerService.saveCustomer(customer);
        String message="Customer is saved successfully";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    // 3- tum customerlari getirme -> http://localhost:8080/customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer(){
        List<Customer> customerList=customerService.getAllCustomer();
        return ResponseEntity.ok(customerList);
    }

    // 4- id ile tek bir customer getirme -> http://localhost:8080/customers/1
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id){
        Customer customer=customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    // odev1: id ile tek bir customer getirme -> http://localhost:8080/customers/custom?id=1

    // odev2: id ile bir customer i silme -> http://localhost:8080/customers/custom?id=1

}
