package lt.codeacademy.blogproject.service;

import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.entities.Post;
import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.repositories.CommentRepository;
import lt.codeacademy.blogproject.repositories.PostRepository;
import lt.codeacademy.blogproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Comment saveComment(Comment  comment, Post post, User user) {
        comment.setUser(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }


    public List<Comment> getCommentsByPostId(Long id){
      return  commentRepository.getAllByPostId(id);
    }

    public Comment getCommentById(Long id){
        return commentRepository.getById(id);
    }

    public void deleteComment(Comment comment){
        commentRepository.delete(comment);
    }

    public Comment updatedComment(Post post, User user, Comment comment) {
        Comment comment1 = getCommentById(comment.getId());

        if (comment1 != null) {
            comment.setCreatedAt(comment1.getCreatedAt());
            comment.setUser(user);
            comment.setPost(post);
            return commentRepository.save(comment);
        }
        return null;
    }
}
