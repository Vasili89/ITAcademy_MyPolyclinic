package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.PolyclinicDepartment;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.service.AppService;
import by.kostopravov.polyclinic.service.PolyclinicDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminControllerRest {

    private AppService appService;
    private PolyclinicDepartmentService departmentService;

    @Autowired
    public AdminControllerRest(AppService appService, PolyclinicDepartmentService departmentService) {
        this.appService = appService;
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAuthority('edit')")
    @GetMapping("/department/{id}")
    public ResponseEntity<?> getDoctorsFromDepartment(@PathVariable Long id) {
        PolyclinicDepartment polyclinicDepartment = departmentService.findDepartmentById(id);
        List<User> users = polyclinicDepartment.getDoctors();
        List<Passport> doctors = new ArrayList<>();
        for(User doctor : users) {
            Passport passport = appService.findPassportByUser(doctor);
            passport.getUser().setMedicalCard(null);
            passport.getUser().setPolyclinicDepartment(null);
            passport.getUser().setAddresses(null);
            doctors.add(passport);
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('edit')")
    @PostMapping("/department")
    public ResponseEntity<?> createDepartment(@RequestBody PolyclinicDepartment department) {
        departmentService.createNewDepartment(department);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('edit')")
    @GetMapping("/department")
    public ResponseEntity<?> getDepartments() {
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }
}
