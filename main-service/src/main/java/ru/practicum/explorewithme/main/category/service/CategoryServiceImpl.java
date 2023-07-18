package ru.practicum.explorewithme.main.category.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.category.storage.CategoryStorage;
import ru.practicum.explorewithme.main.event.storage.EventStorage;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryStorage categoryStorage;
    private final EventStorage eventStorage;

    @Override
    @Transactional
    public Category addCategory(Category category)
        throws ConflictException, InvalidParameterException {
        log.info("Call CategoryServiceImpl create with : category: " + category.getName());
        if (category.getName().isBlank() || category.getName().isEmpty()) {
            throw new InvalidParameterException("Category name is empty.");
        }
        if (categoryStorage.existsCategoryByName(category.getName())) {
            throw new ConflictException("Category already exists: " + category.getName());
        }
        return categoryStorage.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories(Integer from, Integer size) {
        log.info("Call CategoryServiceImpl getAllWithPagination with : from: {}, size: {}", from,
            size);
        return categoryStorage.getAllWithPagination(from, size);
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategory(Long categoryId) throws NotFoundException {
        log.info("Call CategoryServiceImpl getCategory with : category: " + categoryId);
        Optional<Category> category = categoryStorage.findById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException("There is not Category with id: " + categoryId);
        }
        return category.get();
    }

    @Override
    @Transactional
    public Category updateCategory(Long categoryId, Category category)
        throws NotFoundException, ConflictException {
        log.info("Call CategoryServiceImpl updateCategory with  : category: " + category.getName());
        Optional<Category> categoryGotten = categoryStorage.findById(categoryId);
        if (categoryGotten.isEmpty()) {
            throw new NotFoundException("There is not Category with id: " + categoryId);
        }
        if (categoryStorage.existsCategoryByName(category.getName()) && !categoryGotten.get()
            .getName().equals(category.getName())) {
            throw new ConflictException("Category already exists: " + category.getName());
        }
        categoryGotten.get().setName(category.getName());
        return categoryStorage.save(categoryGotten.get());
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) throws NotFoundException, ConflictException {
        log.info("Call#CategoryServiceImpl#delete# : CategoryId: " + categoryId);
        Optional<Category> category = categoryStorage.findById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException("There is not Category with id: " + categoryId);
        }
        if (eventStorage.findAllByCategoryId(categoryId).isEmpty()) {
            categoryStorage.deleteById(categoryId);
        } else {
            throw new ConflictException("Can't remove Category with id: {}" + categoryId);
        }
    }
}
