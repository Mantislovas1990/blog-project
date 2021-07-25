package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.service.PostService;
import lt.codeacademy.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

//    @GetMapping
//    public String getCars(@PageableDefault(size = 5) Pageable pageable,
//                          @AuthenticationPrincipal Post post,
//                          Model model) {
//        model.addAttribute("postPage", postService.getPostsPaginated(pageable));
//        return "/index";
//    }

    @GetMapping(value="/title")
    public Optional<Post> getByTitle(String title) {
        return postService.getPostByTitle(title);
    }

    @GetMapping(value = "/index")
    public String getAllPosts(Model model) {
        model.addAttribute("postList", postService.getAllPosts() );
        return "/index";
    }

    @GetMapping
    public String home(HttpServletRequest request, HttpSession session) {
        return "redirect:/index";
    }

//    @GetMapping(value = "/posts/edit")
//    public String editPost(Model model){
//        model.addAttribute("post", new Post());
//        return "/posts/edit";
//    }
//
//
//    @PostMapping(value = "/posts/edit")
//    public String saveEditedPost(@Valid Post post){
//        postService.savePost(post);
//        return "redirect:/";
//    }


    @GetMapping(value = "/posts/create")
    public String createPost(Model model){
        model.addAttribute("post", new Post());
        return "/posts/create";
    }


    @PostMapping(value = "/posts/create")
    public String saveNewPost(@Valid Post post){
        postService.savePost(post);
        return "redirect:/";
    }

    @GetMapping("/posts/{id}/view")
    public String getPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
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
