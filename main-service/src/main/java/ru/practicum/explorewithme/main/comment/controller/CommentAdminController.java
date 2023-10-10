package ru.practicum.explorewithme.main.comment.controller;

import java.util.Collection;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.comment.dto.CommentDto;
import ru.practicum.explorewithme.main.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/comments")
public class CommentAdminController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentById(@PathVariable final Long commentId) {
        return commentService.getCommentForAdmin(commentId);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CommentDto> getUserComments(@PathVariable final Long userId,
        @RequestParam(value = "from", required = false, defaultValue = "0")
        @PositiveOrZero(message = "Значение 'from' должно быть положительным") final Integer from,
        @RequestParam(value = "size", required = false, defaultValue = "10")
        @Positive(message = "Значение 'size' должно быть положительным") final Integer size) {
        return commentService.getAllUserCommentsForAdmin(userId, from, size);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAdmin(@PathVariable final Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
    }
}