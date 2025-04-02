package com.example.demo.free.entity;

import com.example.demo.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Free {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne //db에 관계를 설정하는 얼로케이션
    //나는 여러개, 상대방은 하나
    //회원 한명이 여러 글을 쓸 수 있다.
    @JoinColumn(name = "author_id", nullable = false)
    private SiteUser author;
}
