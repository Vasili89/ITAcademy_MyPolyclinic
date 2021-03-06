package by.kostopravov.polyclinic.repository;

import by.kostopravov.polyclinic.dto.MedicalCard;
import by.kostopravov.polyclinic.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IMedicalCardRepository extends JpaRepository<MedicalCard, Long> {

    MedicalCard findByOwner(User user);

}
