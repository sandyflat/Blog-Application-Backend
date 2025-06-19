package com.sandyflat.BlogApplication.serviceimpl;

import com.sandyflat.BlogApplication.entity.Categories;
import com.sandyflat.BlogApplication.exception.ResourceNotFoundException;
import com.sandyflat.BlogApplication.dto.CategoryDTO;
import com.sandyflat.BlogApplication.repository.CategoryRepository;
import com.sandyflat.BlogApplication.service.CategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Categories categories = modelMapper.map(categoryDTO, Categories.class);
        Categories addedCategories = categoryRepository.save(categories);

        return modelMapper.map(addedCategories, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        categories.setCategoryTitle(categoryDTO.getCategoryTitle());
        categories.setCategoryDescription(categoryDTO.getCategoryDescription());
        Categories updatedCategories = categoryRepository.save(categories);

        return modelMapper.map(updatedCategories, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        categoryRepository.delete(categories);
    }

    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Categories categories = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

        return modelMapper.map(categories, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Categories> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map((cat) -> modelMapper.map(cat, CategoryDTO.class)).toList();
        return categoryDTOS;
    }
}
