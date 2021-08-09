package by.kostopravov.polyclinic.sirvice;

import by.kostopravov.polyclinic.dto.Address;
import by.kostopravov.polyclinic.dto.MedicalCard;
import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class AppService {

    IUserRepository userRepository;
    IPassportRepository passportRepository;
    IMedicalCardRepository medicalCardRepository;
    IAddressRepository addressRepository;
    IDiagnosisRepository diagnosisRepository;

    @Autowired
    public AppService(IUserRepository userRepository, IPassportRepository passportRepository,
                      IMedicalCardRepository medicalCardRepository, IAddressRepository addressRepository,
                      IDiagnosisRepository diagnosisRepository) {
        this.userRepository = userRepository;
        this.passportRepository = passportRepository;
        this.medicalCardRepository = medicalCardRepository;
        this.addressRepository = addressRepository;
        this.diagnosisRepository = diagnosisRepository;
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

    @Transactional
    public void deleteUser(User user) {
        diagnosisRepository.deleteByMedicalCard(user.getMedicalCard());
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

    public User findUserByPhone(String phone) {
        return userRepository.findByPhoneNumber(phone).orElseThrow(
                () -> new IllegalArgumentException("User not found"));
    }

    public User findUserByGoogleAccount(String googleEmail) {
        return userRepository.findByGoogleEmail(googleEmail).orElseThrow(
                () -> new IllegalArgumentException("User not found"));
    }
}
