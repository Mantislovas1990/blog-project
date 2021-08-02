package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.dto.CommentDto;
import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PreAuthorize("(hasRole('ADMIN') or principal.id == #user.id)")
    @PostMapping("/posts/{postId}/comments/create")
    public String saveComment(
            @PathVariable("postId") Post post,
            @Valid Comment comment,
            @AuthenticationPrincipal User user,
            BindingResult bindingResult,
            RedirectAttributes attributes
    ) {

        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("commentCreateErrorMessage", "Failed to create new comment, please try again");
            return "redirect:/posts/" + post.getId() + "/view";
        }

        attributes.addFlashAttribute("commentCreateSuccessMessage", "Comment added successfully");
        commentService.saveComment(comment, post, user);
        return "redirect:/posts/" + post.getId() + "/view";
    }

//        commentService.saveComment(comment, post, user);
//        return "redirect:/posts/" + post.getId() + "/view";
//    }

    @PreAuthorize("(hasRole('ADMIN') or principal.id == #comment.user.id) and #comment.post.id == #post.id")
    @GetMapping("/posts/{postId}/comments/{id}/delete")
    public String deleteComment(
            @PathVariable("postId") Post post,
            @PathVariable("id") Comment comment
    ) {
        commentService.deleteComment(comment);
        return "redirect:/posts/" + post.getId() + "/view";
    }

    @PreAuthorize("hasRole('ADMIN') or principal.id == #comment.user.id")
    @GetMapping("/posts/{postId}/comments/{id}/edit")
    public String editComment(@PathVariable("postId") Post post,
                              @PathVariable("id") Comment comment,
                              Model model) {
        model.addAttribute("editComment", comment);
        return "comments/edit";
    }

    @PreAuthorize("hasRole('ADMIN') or principal.id == #comment.user.id")
    @PostMapping("/posts/{postId}/comments/{id}/edit")
    public String editComment(@PathVariable("id") Comment comment,
                              @PathVariable("postId") Post post,
                              @Valid CommentDto commentDto,
                              BindingResult bindingResult,
                              RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            attributes.addFlashAttribute("commentEditErrorMessage", "Failed to edit post, please try again");
            return "redirect:/posts/" + post.getId() + "/view";
        }

        attributes.addFlashAttribute("commentEditSuccessMessage", "Post updated successfully");
        commentService.updatedComment(comment, commentDto.getCommentBody());
        return "redirect:/posts/" + post.getId() + "/view";
    }

//        commentService.updatedComment(comment, commentDto.getCommentBody());
//        return "redirect:/posts/" + post.getId() + "/view";
//    }

}
