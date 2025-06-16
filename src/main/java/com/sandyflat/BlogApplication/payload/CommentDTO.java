package com.sandyflat.BlogApplication.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {

    private Long id;

    private String content;

}
