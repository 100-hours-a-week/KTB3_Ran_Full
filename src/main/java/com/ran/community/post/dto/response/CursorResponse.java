package com.ran.community.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CursorResponse<T> {
    private List<T> content;
    private Object nextCursor;
    private boolean hasNext;

    public static <T> CursorResponse<T> of(List<T> content, Object nextCursor) {
        return new CursorResponse<>(
                content,
                nextCursor,
                nextCursor != null
        );
    }
}