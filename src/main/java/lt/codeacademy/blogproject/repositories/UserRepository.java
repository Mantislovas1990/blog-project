package lt.codeacademy.blogproject.repositories;

import lt.codeacademy.blogproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    Optional<User> getUserByUsername(String username);

    @Query(value = "select *  FROM  users s  where s.email= :email", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    @Query(value = "select * FROM  users  where password= :password", nativeQuery = true)
    Optional<User> findUserByPassword(String password);

    @Query(value = "select * FROM  users  where user_name= :userName", nativeQuery = true)

    Optional<User> getUserByUserName(String userName);

}
