package com.example.Projet9.repository;

import com.example.Projet9.Entity.Patient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient>{

    public List<Patient> findAll(Specification<Patient> patientSpecification);

    public Optional<Patient> findByIdPatient(Integer idPatient);



}
