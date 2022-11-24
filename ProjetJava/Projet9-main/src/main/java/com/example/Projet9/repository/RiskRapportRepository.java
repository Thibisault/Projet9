package com.example.Projet9.repository;

import com.example.Projet9.Entity.RiskRapport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskRapportRepository extends MongoRepository<RiskRapport, String> {

    public Optional<RiskRapport> findById(String idRiskRapport);
    public Optional<RiskRapport> findByIdPatientForRiskRapport(Integer idPatientForRiskRapport);


}
