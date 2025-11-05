package com.ran.community.post.dto.response;

import lombok.Getter;

@Getter
public class ViewCountDto {
    private int viewCount;

    public ViewCountDto(int viewCount) {
        this.viewCount = viewCount;
    }
}
