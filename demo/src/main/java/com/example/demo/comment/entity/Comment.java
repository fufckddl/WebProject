package com.example.demo.comment.entity;

import com.example.demo.free.entity.Free;
import com.example.demo.user.entity.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private SiteUser author;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Free postId;

}
