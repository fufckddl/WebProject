package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.service.CommentService;
import com.example.demo.free.service.FreeService;
import com.example.demo.user.security.SiteUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    //private final CommentRepository commentRepository;

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        // id는 수정하려는 댓글의 id
        Comment cmt = commentService.getCommentId(id);
        // 댓글 내용을 DTO에 복사
        CommentDto dto = new CommentDto();
        dto.setContent(cmt.getContent());
        dto.setPostId(cmt.getPost().getId());
        model.addAttribute("cmtDto", dto);
        model.addAttribute("cmtId", cmt.getId());
        return "/comment/edit";
    }

    @PostMapping("/edit/{id}")
    public String modify(@PathVariable Long id, @Valid @ModelAttribute CommentDto cmtDto,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal SiteUserDetails userDetails) {
        if(userDetails == null) //로그인 안한 사용자는 로그인 하도록
            return "/user/login";

        if(bindingResult.hasErrors()) {
            return "/comment/edit/" + id;  // 수정 폼으로 돌아가도록 수정
        }

        Comment cmt = commentService.getCommentId(id);
        if(!cmt.getAuthor().getId().equals(userDetails.getUser().getId())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        // 댓글을 업데이트합니다.
        commentService.updateComment(id, cmtDto);

        // 댓글 수정 후 해당 댓글이 소속된 게시글의 상세 페이지로 리다이렉트
        return "redirect:/free/detail/" + cmt.getPost().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal SiteUserDetails userDetails) {
        Comment cmt = commentService.getCommentId(id); //댓글 아이디가져오기
        if(userDetails == null)
            return "/user/login";
        if(!cmt.getAuthor().getId().equals(userDetails.getUser().getId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        commentService.deleteComment(id);
        return "redirect:/free/detail/" + id;
    }

}
