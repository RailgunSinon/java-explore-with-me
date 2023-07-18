package ru.practicum.explorewithme.main.category.controller;

import java.util.List;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.category.controller.mapper.CategoryMapper;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.category.service.CategoryServiceImpl;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryPublicController {

    private final CategoryServiceImpl categoryService;
    private final CategoryMapper mapper;

    @GetMapping
    public List<CategoryDto> getAllCategoriesWithPagination(
        @RequestParam(defaultValue = "10") @Positive Integer size,
        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from) {
        log.info(
            "Call CategoryPublicController getAllCategoriesWithPagination with size {} from {} ",
            size, from);
        return mapper.toCategoryDtoList(categoryService.getAllCategories(size, from));
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) throws NotFoundException {
        log.info("Call  CategoryPublicController with categoryId {}", catId);
        return mapper.toCategoryDto(categoryService.getCategory(catId));
    }
}
