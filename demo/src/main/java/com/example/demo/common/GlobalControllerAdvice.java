package com.example.demo.common;


import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.security.SiteUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

//모든 컨트롤로에게 공통적으로 적용되는 설정
@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute
    public void addLoggedInUserToModel(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal() instanceof SiteUserDetails userDetails)
        {
            SiteUser user = userDetails.getUser();
            model.addAttribute("loggedInUser", user);
            model.addAttribute("loginUserId", user.getId());
        }
    }
}
