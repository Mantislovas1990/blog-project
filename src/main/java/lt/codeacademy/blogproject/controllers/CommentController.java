package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

//    @GetMapping
//    public List<Comment> getCommentsByUserFullName() {
//        return commentService.
//    }

    @PostMapping(value = "/add")
    public void addNewComment(Comment comment) {
        commentService.addNewComment(comment);
    }

    @GetMapping(value = "/{id}/delete")
    public void deleteCommentById(@PathVariable Long id) {
        commentService.deleteCommentById(id);
    }

    @PutMapping(value = "/{id}/update")
    public void updateComment(
            @PathVariable Long id,
            @RequestParam(required = false) String comment
    ) {
        commentService.updateComment(id, comment);
    }
}
