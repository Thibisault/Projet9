package com.example.Projet9.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "riskRapport")
@Data
@Entity
public class RiskRapport {

    @JsonIgnoreProperties
    @JsonIgnore
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "risk", nullable = false)
    private Risk risk;

    @ElementCollection(targetClass=String.class)
    @Column(name = "terminologyList")
    private List<String> terminologyList = new ArrayList<>();

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @Column(name = "phoneNumber", nullable = false)
    private Integer phoneNumber;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @JsonIgnoreProperties
    @JsonIgnore
    @Column(name = "idPatientForRiskRapport")
    private Integer idPatientForRiskRapport;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "idPatient")
    private List<Note> noteList = new ArrayList<>();

}
