package com.nnk.springboot.security;

import com.nnk.springboot.domain.UserDb;
import com.nnk.springboot.exception.RequestException;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security service to load user details from the database.
 * Implements UserDetailsService for authentication via username.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user to load
     * @return a UserDetails object containing user information and authorities
     * @throws UsernameNotFoundException if the user does not exist
     * @throws RequestException          if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("research user... " + username);
        UserDb userDb = userRepository.findUserByUsername(username);

        if (userDb == null) {
            throw new RequestException("unknown user");
        }

        return new User(userDb.getUsername(), userDb.getPassword(), getGrantedAuthorities(userDb.getRole()));
    }

    /**
     * Creates a list of Spring Security authorities from the user's role.
     *
     * @param role the user's role from the database (e.g. "USER", "ADMIN")
     * @return a list of GrantedAuthority with the "ROLE_" prefix
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return authorities;
    }

}
