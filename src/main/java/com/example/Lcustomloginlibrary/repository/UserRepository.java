package com.example.Lcustomloginlibrary.repository;


import com.example.Lcustomloginlibrary.entity.UserAccount;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<UserAccount, String > {
    Optional<UserAccount> findByUsername(String username);
}
