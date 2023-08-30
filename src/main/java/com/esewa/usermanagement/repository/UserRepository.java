package com.esewa.usermanagement.repository;

import com.esewa.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
 /*   @Query("SELECT u FROM User u WHERE u.name = :username")
    public User findByName(@Param("username") String username);*/
    public User findByName(String name);
   // public Long countByName(String name);
}
