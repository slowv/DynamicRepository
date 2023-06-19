package com.vsoftlight.dynamicrepository.entity;

import com.vsoftlight.dynamicrepository.repository.TupleField;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "hero")
public class Hero {
    @Id
    @TupleField(alias = "identity", type = Long.class)
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    @TupleField(alias = "display", type = String.class)
    private String name;

    @Column(name = "dob")
    @TupleField(alias = "date_of_birth", type = Instant.class)
    private Instant dob;

    @OneToMany(mappedBy = "hero", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Skill> skills = new HashSet<>();
}
