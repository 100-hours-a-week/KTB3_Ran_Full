package com.ran.community.post.dto.response;

import com.ran.community.post.entity.Post;

import java.util.ArrayList;
import java.util.List;

public class PageDto {
    private PageMeta pageMeta;
    private List<Post> offsetPosts;

    public PageDto(PageMeta pageMeta, List<Post> offsetPosts) {
        this.pageMeta = pageMeta;
        this.offsetPosts = offsetPosts;
    }
    public PageMeta getPageMeta() {
        return pageMeta;
    }
    public void setPageMeta(PageMeta pageMeta) {
        this.pageMeta = pageMeta;
    }

    public List<Post> getOffsetPosts() {
        return offsetPosts;
    }
    public void setOffsetPosts(List<Post> offsetPosts) {
        this.offsetPosts = offsetPosts;
    }
}
