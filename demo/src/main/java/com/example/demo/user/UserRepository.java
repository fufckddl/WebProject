package com.example.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByUsername(String username);
    //= SELECT * FROM site_user WHERE username = "홍길동"
}
