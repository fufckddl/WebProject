package com.example.demo.free;

import com.example.demo.user.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;


//Free : 엔티티
//Long : Free엔티티의 기본 키의 타입
public interface FreeRepository  extends JpaRepository<Free, Long> {
}
