package com.capstone_project.hbts.repository;

import com.capstone_project.hbts.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users getUsersByUsername(String username);

    Users getUsersByEmail(String email);

    @Query(value = "select u from Users u where u.username like lower(concat('%',:text,'%')) ")
    List<Users> searchUserByUsername(@Param("text") String text);

}
