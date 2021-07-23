package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.service.PostService;
import lt.codeacademy.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

//    @GetMapping
//    public String getCars(@PageableDefault(size = 5) Pageable pageable,
//                          @RequestParam(required = false) Long id,
//                          @AuthenticationPrincipal Post post,
//                          Model model,
//                          HttpSession session,
//                          HttpServletRequest request,
//                          HttpServletResponse response) {
//        model.addAttribute("postPage", postService.getPostsPaginated(pageable, id));
//        return "/index";
//    }

    @GetMapping(value="/title")
    public Optional<Post> getByTitle(String title) {
        return postService.getPostByTitle(title);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }


    @GetMapping(value = "/create")
    public String createPost(@Valid BindingResult bindingResult, Model model, RedirectAttributes attributes){
        model.addAttribute("post", new Post());
        attributes.addFlashAttribute("successMsg", "Your car has been successfully created!");
        return "/posts/create";
    }


    //TODO FIND HOW TO BIND CURRENT USER TO CURRENT POST
//    @PostMapping(value = "/save")
//    public String SaveNewPost(Post post){
//
//    }

    @GetMapping(value = "/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return "/posts";
    }

    @PutMapping(value = "/{id}/edit")
    public String updatePost(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String body
    ) {
        postService.updatePost(id, title, body);
        return "redirect:/";
    }

}
