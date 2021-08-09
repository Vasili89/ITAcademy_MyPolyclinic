package by.kostopravov.polyclinic.repository;

import by.kostopravov.polyclinic.dto.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCityAndStreetAndHouseAndFlat(
            String city, String street, String house, String flat);

}
