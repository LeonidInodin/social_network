package ru.inodinln.social_network.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.inodinln.social_network.services.UserService;

@Service
public class BasicUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;

    public BasicUserDetailsService(UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String eMail) throws UsernameNotFoundException {
        return UserDetailsFactory.create(userService.getByEmail(eMail));

    }
}
