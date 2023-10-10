package ru.practicum.explorewithme.main.comment.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.comment.dto.CommentDto;
import ru.practicum.explorewithme.main.comment.dto.CommentUpdateDto;
import ru.practicum.explorewithme.main.comment.dto.NewCommentDto;
import ru.practicum.explorewithme.main.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class CommentPrivateController {

    private final CommentService commentService;

    @PostMapping("/{userId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addNewComment(@PathVariable final Long userId,
        @Valid @RequestBody NewCommentDto newCommentDto) {
        return commentService.addNewComment(userId, newCommentDto);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateComment(@PathVariable final Long userId,
        @PathVariable final Long commentId,
        @Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        return commentService.updateComment(userId, commentId, commentUpdateDto);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAuthor(@PathVariable final Long userId,
        @PathVariable final Long commentId) {
        commentService.deleteCommentByUser(userId, commentId);
    }
}
