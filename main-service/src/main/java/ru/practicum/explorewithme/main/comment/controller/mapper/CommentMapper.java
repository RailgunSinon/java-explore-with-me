package ru.practicum.explorewithme.main.comment.controller.mapper;

import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;
import ru.practicum.explorewithme.main.comment.dto.CommentDto;
import ru.practicum.explorewithme.main.comment.dto.NewCommentDto;
import ru.practicum.explorewithme.main.comment.model.Comment;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.user.model.User;

@UtilityClass
public class CommentMapper {

    public Comment toComment(NewCommentDto newCommentDto, User commentator, Event event) {
        return Comment.builder()
            .text(newCommentDto.getText())
            .commentator(commentator)
            .event(event)
            .created(LocalDateTime.now())
            .updated(LocalDateTime.now())
            .build();
    }

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
            .id(comment.getId())
            .text(comment.getText())
            .commentatorId(comment.getCommentator().getId())
            .eventId(comment.getEvent().getId())
            .created(comment.getCreated())
            .updated(comment.getUpdated())
            .build();
    }
}