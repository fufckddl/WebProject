package com.example.demo.comment.repository;

import com.example.demo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


//Free : 엔티티
//Long : Free엔티티의 기본 키의 타입
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_Id(Long postId);
}
