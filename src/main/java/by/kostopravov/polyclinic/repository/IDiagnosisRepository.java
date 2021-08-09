package by.kostopravov.polyclinic.repository;

import by.kostopravov.polyclinic.dto.Diagnosis;
import by.kostopravov.polyclinic.dto.MedicalCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    void deleteByMedicalCard(MedicalCard medicalCard);
}
