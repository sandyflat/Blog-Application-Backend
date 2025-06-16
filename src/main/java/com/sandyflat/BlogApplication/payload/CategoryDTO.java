package com.sandyflat.BlogApplication.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDTO {
    private Long categoryId;

    @NotBlank(message = "category title must not be blank")
    @Size(min = 5, message = "minimum size of category title is 4")
    private String categoryTitle;

    @NotBlank(message = "category description must not be blank")
    @Size(min = 10, message = "minimum size of category description is 10")
    private String categoryDescription;
}
