package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.Address;
import by.kostopravov.polyclinic.dto.Diagnosis;
import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.sirvice.AddressService;
import by.kostopravov.polyclinic.sirvice.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class AppControllerRest {

    AppService appService;
    AddressService addressService;

    @Autowired
    public AppControllerRest(AppService appService, AddressService addressService) {
        this.appService = appService;
        this.addressService = addressService;
    }

    @PutMapping("/user")
    public ModelAndView saveUser(@ModelAttribute("userForEdit") @Valid User user,
                                 BindingResult bindingResultUser) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResultUser.hasErrors()) {
            modelAndView.setViewName("edituser");
            return modelAndView;
        }
        appService.saveUser(user);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PutMapping("/user/passport")
    public ModelAndView saveUserPassport(@ModelAttribute("passportForEdit") @Valid Passport passport,
                                 BindingResult bindingResultUser) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResultUser.hasErrors()) {
            modelAndView.setViewName("editpassport");
            return modelAndView;
        }
        appService.savePassport(passport);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PreAuthorize("hasAuthority('delete')")
    @DeleteMapping(path = "/user/{id}")
    public void deleteUserById(@PathVariable Long id, HttpServletResponse response) {
        User userForDelete = appService.findUserById(id);
        appService.deleteUser(userForDelete);
        try {
            response.sendRedirect("/polyclinic");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/user/card")
    public ModelAndView saveDiagnosisToCart(@ModelAttribute("newDiagnosis") Diagnosis diagnosis) {
        ModelAndView modelAndView = new ModelAndView();


        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @PostMapping("/user")
    public void createNewUser(@RequestBody Passport passport) {
        User user = new User(passport.getUser().getPhoneNumber(),
                new BCryptPasswordEncoder(12).encode(passport.getUser().getPassword()));
        Address newAddress = passport.getUser().getAddresses().get(0);
        Address address = addressService.checkAddress(newAddress);
        user.getAddresses().add(address);
        passport.setUser(user);
        appService.savePassport(passport);
    }

}
