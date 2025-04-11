package com.example.demo.free.controller;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.service.CommentService;
import com.example.demo.free.dto.FreeDto;
import com.example.demo.free.entity.Free;
import com.example.demo.free.service.FreeService;
import com.example.demo.user.security.SiteUserDetails;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/free")
public class FreeController {
    private final CommentService commentService;
    private final FreeService freeService;

    @GetMapping("/list")
    public String list(Model model, @AuthenticationPrincipal SiteUserDetails userDetails){
        List<Free> posts = freeService.findAll();
        model.addAttribute("posts", posts);
        return "/free/list";
    }

    @GetMapping("/form")
    public String create(Model model){
        model.addAttribute("freeDto", new FreeDto());
        return "/free/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute FreeDto freeDto, BindingResult bindingResult, @AuthenticationPrincipal SiteUserDetails siteUserDetails){
        if(bindingResult.hasErrors())
            return "/free/form";
        if(siteUserDetails == null)
            return "/user/login";
        freeService.create(freeDto, siteUserDetails.getUser());
        return "redirect:/free/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Free post = freeService.findById(id);
        CommentDto cmtDto = new CommentDto();
        cmtDto.setPostId(post.getId());  // 여기서 postId를 미리 설정합니다.
        model.addAttribute("post", post);
        List<Comment> comments = commentService.getCommentsByPostId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("cmtDto", cmtDto);
        return "/free/detail";
    }


    @PostMapping("/detail/{id}")
    public String writeComment(@PathVariable("id") Long id,
                               @Valid @ModelAttribute CommentDto cmtDto,
                               BindingResult bindingResult,
                               @AuthenticationPrincipal SiteUserDetails siteUserDetails,
                               Model model) {
        if (bindingResult.hasErrors()) {
            Free post = freeService.findById(id);
            model.addAttribute("post", post);
            model.addAttribute("cmtDto", cmtDto);
            System.out.println("error: " + bindingResult.getAllErrors());
            return "/free/detail";
        }
        // postId는 GET에서 설정해주었으므로 굳이 다시 설정할 필요 없습니다.
        commentService.createComment(cmtDto, siteUserDetails.getUser());
        return "redirect:/free/detail/" + id;
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Free post = freeService.findById(id);
        FreeDto dto = new FreeDto();
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        model.addAttribute("freeDto", dto);
        model.addAttribute("postId", id);
        return "/free/form";
    }

    @PostMapping("/edit/{id}")
    public String modify(@PathVariable Long id, @Valid @ModelAttribute FreeDto freeDto,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal SiteUserDetails userDetails) {
        if(bindingResult.hasErrors())
            return "/free/form";
        if(userDetails == null)
            return "/user/login";
        Free post = freeService.findById(id);
        if(!post.getAuthor().getId().equals(userDetails.getUser().getId())) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }

        freeService.update(id, freeDto);
        return "redirect:/free/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal SiteUserDetails userDetails) {
        Free post = freeService.findById(id);
        if(userDetails == null)
            return "/user/login";
        if(!post.getAuthor().getId().equals(userDetails.getUser().getId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        freeService.delete(id);
        return "redirect:/free/list";
    }
}
