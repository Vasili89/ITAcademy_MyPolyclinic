package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.Diagnosis;
import by.kostopravov.polyclinic.dto.MedicalCard;
import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.sirvice.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping
public class AppController {

    private AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails authUser, Model model) {
        Passport currentUserPassport = appService.checkCurrentUser(authUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        return "index";
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

    @GetMapping("/registration")
    public String registrationPage(@AuthenticationPrincipal UserDetails authUser, Model model) {
        Passport currentUserPassport = appService.checkCurrentUser(authUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        return "registration";
    }

    @GetMapping("/profile")
    public String userPage(Model model, @AuthenticationPrincipal UserDetails authUser) {
        User currentUser = appService.findUser(authUser.getUsername());
        Passport currentUserPassport = appService.findPassportByUser(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        return "profile";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/user/{id}")
    public String getPageForEditUserAccount(@PathVariable Long id, Model model) {
        User userForEdit = appService.findUserById(id);
        model.addAttribute("userForEdit", userForEdit);
        return "edituser";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/user/{id}/passport")
    public String getPageForEditUserPassport(@PathVariable Long id, Model model) {
        User user = appService.findUserById(id);
        Passport passport = appService.findPassportByUser(user);
        model.addAttribute("passportForEdit", passport);
        return "editpassport";
    }

    @PreAuthorize("hasAuthority('write')")
    @GetMapping("/user/{id}/card")
    public String getPageForCreateDiagnosis(@PathVariable Long id, Model model,
                                            @AuthenticationPrincipal UserDetails authUser) {
        User user = appService.findUserById(id);
        User currentUser = appService.findUser(authUser.getUsername());
        MedicalCard medicalCard = appService.findMedicalCardByUser(user);
        model.addAttribute("medicalCard", medicalCard);
        model.addAttribute("newDiagnosis", new Diagnosis());
        model.addAttribute("doctor", currentUser);
        return "card";
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/finduser")
    public String getPageWithUserInformation(Model model,
                            @RequestParam String phoneNumberForFindUser,
                            @AuthenticationPrincipal UserDetails authUser) {
        User findUser = appService.findUser(phoneNumberForFindUser);
        Passport findUserPassport = appService.findPassportByUser(findUser);
        User currentUser = appService.findUser(authUser.getUsername());
        model.addAttribute("findUser", findUser);
        model.addAttribute("findUserPassport", findUserPassport);
        model.addAttribute("currentUser", currentUser);
        return "user";
    }

}
