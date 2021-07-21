package lt.codeacademy.blogproject.service;

import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.exceptions.UserNotFoundException;
import lt.codeacademy.blogproject.repositories.RoleRepository;
import lt.codeacademy.blogproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder= passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(User user) throws RoleNotFoundException {
        Optional<User> userEmail = userRepository.findUserByEmail(user.getEmail());
        Optional<User> userName = userRepository.getUserByUserName(user.getUserName());
        if (userEmail.isPresent()) {
            throw new IllegalStateException("Email is taken");
        }
        if (userName.isPresent()) {
            throw new IllegalStateException("User Name is taken");
        }

        // need to add default role
        user.setRoles(Set.of(roleRepository.getRoleByName("USER").orElseThrow(() -> new RoleNotFoundException("USER"))));

        // need to encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
       return userRepository.save(user);
    }


    public void deleteUser(Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException(
                    "User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long id, String userName, String email, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "user with id " + id + " does not exists"));

        if (userName != null && userName.length() > 0 && !Objects.equals(user.getUserName(), userName)) {
            user.setUserName(userName);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> userEmail = userRepository.findUserByEmail(email);
            if (userEmail.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }

        if (password != null && password.length() > 0 && !Objects.equals(user.getPassword(), password)) {
            user.setPassword(password);
        }
    }

    private Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }
}


