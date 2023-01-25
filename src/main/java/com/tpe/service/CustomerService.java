package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.dto.CustomerDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void saveCustomer(Customer customer) {
        boolean isExistsCustomer = customerRepository.existsByEmail(customer.getEmail());
        if (isExistsCustomer) {
            throw new ConflictException("Customer already exists by email " + customer.getEmail());
        }

        customerRepository.save(customer);

    }

    public List<Customer> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        return customerList;
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found by id: " + id));
        return customer;
    }

    public void deleteCustomerById(Long id) {
        Customer customer=getCustomerById(id);
        customerRepository.delete(customer);
    }

    public CustomerDTO getCustomerDTOById(Long id) {
        Customer customer=getCustomerById(id);
//        CustomerDTO customerDTO=new CustomerDTO();
//        customerDTO.setName(customerDTO.getName());
//        customerDTO.setLastName(customerDTO.getLastName());
//        customerDTO.setEmail(customerDTO.getEmail());
//        customerDTO.setPhone(customerDTO.getPhone());
        CustomerDTO customerDTO=new CustomerDTO(customer);
        return customerDTO;
    }

    public void updateCustomerById(Long id, CustomerDTO customerDTO) {
        Customer customer=getCustomerById(id);
        boolean isExistsEmail=customerRepository.existsByEmail(customerDTO.getEmail());
        if (isExistsEmail && !customerDTO.getEmail().equals(customer.getEmail())){
            throw new ConflictException("Email is already in use: "+customerDTO.getEmail());
        }
        customer.setName(customerDTO.getName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customerRepository.save(customer);
    }

    public Page<Customer> getAllCustomerByPage(Pageable pageable) {
        Page<Customer> customerPage=customerRepository.findAll(pageable);
        return customerPage;
    }


    public List<Customer> getAllCustomerByName(String name) {
        List<Customer> customers=customerRepository.findByName(name);
        return customers;

    }

    public List<Customer> getAllCustomerByFullName(String name, String lastName) {
        return customerRepository.findByNameAndLastName(name,lastName);
    }

    public List<Customer> getAllCustomerByNameLike(String name) {
        String lowerName=name.toLowerCase();
        return customerRepository.findAllByNameLike(lowerName);
    }
}
