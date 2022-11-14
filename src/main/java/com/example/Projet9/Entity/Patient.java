package com.example.Projet9.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name = "idPatient", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idPatient;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @Column(name = "phoneNumber", nullable = false)
    private Integer phoneNumber;

    @Column(name = "risk", nullable = false)
    private Risk risk;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "idPatient")
    private List<Note> noteList = new ArrayList<>();


}
