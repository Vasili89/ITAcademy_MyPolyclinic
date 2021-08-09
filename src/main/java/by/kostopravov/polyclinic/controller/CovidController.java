package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.covidModel.Location;
import by.kostopravov.polyclinic.service.AppService;
import by.kostopravov.polyclinic.service.covidDataService.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/covid")
public class CovidController {

    private final CovidDataService covidService;
    private final AppService appService;

    @Autowired
    public CovidController(CovidDataService covidService, AppService appService) {
        this.covidService = covidService;
        this.appService = appService;
    }

    @GetMapping
    public String covidPage(@AuthenticationPrincipal UserDetails authUser, Model model) {

        Passport currentUserPassport = appService.checkCurrentUser(authUser);
        model.addAttribute("currentUserPassport", currentUserPassport);

        List<Location> reportList = covidService.getVirusData();
        int totalCases = reportList.stream().mapToInt(Location::getLastTotalCases).sum();
        int dayTotalCases = reportList.stream().mapToInt(Location::getDelta).sum();
        model.addAttribute("locations", reportList);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("dayTotalCases", dayTotalCases);

        return "covid";
    }
}
