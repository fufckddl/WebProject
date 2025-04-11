package com.example.demo.comment.service;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.free.entity.Free;
import com.example.demo.free.repository.FreeRepository;
import com.example.demo.free.service.FreeService;
import com.example.demo.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final FreeRepository freeRepository;
    private final FreeService freeService;

    @Transactional  // 트랜잭션 관리
    public void createComment(CommentDto dto, SiteUser author) {
        // DTO의 postId로 게시글(Free) 엔티티 조회
        Free post = freeService.findById(dto.getPostId());

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(author);
        comment.setPost(post);  // 게시글과 연관관계 설정

        commentRepository.save(comment);
    }

    // 댓글 수정하기
    public void updateComment(Long id, CommentDto dto) {
        Comment cmt = getCommentId(id);
        cmt.setContent(dto.getContent());
        commentRepository.save(cmt);
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    // 댓글 아이디로 댓글 찾기
    public Comment getCommentId(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
    }
    // 게시글과 연관된 댓글 조회 (참고용)
    public Free findById(Long id) {
        return freeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        // Free 엔티티를 기준으로 댓글을 조회하거나, 직접 쿼리를 날려 가져올 수 있습니다.
        return commentRepository.findByPost_Id(postId);
    }
}
