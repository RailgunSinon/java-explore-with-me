package ru.practicum.explorewithme.main.category.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.category.model.Category;

@Component
public class CategoryMapper {

    public CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public List<CategoryDto> toCategoryDtoList(List<Category> categories) {
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category : categories) {
            categoryDtos.add(toCategoryDto(category));
        }
        return categoryDtos;
    }

    public Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
