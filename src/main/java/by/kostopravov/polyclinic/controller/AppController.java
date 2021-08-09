package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.MedicalCard;
import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.PolyclinicDepartment;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.dto.enums.Role;
import by.kostopravov.polyclinic.sirvice.AppService;
import by.kostopravov.polyclinic.sirvice.PolyclinicDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@Controller
@RequestMapping
public class AppController {

    private AppService appService;
    private PolyclinicDepartmentService departmentService;

    @Autowired
    public AppController(AppService appService, PolyclinicDepartmentService departmentService) {
        this.appService = appService;
        this.departmentService = departmentService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails authUser, Model model) {
        Passport currentUserPassport = appService.checkCurrentUser(authUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        return "index";
    }

    @GetMapping("/registration")
    public String registrationPage(@AuthenticationPrincipal UserDetails authUser, Model model) {
        Passport currentUserPassport = appService.checkCurrentUser(authUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        return "registration";
    }

    @GetMapping("/login")
    public String loginPage(@AuthenticationPrincipal UserDetails authUser, Model model) {
        if (authUser != null) {
            Passport currentUserPassport = appService.checkCurrentUser(authUser);
            model.addAttribute("currentUserPassport", currentUserPassport);
            return "profile";
        }
        return "login";
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping("/profile")
    public String userPage(Model model, @AuthenticationPrincipal UserDetails authUser) {
        User currentUser = appService.findUser(authUser.getUsername());
        Passport currentUserPassport = appService.findPassportByUser(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        if (currentUser.getRole() == Role.ADMIN) {
            ArrayList<PolyclinicDepartment> departments = departmentService.getAllDepartments();
            model.addAttribute("departments", departments);
            return "admin";
        }
        return "profile";
    }

    @PreAuthorize("hasAuthority('browse')")
    @GetMapping("/user/{id}/card")
    public String getMedicalCardPage(@PathVariable Long id, Model model,
                                     @AuthenticationPrincipal UserDetails authUser) {
        User user = appService.findUserById(id);
        User currentUser = appService.findUser(authUser.getUsername());
        Passport currentUserPassport = appService.findPassportByUser(currentUser);
        MedicalCard medicalCard = appService.findMedicalCardByUser(user);
        model.addAttribute("medicalCard", medicalCard);
        model.addAttribute("currentUserPassport", currentUserPassport);
        return "card";
    }


    @PreAuthorize("hasAuthority('browse')")
    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id, Model model,
                          @AuthenticationPrincipal UserDetails authUser) {
        User user = appService.findUserById(id);
        Passport passport = appService.findPassportByUser(user);
        Passport currentUserPassport = appService.checkCurrentUser(authUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        model.addAttribute("user", user);
        model.addAttribute("userPassport", passport);
        return "user";
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping("/profile/card")
    public String getMyMedicalCardPage(Model model, @AuthenticationPrincipal UserDetails authUser) {
        User currentUser = appService.findUser(authUser.getUsername());
        Passport currentUserPassport = appService.findPassportByUser(currentUser);
        MedicalCard medicalCard = appService.findMedicalCardByUser(currentUser);
        model.addAttribute("medicalCard", medicalCard);
        model.addAttribute("currentUserPassport", currentUserPassport);
        return "card";
    }

}
