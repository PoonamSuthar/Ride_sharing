package com.example.demo.Repositories;

import com.example.demo.Classes.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<CustomUser, String> {
    Optional<CustomUser> findByUsername(String username);
}
