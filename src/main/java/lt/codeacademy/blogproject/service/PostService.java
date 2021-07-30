package lt.codeacademy.blogproject.service;


import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.repositories.PostRepository;
import lt.codeacademy.blogproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostService {


    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public Page<Post> getPostsPaginated(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post savePost(Post post) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        post.setUser(userRepository.getUserByUsername(authentication.getName()));
        return postRepository.save(post);
    }

    public Post updatedPost(Post post, User user) {
        Post post1 = getPostById(post.getId());

        if (post1 != null) {
            post.setCreatedAt(post1.getCreatedAt());
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            post.setUser(user);
            return postRepository.save(post);
        }
        return null;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostByTitle(String title) {
        return postRepository.findPostByTitle(title);
    }

    public Post getPostById(Long id) {
        return postRepository.getById(id);
    }

    public void deletePost(Post post){
        postRepository.delete(post);
    }
}
