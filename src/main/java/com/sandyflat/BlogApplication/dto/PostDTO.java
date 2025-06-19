package com.sandyflat.BlogApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDTO categories;

    private UserDTO user;

    private Set<CommentDTO> comments = new HashSet<>();
}
