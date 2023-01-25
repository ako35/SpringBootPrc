package com.tpe.service;

import com.tpe.domain.Customer;
import com.tpe.domain.Product;
import com.tpe.dto.ProductDTO;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(ProductDTO productDTO) {
        Product product=new Product();
        Customer customer=customerService.getCustomerById(productDTO.getCustomerId());
        product.setProductName(productDTO.getProductName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setCustomer(customer);
        productRepository.save(product);

    }

    public List<Product> getAllProduct() {
        List<Product> products=productRepository.findAll();
        return products;
    }

    public Product getProductById(Long id) {
        Product product=productRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Resource not found exception"));
        return product;
    }
}
