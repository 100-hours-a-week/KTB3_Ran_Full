package com.ran.community.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentUpdatedFormDto {
    @NotBlank(message = "내용을 입력해주세요.")
    private String Content;

}
