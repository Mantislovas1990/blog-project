package lt.codeacademy.blogproject.service;

import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.repositories.CommentRepository;
import lt.codeacademy.blogproject.repositories.PostRepository;
import lt.codeacademy.blogproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Comment saveComment(Comment  comment, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        comment.setUser(userRepository.getUserByUsername(authentication.getName()));
        comment.setPost(postRepository.getById(id));
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id){
        return commentRepository.getById(id);
    }

//    public void deleteCommentById(Long commentId) {
//        boolean exists = commentRepository.existsById(commentId);
//        if (!exists) {
//            throw new IllegalStateException(
//                    "Comment with id " + commentId + " does not exist");
//        }
//        commentRepository.deleteById(commentId);
//    }
//
//
//    @Transactional
//    public void updateComment(Long id, String comment) {
//        Comment commentId = commentRepository.findById(id)
//                .orElseThrow(() -> new IllegalStateException(
//                        "Comment with id " + id + " does not exists"));
//
//        if (comment != null && comment.length() > 0 && !Objects.equals(commentId.getComment(), comment)) {
//            commentId.setComment(comment);
//        }
//    }
}
