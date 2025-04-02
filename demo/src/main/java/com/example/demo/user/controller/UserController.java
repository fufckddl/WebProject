package com.example.demo.user.controller;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor //생성자 자동생성
@Controller
//@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    
    @GetMapping("/user/signup") //GET: 정보를 받아옴
    public String signup(Model model){
        model.addAttribute("userDto", new UserDto());
        return "/user/signup";
    }

    /*@PostMapping("/signup") //POST : DB에 정보를 보냄
    public String signup(@ModelAttribute SiteUser siteUser){
        userService.createSiteUser(siteUser); //유저 생성(회원가입 할때)
        return "redirect:/";
    }*/

    @PostMapping("/user/signup")
    //유효성 검사(입력한 것 검사) @Valid
    public String signup(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "/user/signup";
        }
        //유효성 검사(입력한 것 검사)
        if(userService.isUsernameTaken(userDto.getUsername()))
        {
            bindingResult.rejectValue("username", "duplicate", "이미 사용중인 아이디 입니다.");
            return "/user/signup";
        }
        userService.createUser(userDto);
        return "redirect:/user/login";
    }

    @GetMapping("/user/login")
    public String login(){
        return "/user/login";
    }
}
