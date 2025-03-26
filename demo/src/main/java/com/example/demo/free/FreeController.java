package com.example.demo.free;

import com.example.demo.user.SiteUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


//생성자를 자동으로 생성해줌(final, not null, 필드들을 매개변수로 사용하는 생성자 자동생성), Lombok의 기능
@RequiredArgsConstructor
@Controller
public class FreeController {
    private final FreeService freeService;


    @GetMapping("/free/list")
    public String list(HttpSession session, Model model){
        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser");
        model.addAttribute("loggedInUser", loggedInUser);
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
    public String create(@RequestParam String title, @RequestParam String content, HttpSession session)
    {
        SiteUser loggedInUser = (SiteUser) session.getAttribute("loggedInUser");
        if(loggedInUser == null)
            return "redirect:/user/login";
        freeService.createPost(title, content);
        return "/free/list";
    }
}
