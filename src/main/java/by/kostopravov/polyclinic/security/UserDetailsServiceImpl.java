package by.kostopravov.polyclinic.security;

import by.kostopravov.polyclinic.dto.User;
import by.kostopravov.polyclinic.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepository.findByPhoneNumber(login).orElseThrow(() ->
                new IllegalArgumentException("User not exists"));
        return SecurityUser.fromUserAccount(user);
    }

}
