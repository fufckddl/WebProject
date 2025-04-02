package com.example.demo.user.repository;

import com.example.demo.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    //Optional : null일 수도 있는 객체를 감싸는 일종의 Wrapper 클래스 (값의 유무에 관계없이 객체를 감쌈)
    //자바에서 null일때 프로그램이 멈춰버리니까 빈박스라도 보내줘서 프로그램이 종료되지 않도록 보호함.
    //null값에 대한 안전 처리
    Optional<SiteUser> findByUsername(String username);
    //= SELECT * FROM site_user WHERE username = "홍길동"
}
