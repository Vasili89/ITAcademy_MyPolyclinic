package by.kostopravov.polyclinic.sirvice;

import by.kostopravov.polyclinic.dto.Address;
import by.kostopravov.polyclinic.dto.MedicalCard;
import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.repository.IAddressRepository;
import by.kostopravov.polyclinic.repository.IMedicalCardRepository;
import by.kostopravov.polyclinic.repository.IPassportRepository;
import by.kostopravov.polyclinic.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AppService {

    IUserRepository userRepository;
    IPassportRepository passportRepository;
    IMedicalCardRepository medicalCardRepository;
    IAddressRepository addressRepository;

    @Autowired
    public AppService(IUserRepository userRepository, IPassportRepository passportRepository,
                      IMedicalCardRepository medicalCardRepository, IAddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.passportRepository = passportRepository;
        this.medicalCardRepository = medicalCardRepository;
        this.addressRepository = addressRepository;
    }

    public void saveUserAccount(User newUser, Passport newUserPassport) {
        userRepository.save(newUser);
        passportRepository.save(newUserPassport);
    }

    public User findUser(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new IllegalArgumentException("User not exists"));
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("User not exists"));
    }

    public Passport findPassportByUser(User user) {
        return passportRepository.findByUser(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void savePassport(Passport passport) {
        passportRepository.save(passport);
    }

    public void deleteUser(User user) {
        passportRepository.deleteByUser(user);
    }

    public MedicalCard findMedicalCardByUser(User user) {
        return medicalCardRepository.findByOwner(user);
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    public Passport checkCurrentUser(UserDetails authUser) {
        Passport currentUserPassport = null;
        if (authUser != null) {
            User currentUser = findUser(authUser.getUsername());
            currentUserPassport = findPassportByUser(currentUser);
        }
        return currentUserPassport;
    }
}
