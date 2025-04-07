package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.comment.service.CommentService;
import com.example.demo.free.dto.FreeDto;
import com.example.demo.user.security.SiteUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;

    @GetMapping("/form")
    public String create(Model model){
        model.addAttribute("freeDto", new FreeDto());
        return "/free/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute CommentDto commentDto, BindingResult bindingResult, @AuthenticationPrincipal SiteUserDetails siteUserDetails){
        if(bindingResult.hasErrors())
            return "/free/form";
        CommentService.createComment(commentDto, siteUserDetails.getUser());
        return "redirect:/free/list";
    }
}
