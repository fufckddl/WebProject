package com.example.demo.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 2, max = 100, message = "내용은 2~100자여야 합니다.")
    private String content;
}
