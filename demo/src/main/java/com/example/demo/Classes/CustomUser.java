package com.example.demo.Classes;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table
public class CustomUser implements UserDetails {
    @Id
    private String username;
    private String password;
    private String name;
    private String email;
    private String type;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->"read");
    }
}
