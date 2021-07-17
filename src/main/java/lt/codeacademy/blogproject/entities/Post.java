package lt.codeacademy.blogproject.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "articles")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide title for post")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Please add text to you post")
    @Column(name = "body")
    private String body;

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String postImg;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Post() {
    }

    public Post(String title, String body,
                List<Comment> comments, User user) {
        this.title = title;
        this.body = body;
        this.comments = comments;
        this.user = user;

    }
}
