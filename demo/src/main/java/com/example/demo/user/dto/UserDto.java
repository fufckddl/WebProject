package com.example.demo.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    //null 이나 빈칸은 허용하지 않는다.
    @NotBlank(message = "아이디는 필수입니다.")
    //Size 3~20자
    @Size(min =3, max = 20, message="아이디는 3자 이상 20자 이하로 입력하세요.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min =3, message="비밀번호는 최소 3자 이상이어야 합니다.")
    private String password;

}
