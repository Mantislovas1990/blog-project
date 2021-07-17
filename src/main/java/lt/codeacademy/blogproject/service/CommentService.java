package lt.codeacademy.blogproject.service;

import lt.codeacademy.blogproject.entities.Comment;
import lt.codeacademy.blogproject.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class CommentService {


    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void deleteCommentById(Long commentId) {
        boolean exists = commentRepository.existsById(commentId);
        if (!exists) {
            throw new IllegalStateException(
                    "Comment with id " + commentId + " does not exist");
        }
        commentRepository.deleteById(commentId);
    }

    public void addNewComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long id, String comment) {
        Comment commentId = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Comment with id " + id + " does not exists"));

        if (comment != null && comment.length() > 0 && !Objects.equals(commentId.getComment(), comment)) {
            commentId.setComment(comment);
        }
    }
}
