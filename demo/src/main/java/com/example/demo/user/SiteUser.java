package com.example.demo.user;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SiteUser {
    @Id //기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; //로그인 id

    @Column(nullable = false)
    private String password; //로그인 pw

}
