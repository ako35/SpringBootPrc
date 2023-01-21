package com.tpe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "t_customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // burda sadece @NotBlank isimizi goruyor aslinda sadece null mesaji ayri donmek istersek ikisini birden kullanabiliriz.
    @NotNull(message = "First name can not be null") // sadece null olmasin yani "" hiclik olabilir " " bosluk olabilir.
    @NotBlank(message = "First name can not be space") // null olamaz, empty olamaz bosluk olamaz
    // @NotEmpty // null olamaz, empty olamaz, bosluk olabilir.
    @Size(min = 2, max = 50)
    private String name;

    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be space")
    @Size(min = 2, max = 50)
    private String lastName;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

}
