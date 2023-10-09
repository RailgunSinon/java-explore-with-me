package ru.practicum.explorewithme.main.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}