package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

 User getUserByUsername(String userName);

 User getUserByEmail(String email);

 User getUserByUsername(User user);
}
