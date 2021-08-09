package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.*;
import by.kostopravov.polyclinic.dto.enums.Role;
import by.kostopravov.polyclinic.sirvice.AddressService;
import by.kostopravov.polyclinic.sirvice.AppService;
import by.kostopravov.polyclinic.sirvice.DiagnosisService;
import by.kostopravov.polyclinic.sirvice.PolyclinicDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestController
public class AppControllerRest {

    AppService appService;
    AddressService addressService;
    DiagnosisService diagnosisService;
    PolyclinicDepartmentService departmentService;

    @Autowired
    public AppControllerRest(AppService appService, AddressService addressService,
                             DiagnosisService diagnosisService, PolyclinicDepartmentService departmentService) {
        this.appService = appService;
        this.addressService = addressService;
        this.diagnosisService = diagnosisService;
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAuthority('edit')")
    @PutMapping("/user/{id}/passport")
    public ResponseEntity<?> editPassport(@PathVariable Long id, @RequestBody Passport passport) {
        try {
            User user = appService.findUserById(id);
            passport.setUser(user);
            passport.setGender(appService.findPassportByUser(user).getGender());
            appService.savePassport(passport);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionSystemException | NullPointerException | ConstraintViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('edit')")
    @PutMapping("/user/{id}")
    public ResponseEntity<?> editUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            User user = appService.findUserById(id);
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setGoogleEmail(updatedUser.getGoogleEmail());
            user.setRole(updatedUser.getRole());
            if (user.getRole() != Role.DOCTOR) {
                user.setPolyclinicDepartment(null);
            }
            user.setStatus(updatedUser.getStatus());
            appService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (TransactionSystemException | NullPointerException | ConstraintViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('edit')")
    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        User userForDelete = appService.findUserById(id);
        appService.deleteUser(userForDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('edit')")
    @DeleteMapping(path = "/user/{userId}/address/{addressId}")
    public ResponseEntity<?> deleteAddressFromUser(@PathVariable Long userId, @PathVariable Long addressId) {
        User user = appService.findUserById(userId);
        user.getAddresses().removeIf(address -> address.getId() == addressId);
        appService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createNewUser(@RequestBody Passport passport) {
        try {
            User user = new User(passport.getUser().getPhoneNumber(),
                    new BCryptPasswordEncoder(12).encode(passport.getUser().getPassword()));
            Address newAddress = passport.getUser().getAddresses().get(0);
            Address address = addressService.checkAddress(newAddress);
            user.getAddresses().add(address);
            passport.setUser(user);
            appService.savePassport(passport);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TransactionSystemException | NullPointerException | ConstraintViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('browse')")
    @GetMapping(value = "/user/phone/{phone}", produces = "application/json")
    public ResponseEntity<?> getUserAccount(@PathVariable String phone) {
        User user = appService.findUserByPhone(phone);
        Passport passport = appService.findPassportByUser(user);
        passport.getUser().setAddresses(null);
        passport.getUser().setMedicalCard(null);
        passport.getUser().setPolyclinicDepartment(null);
        return new ResponseEntity<>(passport, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/user/{id}/diagnosis")
    public ResponseEntity<?> createNewDiagnosis(@PathVariable Long id, @RequestBody Diagnosis diagnosis) {
        try {
            diagnosis.setDateTime(LocalDateTime.now());
            User owner = appService.findUserById(id);
            User doctor = appService.findUserById(diagnosis.getDoctor().getId());
            owner.getMedicalCard().getDiagnosis().add(diagnosis);
            diagnosis.setMedicalCard(owner.getMedicalCard());
            diagnosis.setDoctor(doctor);
            diagnosisService.saveDiagnosis(diagnosis);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (TransactionSystemException | NullPointerException | ConstraintViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('edit')")
    @PutMapping(path = "/user/{id}/address")
    public ResponseEntity<?> addAddress(@PathVariable Long id, @RequestBody Address newAddress) {
        User user = appService.findUserById(id);
        Address address = addressService.checkAddress(newAddress);
        user.getAddresses().add(address);
        appService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('edit')")
    @PutMapping(path = "/user/{id}/department")
    public ResponseEntity<?> editDepartment(@PathVariable Long id, @RequestBody PolyclinicDepartment department) {
        User user = appService.findUserById(id);
        PolyclinicDepartment newDepartment = departmentService.findDepartmentById(department.getId());
        user.setPolyclinicDepartment(newDepartment);
        appService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

