package ru.practicum.explorewithme.main.category.storage;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.main.category.model.Category;

public interface CategoryStorage extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories ORDER BY id ASC LIMIT ?1 OFFSET ?2", nativeQuery = true)
    List<Category> getAllWithPagination(Integer size, Integer from);

    boolean existsCategoryByName(String name);
}
