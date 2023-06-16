package com.vsoftlight.dynamicrepository.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hero")
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
