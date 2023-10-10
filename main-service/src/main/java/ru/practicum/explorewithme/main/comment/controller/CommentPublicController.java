package ru.practicum.explorewithme.main.comment.controller;

import java.util.Collection;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.comment.dto.CommentDto;
import ru.practicum.explorewithme.main.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class CommentPublicController {

    private final CommentService commentService;

    @GetMapping("/events/{eventId}")
    public Collection<CommentDto> getEventComments(@PathVariable final Long eventId,
        @RequestParam(value = "from", required = false, defaultValue = "0")
        @PositiveOrZero(message = "Значение 'from' должно быть положительным") final Integer from,
        @RequestParam(value = "size", required = false, defaultValue = "10")
        @Positive(message = "Значение 'size' должно быть положительным") final Integer size) {

        return commentService.getEventComments(eventId, from, size);
    }
}