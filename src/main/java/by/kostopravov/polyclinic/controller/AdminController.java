package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.service.AppService;
import by.kostopravov.polyclinic.service.PolyclinicDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private AppService appService;
    private PolyclinicDepartmentService departmentService;

    @Autowired
    public AdminController(AppService appService, PolyclinicDepartmentService departmentService) {
        this.appService = appService;
        this.departmentService = departmentService;
    }

    @PreAuthorize("hasAuthority('edit')")
    @GetMapping("/user/{id}")
    public String getUserForAdmin(@PathVariable Long id, Model model,
                                  @AuthenticationPrincipal UserDetails authUser) {
        User user = appService.findUserById(id);
        Passport passport = appService.findPassportByUser(user);
        Passport currentUserPassport = appService.checkCurrentUser(authUser);
        model.addAttribute("currentUserPassport", currentUserPassport);
        model.addAttribute("user", user);
        model.addAttribute("userPassport", passport);
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "userForAdmin";
    }
}
