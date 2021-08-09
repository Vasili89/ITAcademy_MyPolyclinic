package by.kostopravov.polyclinic.service;

import by.kostopravov.polyclinic.dto.Diagnosis;
import by.kostopravov.polyclinic.dto.MedicalCard;
import by.kostopravov.polyclinic.repository.IDiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisService {

    IDiagnosisRepository diagnosisRepository;

    @Autowired
    public DiagnosisService(IDiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    public void saveDiagnosis(Diagnosis diagnosis) {
        diagnosisRepository.save(diagnosis);
    }

    public void deleteDiagnosisList(MedicalCard medicalCard) {
        diagnosisRepository.deleteByMedicalCard(medicalCard);
    }
}
