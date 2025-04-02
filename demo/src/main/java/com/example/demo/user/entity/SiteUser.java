package com.example.demo.user.entity;


import com.example.demo.free.entity.Free;
import com.example.demo.user.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    //나는 하나고, 상대방은 여러개
    //누가 글을 썻는지에 대한 정보가 Free클래스에 존재
    //회원 한명이 여러개의 글을 쓸 수 있다.
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Free> posts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @PrePersist //자동실행 어로케이션
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

}
