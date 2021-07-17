package lt.codeacademy.blogproject.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide your User Name")
    @Column(name = "user_name")
    private String userName;

    @NotBlank(message = "Please provide your password")
    @Size(min = 4,  message = "Your password must have at least 4 characters")
    @Column(name = "password")
    private String password;

    @NotBlank(message = "Please provide your email")
    @Column(name = "email")
    @Email
    private String email;

    @NotBlank(message = "Please provide your full Name")
    @Column(name = "full_name")
    private String fullName;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String userPhoto;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(String userName, String password, String email, String fullName) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.fullName = fullName;

    }
}
