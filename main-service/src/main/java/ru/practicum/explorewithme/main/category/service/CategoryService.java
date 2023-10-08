package ru.practicum.explorewithme.main.category.service;

import java.util.Collection;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.category.dto.NewCategoryDto;

public interface CategoryService {

    CategoryDto addNewCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDto);

    void deleteCategory(Long catId);

    Collection<CategoryDto> getAllCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}