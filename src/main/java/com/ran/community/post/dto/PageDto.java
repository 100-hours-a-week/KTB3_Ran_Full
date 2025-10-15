package com.ran.community.post.dto;

import java.util.ArrayList;
import java.util.List;

public class PageDto {
    private PageMeta pageMeta;
    private List<PostDto> offsetPosts;

    public PageDto() {}
    public PageDto(int numOfContents, int limit, int numOfPages, int page, List<PostDto> offsetPosts) {
        this.pageMeta = new PageMeta(numOfContents, limit, numOfPages, page);
        this.offsetPosts = new ArrayList<PostDto>();
    }
    public PageMeta getPageMeta() {
        return pageMeta;
    }
    public void setPageMeta(PageMeta pageMeta) {
        this.pageMeta = pageMeta;
    }

    public List<PostDto> getOffsetPosts() {
        return offsetPosts;
    }
    public void setOffsetPosts(List<PostDto> offsetPosts) {
        this.offsetPosts = offsetPosts;
    }
}
