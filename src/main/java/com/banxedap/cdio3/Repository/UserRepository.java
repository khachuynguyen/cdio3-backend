package com.banxedap.cdio3.Repository;


import com.banxedap.cdio3.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>  findUsersByUserName(String userName);
}
