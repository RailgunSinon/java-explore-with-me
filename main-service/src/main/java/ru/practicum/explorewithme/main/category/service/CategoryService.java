package ru.practicum.explorewithme.main.category.service;

import java.security.InvalidParameterException;
import java.util.List;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

public interface CategoryService {

    Category addCategory(Category category) throws ConflictException, InvalidParameterException;

    List<Category> getAllCategories(Integer from, Integer size);

    Category getCategory(Long categoryId) throws NotFoundException;

    Category updateCategory(Long categoryId, Category category)
        throws NotFoundException, ConflictException;

    void deleteCategory(Long categoryId) throws NotFoundException, ConflictException;
}
