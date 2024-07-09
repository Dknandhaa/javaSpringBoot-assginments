package com.demo.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class userService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private verificationRepository tokenRepository;

    @Autowired
    private emailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        verificationToken verificationToken = new verificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        tokenRepository.save(verificationToken);

        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    public boolean verifyUser(String token) {
        verificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken != null) {
            users user = verificationToken.getUser();
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
