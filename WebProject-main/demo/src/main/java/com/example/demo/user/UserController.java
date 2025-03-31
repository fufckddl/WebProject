package com.example.demo.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    //생성자
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/user/signup") //GET: 정보를 받아옴
    public String signup(){
        return "/user/signup";
    }

    @PostMapping("/user/signup") //POST : DB에 정보를 보냄
    public String signup(@ModelAttribute SiteUser siteUser){
        userService.createSiteUser(siteUser); //유저 생성(회원가입 할때)
        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }

    @PostMapping("/user/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model)
    {
        SiteUser user = userService.validateUser(username, password);
        if(user != null)
        {
            session.setAttribute("loggedInUser", user);
            return "redirect:/";
        }else {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "/user/login";
        }
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session)
    {
        session.invalidate(); //invalidate 세션 연결을 끊는다는 뜻 (정보 삭제)
        return "redirect:/";
    }
}
