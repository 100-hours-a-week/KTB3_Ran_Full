package com.ran.community.post.dto.response;

public class PageMeta {
    private int numOfContents;
    private int limit;
    private int numOfPages;
    private int page;

    public PageMeta(int numOfContents, int limit, int numOfPages, int page) {
        this.numOfContents = numOfContents;
        this.limit = limit;
        this.numOfPages = numOfPages;
        this.page = page;

    }

    public int getNumOfContents() {
        return numOfContents;
    }
    public int getLimit() {
        return limit;
    }
    public int getNumOfPages() {
        return numOfPages;
    }
    public int getPage() {
        return page;
    }
}
