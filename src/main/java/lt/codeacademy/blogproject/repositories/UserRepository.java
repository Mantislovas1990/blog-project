package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

//    @Query(value = "select *  FROM  users s  where s.email= :email", nativeQuery = true)
    Optional<User> getUserByEmail(String email);

//    @Query(value = "select * FROM  users  where password= :password", nativeQuery = true)
    Optional<User> getUserByPassword(String password);

//    @Query(value = "select * FROM  users  where user_name= :userName", nativeQuery = true)
 User getUserByUsername(String userName);

//    Optional<User> getUserByUsername(String username);
}
