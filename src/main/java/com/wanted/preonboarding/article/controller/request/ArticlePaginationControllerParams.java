package com.wanted.preonboarding.article.controller.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArticlePaginationControllerParams {
    private String page;
    private String size;

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Integer getPage() {
        if(!isNumeric(this.page)) {
            return 0;
        }

        int page = Integer.parseInt(this.page);

        return page == 0 ? 0 : page * getSize() + 1;
    }

    public Integer getSize() {
        if(!isNumeric(this.size)) {
            return 10;
        }

        return Integer.parseInt(this.size);
    }
}
