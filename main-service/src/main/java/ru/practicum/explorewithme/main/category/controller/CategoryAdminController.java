package ru.practicum.explorewithme.main.category.controller;

import java.security.InvalidParameterException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.category.controller.mapper.CategoryMapper;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.category.service.CategoryService;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class CategoryAdminController {

    private final CategoryService categoryService;
    private final CategoryMapper mapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto addCategory(@RequestBody @Valid CategoryDto categoryDto)
        throws ConflictException, InvalidParameterException {
        log.info("Call CategoryAdminController addCategory with category: {}", categoryDto);
        return mapper.toCategoryDto(categoryService.addCategory(mapper.toCategory(categoryDto)));
    }

    @PatchMapping("/{id}")
    public CategoryDto updateCategory(@Positive @PathVariable Long id,
        @RequestBody @Valid CategoryDto categoryDto) throws ConflictException, NotFoundException {
        log.info("Call CategoryAdminController updateCategory with categoryId: {}", id);
        return mapper
            .toCategoryDto(categoryService.updateCategory(id, mapper.toCategory(categoryDto)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCategory(@Positive @PathVariable Long id)
        throws NotFoundException, ConflictException {
        log.info("Call CategoryAdminController deleteCategory with categoryId: {}", id);
        categoryService.deleteCategory(id);
    }

}
