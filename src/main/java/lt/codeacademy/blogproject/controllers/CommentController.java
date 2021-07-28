package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments/create")
    public String saveComment(
            @PathVariable("postId") Post post,
            @Valid Comment comment,
            @AuthenticationPrincipal User user
    ) {
        commentService.saveComment(comment, post, user);
        return "redirect:/posts/" + post.getId() + "/view";
    }

//    @PreAuthorize("(hasRole('ADMIN') or principal.id == #comment.user.id) and #comment.post.id == #post.id")
//    @GetMapping("/posts/{postId}/comments/{id}/delete")
//    public String deleteComment(
//            @PathVariable("postId") Post post,
//            @PathVariable("id") Comment comment
//    ) {
//        commentService.delete(comment, post);
//        return "redirect:/";
//    }

}
