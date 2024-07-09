package com.demo.verification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
public class verificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private users user;

}

//    @OneToOne(targetEntity = users.class, fetch = FetchType.EAGER)
//@Entity
//public class verificationToken {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String token;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private users user;
//
//    // Getters and setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getToken() { return token; }
//    public void setToken(String token) { this.token = token; }
//    public users getUser() { return user; }
//    public void setUser(users user) { this.user = user; }
//}
