package com.ran.community.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUpdatedFormDto {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max=24, message = "제목은 24자 이하로 작성 가능합니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    //nullable = true
    public String imgUrl;

}