package com.example.demo.user.service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.SiteUser;
import com.example.demo.user.model.Role;
import com.example.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //하나라도 저장이 안되면 전부다 취소시킴(시작 전으로 되돌림) (DB 트랜젝션 참고) -> createUser를 통으로 관리한다는 뜻
    @Transactional
    public SiteUser createUser(UserDto dto)
    {
        if(isUsernameTaken(dto.getUsername()))
        {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
        }
        SiteUser user = new SiteUser();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public boolean isUsernameTaken(String username)
    {
        //isPresent()는 true/false값 반환하도록
        return userRepository.findByUsername(username).isPresent();
    }
    public SiteUser findByUsername(String username)
    {
        //유저가 없다면 error 를 던짐 - > 메시지 뜨도록
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

}
