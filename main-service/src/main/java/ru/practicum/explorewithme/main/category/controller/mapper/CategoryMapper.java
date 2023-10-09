package ru.practicum.explorewithme.main.category.controller.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.main.category.model.Category;

@UtilityClass
public class CategoryMapper {

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
            .name(newCategoryDto.getName())
            .build();
    }

    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
            .id(category.getId())
            .name(category.getName())
            .build();
    }
}