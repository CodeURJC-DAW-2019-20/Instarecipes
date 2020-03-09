package com.proyect.instarecipes.api;

public class CommentDto {
    private String content;
    private Long ParentComment;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentComment() {
        return ParentComment;
    }

    public void setParentComment(Long parentComment) {
        ParentComment = parentComment;
    }
}