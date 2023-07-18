package ru.practicum.explorewithme.main.user.storage;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.user.model.User;

public interface UserStorage extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    List<User> findAllByIdIn(List<Long> ids, Pageable pageable);
}
