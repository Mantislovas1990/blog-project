package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.service.CommentService;
import lt.codeacademy.blogproject.service.PostService;
import lt.codeacademy.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping
public class PostController {

    @Autowired
    private PostService postService;


    private CommentService commentService;

    @Autowired
    private UserService userService;

    public PostController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping
    public String home(HttpServletRequest request, HttpSession session) {
        return "redirect:/index";
    }

    @GetMapping(value = "/index")
    public String getAllPosts(Model model) {
        model.addAttribute("postList", postService.getAllPosts());
        return "/index";
    }

    @GetMapping(value = "/posts/create")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "/posts/create";
    }

    @PostMapping(value = "/posts/create")
    public String saveNewPost(@Valid Post post) {
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/posts/{id}/view")
    public String getPost(@PathVariable("id") Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        model.addAttribute("createComment", new Comment());
        model.addAttribute("commentList", commentService.getCommentsByPostId(id));
        return "posts/view";
    }






//    @GetMapping(value = "/posts/{id}/delete")
//    public String deletePost(@PathVariable Long id) {
//        postService.deletePostById(id);
//        return "/posts";
//    }
//
//    @PutMapping(value = "/{id}/edit")
//    public String updatePost(
//            @PathVariable Long id,
//            @RequestParam(required = false) String title,
//            @RequestParam(required = false) String body
//    ) {
//        postService.updatePost(id, title, body);
//        return "redirect:/";

//    }


}
