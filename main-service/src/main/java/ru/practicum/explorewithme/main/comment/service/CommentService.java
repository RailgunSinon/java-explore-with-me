package ru.practicum.explorewithme.main.comment.service;

import java.util.Collection;
import ru.practicum.explorewithme.main.comment.dto.CommentDto;
import ru.practicum.explorewithme.main.comment.dto.CommentUpdateDto;
import ru.practicum.explorewithme.main.comment.dto.NewCommentDto;

public interface CommentService {

    CommentDto addNewComment(Long userId, NewCommentDto newCommentDto);

    CommentDto updateComment(Long userId, Long commentId, CommentUpdateDto commentUpdateDto);

    void deleteCommentByUser(Long userId, Long commentId);

    void deleteCommentByAdmin(Long commentId);

    CommentDto getCommentForAdmin(Long commentId);

    Collection<CommentDto> getAllUserCommentsForAdmin(Long userId, Integer from, Integer size);

    Collection<CommentDto> getEventComments(Long eventId, Integer from, Integer size);
}
