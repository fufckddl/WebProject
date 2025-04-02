package com.example.demo.common;

import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.security.SiteUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping(value = "/")
    public String main(HttpSession session, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof SiteUserDetails)
        {
            SiteUserDetails userDetails = (SiteUserDetails) principal;
            model.addAttribute("loggedInUser", userDetails.getUser());
        }
        return "main";
    }
}