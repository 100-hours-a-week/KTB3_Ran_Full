package com.ran.community.post.dto.response;

public class PageMeta {
    private int numOfContents;
    private int limit;
    private int numOfPages;
    private int page;

    public PageMeta() {}
    public PageMeta(int numOfContents, int limit, int numOfPages, int page) {
        this.numOfContents = numOfContents;
        this.limit = limit;
        this.numOfPages = numOfPages;
        this.page = page;

    }

    public int getNumOfContents() {
        return numOfContents;
    }
    public void setNumOfContents(int numOfContents) {
        this.numOfContents = numOfContents;
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public int getNumOfPages() {
        return numOfPages;
    }
    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
}
