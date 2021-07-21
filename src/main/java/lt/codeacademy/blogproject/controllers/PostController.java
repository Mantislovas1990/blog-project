package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(value="/title")
    public Optional<Post> getByTitle(String title) {
        return postService.getPostByTitle(title);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping(value = "/create")
    public String addNewPost(Post post, Model model) {
      
       return "posts/create";
    }

    @GetMapping(value = "/{id}/delete")
    public void deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
    }

    @PutMapping(value = "/{id}/edit")
    public void updatePost(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String body
    ) {
        postService.updatePost(id, title, body);
    }

}
