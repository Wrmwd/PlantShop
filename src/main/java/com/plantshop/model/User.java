package com.plantshop.model;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String role;
    @Column
    private String mobileNumber;
}
