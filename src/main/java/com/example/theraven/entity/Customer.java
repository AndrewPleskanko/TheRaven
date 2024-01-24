package com.example.theraven.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    private Long created;

    @Column(name = "updated")
    private Long updated;

    @Size(min = 2, max = 50)
    @Column(name = "full_name")
    private String fullName;

    @Email
    @Size(min = 2, max = 100)
    @Column(name = "email", unique = true)
    private String email;

    @Pattern(regexp = "^\\+\\d{6,14}$")
    @Column(name = "phone")
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

}
