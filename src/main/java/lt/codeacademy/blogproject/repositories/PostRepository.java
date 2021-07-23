package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query(value = "select *  FROM  articles s  where s.title= :title", nativeQuery = true)
    Optional<Post> findPostByTitle(String title);

//    @Query(value = "select *  FROM  articles s  where s.f= :title", nativeQuery = true)
//    Optional<Post> findPostByAuthorName(String authorName);

    Page<Post> getPostById(Pageable pageable, Long id);
}
