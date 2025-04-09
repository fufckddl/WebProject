package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.comment.service.CommentService;
import com.example.demo.free.entity.Free;
import com.example.demo.free.service.FreeService;
import com.example.demo.user.security.SiteUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final FreeService freeService;
    //private final CommentRepository commentRepository;

    @GetMapping("/write")
    public String create(@RequestParam("postId") Long postId, Model model, Long id){
        CommentDto dto = new CommentDto();
        dto.setPostId(postId);
        model.addAttribute("cmtDto", dto);
        return "/comment/write";
    }

    @PostMapping("/write")
    public String create(@Valid @ModelAttribute CommentDto commentDto,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal SiteUserDetails siteUserDetails) {

        if (bindingResult.hasErrors()) {
            return "/comment/write";
        }

        commentService.createComment(commentDto, siteUserDetails.getUser());

        return "redirect:/free/detail/" + commentDto.getPostId();
    }

}
