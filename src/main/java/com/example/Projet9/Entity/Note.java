package com.example.Projet9.Entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "note")
public class Note {

    @Id
    @Column(name = "idNote", nullable = false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idNote;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dateNote")
    private Date dateNote;

    @Column(name = "takeNote")
    private String takeNote;

    @Column(name = "idPatientForNote")
    private Integer idPatientForNote;

}
