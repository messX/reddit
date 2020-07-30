package com.example.messx.redit.repository;

import com.example.messx.redit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="SELECT * FROM user t where t.user_name = ?1", nativeQuery = true)
    Optional<User> findByUserName(String username);
}
