package com.example.demo.user.security;

import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SiteUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        SiteUser user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다." + username));
        return new SiteUserDetails(user);
    }
}
