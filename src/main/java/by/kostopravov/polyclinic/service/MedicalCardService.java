package by.kostopravov.polyclinic.service;

import by.kostopravov.polyclinic.repository.IMedicalCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicalCardService {

    private IMedicalCardRepository medicalCardRepository;

    @Autowired
    public MedicalCardService(IMedicalCardRepository medicalCardRepository) {
        this.medicalCardRepository = medicalCardRepository;
    }

    public void deleteCard(Long medicalCardId) {
        medicalCardRepository.deleteById(medicalCardId);
    }
}
