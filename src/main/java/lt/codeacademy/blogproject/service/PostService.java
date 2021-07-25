package lt.codeacademy.blogproject.service;


import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.repositories.PostRepository;
import lt.codeacademy.blogproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostByTitle(String title) {
        return postRepository.findPostByTitle(title);
    }

    public Post getPostById(Long id) {
        return postRepository.getById(id);
    }

    public void deletePostById(Long postId) {
        boolean exists = postRepository.existsById(postId);
        if (!exists) {
            throw new IllegalStateException(
                    "Post with id " + postId + " does not exist");
        }
        postRepository.deleteById(postId);
    }

    @Transactional
    public void updatePost(Long id, String title, String body) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Post with id " + id + " does not exists"));

        if (title != null && title.length() > 0 && !Objects.equals(post.getTitle(), title)) {
            post.setTitle(title);
        }

        if (body != null && body.length() > 0 && !Objects.equals(post.getBody(), body)) {
            post.setBody(body);
        }
    }
}
