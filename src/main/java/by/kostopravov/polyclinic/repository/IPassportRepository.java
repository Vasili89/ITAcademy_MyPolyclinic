package by.kostopravov.polyclinic.repository;

import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface IPassportRepository extends JpaRepository<Passport, String> {

    Passport findByUser(User user);

    @Transactional
    void deleteByUser(User user);
}
