package com.example.demo.Auth;


import com.example.demo.Classes.CustomUser;
import com.example.demo.Repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(
                ">>  loadUserByName called for {" + username + "}"
        );
        CustomUser customUser = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The user with this username doesn't exist"));

        PasswordEncoder pe = SecurityConfig.passwordEncoder();
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        if (customUser.getType().equals("ADMIN")) {
            System.out.println(">>  Assigning role of ADMIN to " + customUser.getUsername());
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else {
            System.out.println(">>  Assigning role of NORMAL_USER to " + customUser.getUsername());
            authorities.add(new SimpleGrantedAuthority("ROLE_NORMAL"));
        }

        return  org.springframework.security.core.userdetails.User
                .withUsername(customUser.getUsername())
                .password(pe.encode(customUser.getPassword()))
                .authorities(authorities)
                .build();

    }
}