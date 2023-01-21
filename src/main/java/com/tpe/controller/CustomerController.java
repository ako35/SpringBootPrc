package com.tpe.controller;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.repository.CustomerRepository;
import com.tpe.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private CustomerRepository customerRepository;

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
    // Customer is deleted successfully mesaji donsun

    // id ile tek bir customer getirme -> http://localhost:8080/customers/custom?id=1
    @GetMapping("/custom")
    public ResponseEntity<CustomerDTO> getCustomerByIdRequestParam(@RequestParam("id") Long id){
        CustomerDTO customerDTO=customerService.getCustomerDTOById(id);
        return ResponseEntity.ok(customerDTO);
    }

    // id ile bir customer i silme -> http://localhost:8080/customers/custom?id=1
    //     Customer is deleted successfully mesaji donsun
    @DeleteMapping("/custom")
    public ResponseEntity<String> deleteCustomerById(@RequestParam("id") Long id){
        String message="Customer is deleted successfully";
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok(message);
    }

    // 5- id ile customer i update etme -> http://localhost:8080/customers/update/1
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomerById(@PathVariable("id") Long id,@RequestBody CustomerDTO customerDTO){
        customerService.updateCustomerById(id,customerDTO);
        return ResponseEntity.ok("Customer is updated successfully");
    }

    // pagination?
    // 6- tum customer lari page page gosterme
    // http://localhost:8080/customers/page?page=1&size=2&sort=id&direction=ASC
    @GetMapping("/page")
    public ResponseEntity<Page<Customer>> getAllCustomerByPage(@RequestParam("page") int page, // hangi sayfa
                                                               @RequestParam("size") int size, // her sayfada kac adet
                                                               @RequestParam("sort") String prop, // siralama fieldi
                                                               @RequestParam("direction") Sort.Direction direction){ // azalan, artan
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Customer> customerPage=customerService.getAllCustomerByPage(pageable);
        return ResponseEntity.ok(customerPage);
    }


}
