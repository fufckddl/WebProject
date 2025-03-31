package com.example.demo.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    //생성자
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //유저 생성 SAVE는 JPA에서 제공
    public void createSiteUser(SiteUser siteUser)
    {
        userRepository.save(siteUser);
    }
    public SiteUser validateUser(String username, String password)
    {
        SiteUser user = userRepository.findByUsername(username);
        if(user != null && user.getPassword().equals(password))
        {
            return user;
        }
        return null;

    }
}
