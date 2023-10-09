package ru.practicum.explorewithme.main.category.controller;

import java.util.Collection;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.category.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<CategoryDto> getAllCategories(
        @RequestParam(value = "from", required = false, defaultValue = "0")
        @PositiveOrZero(message = "Значение 'from' должно быть положительным") final Integer from,
        @RequestParam(value = "size", required = false, defaultValue = "10")
        @Positive(message = "Значение 'size' должно быть положительным") final Integer size) {
        return categoryService.getAllCategories(from, size);
    }

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}