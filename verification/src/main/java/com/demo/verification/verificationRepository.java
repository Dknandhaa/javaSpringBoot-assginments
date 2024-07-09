package com.demo.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface verificationRepository extends JpaRepository<verificationToken, Long> {
    verificationToken findByToken(String token);
}
