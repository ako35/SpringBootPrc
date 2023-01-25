package com.tpe.repository;

import com.tpe.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    boolean existsByEmail(String email);

    List<Customer> findByName(String name);

    List<Customer> findByNameAndLastName(String name, String lastName);

    @Query("select c from Customer c where lower(c.name) like %:pName%")
    List<Customer> findAllByNameLike(@Param("pName") String name);
}
