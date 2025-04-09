package com.example.demo.comment.service;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.free.entity.Free;
import com.example.demo.free.repository.FreeRepository;
import com.example.demo.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final FreeRepository freeRepository;

    //댓글 작성
    public void createComment(CommentDto dto, SiteUser author) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(author);
        //comment.setPost(post);

        commentRepository.save(comment);
    }

    //댓글 수정하기
    public void updateComment(Long id, CommentDto dto)
    {
        Comment cmt = getPostId(id);
        cmt.setContent(dto.getContent());
        commentRepository.save(cmt);
    }
    
    //댓글 삭제
    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }
    
    //댓글 아이디 찾기
    public Comment getPostId(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
    }

    //댓글쓴 게시글 아이디 찾기
    public Free findById(Long id)
    {
        return freeRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }
}
