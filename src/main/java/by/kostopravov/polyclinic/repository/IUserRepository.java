package by.kostopravov.polyclinic.repository;

import by.kostopravov.polyclinic.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByGoogleEmail(String googleEmail);

}
