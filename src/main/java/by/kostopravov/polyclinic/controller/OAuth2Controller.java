package by.kostopravov.polyclinic.controller;

import by.kostopravov.polyclinic.dto.Passport;
import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.dto.enums.Role;
import by.kostopravov.polyclinic.security.UserDetailsServiceImpl;
import by.kostopravov.polyclinic.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Controller
@RequestMapping
public class OAuth2Controller {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;
    AppService appService;
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    public OAuth2Controller(AppService appService, UserDetailsServiceImpl userDetailsService) {
        this.appService = appService;
        this.userDetailsService = userDetailsService;

    }

    @GetMapping("/oauth2")
    public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {
        User currentUser = null;
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        String userInfoEndpointUri = client.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri();
        if (! StringUtils.isEmpty(userInfoEndpointUri)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());
            HttpEntity<String> entity = new HttpEntity<String>("", headers);
            ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
            Map userAttributes = response.getBody();

            currentUser = appService.findUserByGoogleAccount((String) userAttributes.get("email"));
            String login = currentUser.getPhoneNumber();

            UserDetails principal = userDetailsService.loadUserByUsername(login);
            Authentication auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);

            Passport currentUserPassport = appService.findPassportByUser(currentUser);
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("currentUserPassport", currentUserPassport);
        }

        if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
            return "admin";
        }

        return "profile";
    }

}
