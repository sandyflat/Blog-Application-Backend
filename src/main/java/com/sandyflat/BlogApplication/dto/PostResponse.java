package com.sandyflat.BlogApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private List<PostDTO> content;

    private int pageNumber;

    private int pageSize;

    private long totalElement;

    private int totalPages;

    private boolean lastPage;
}
