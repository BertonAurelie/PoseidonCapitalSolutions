package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<UserDb, Integer>, JpaSpecificationExecutor<UserDb> {
    UserDb findUserByUsername(String username);
}
