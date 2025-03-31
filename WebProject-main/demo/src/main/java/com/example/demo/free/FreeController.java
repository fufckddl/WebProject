package com.example.demo.free;

import com.example.demo.user.SiteUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


//생성자를 자동으로 생성해줌(final, not null, 필드들을 매개변수로 사용하는 생성자 자동생성), Lombok의 기능
@RequiredArgsConstructor
@Controller
public class FreeController {
    private final FreeService freeService;


    @GetMapping("/free/list")
    public String list(HttpSession session, Model model){
        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);

        model.addAttribute("posts", freeService.getAllPosts());
        return "/free/list";
    }

    @GetMapping("/free/create")
    public String create(HttpSession session){
        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null)
            return "redirect:/user/login";
        return "/free/create";
    }

    @PostMapping("/free/create")
    public String create(@RequestParam String title, @RequestParam String content, HttpSession session, Model model)
    {

        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser");//가져오기

        if(loggedInUser == null)
            return "redirect:/user/login";


        freeService.createPost(title, content, loggedInUser);
        return "redirect:/free/list";
    }

    @GetMapping("/free/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, HttpSession session) {
        Free post = freeService.getPostId(id); // 게시글 가져오기
        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser"); // 로그인한 사용자 가져오기
        String authorName = post.getAuthor().getUsername();
        model.addAttribute("authorName", authorName);
        model.addAttribute("loggedInUser", loggedInUser);
        if(loggedInUser == null)
            return "redirect:/user/login";
        if(post.getAuthor().getId().equals(loggedInUser.getId()))
            model.addAttribute("canUpdate", true);
        model.addAttribute("post", post);
        return "/free/detail";
    }

    //수정
    @GetMapping("/free/modify/{id}")
    public String modify(@PathVariable("id") Long id, Model model, HttpSession session){
        Free post = freeService.getPostId(id);
        model.addAttribute("post", post);
        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null)
            return "redirect:/user/login";
        return "/free/modify";
    }

    @PostMapping("/free/modify/{id}")
    public String modify(@PathVariable("id") Long id, @RequestParam String title, @RequestParam String content, HttpSession session)
    {
        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser");
        Free post = freeService.getPostId(id); //post 아이디 가져오기
        if(loggedInUser == null)
            return "redirect:/user/login";

        freeService.modifyPost(id, title, content, loggedInUser);
        return "redirect:/free/detail/" + id;
    }

    @PostMapping("/free/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        freeService.deletePost(id);
        return "redirect:/free/list";
    }
}
