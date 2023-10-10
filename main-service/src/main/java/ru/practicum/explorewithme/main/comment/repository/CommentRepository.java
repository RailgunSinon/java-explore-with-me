package ru.practicum.explorewithme.main.comment.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.comment.model.Comment;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.user.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByCommentator(User commentator, Pageable pageable);

    List<Comment> findAllByEvent(Event event, Pageable pageable);

    List<Comment> findAllByEventIn(List<Event> events);
}