package com.proyect.instarecipes.views.DTO;

import com.fasterxml.jackson.annotation.JsonView;

public class UserLikeCommentDTO {

    public interface UserLikeView{}

    @JsonView(UserLikeView.class)
    Long id_user;
    
    @JsonView(UserLikeView.class)
    boolean isSubcomment;

    public UserLikeCommentDTO(Long id_user, boolean isSubcomment) {
        this.id_user = id_user;
        this.isSubcomment = isSubcomment;
    }

    public UserLikeCommentDTO(boolean isSubcomment) {
        this.isSubcomment = isSubcomment;
    }


}