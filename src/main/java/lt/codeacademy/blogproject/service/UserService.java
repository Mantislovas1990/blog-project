package lt.codeacademy.blogproject.service;

import lt.codeacademy.blogproject.entities.Role;
import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.exceptions.UserNotFoundException;
import lt.codeacademy.blogproject.repositories.RoleRepository;
import lt.codeacademy.blogproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService implements UserDetailsService{


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addNewUser(User user) throws RoleNotFoundException {
//        Optional<User> userEmail = userRepository.getUserByEmail(user.getEmail());
//        Optional<User> userName = userRepository.getUserByUsername(user.getUsername());
//        if (userEmail.isPresent()) {
//            throw new IllegalStateException("Email is taken");
//        }
//        if (userName.isPresent()) {
//            throw new IllegalStateException("User Name is taken");
//        }

        // need to add default role
        user.setRoles(Set.of(roleRepository.getRoleByName("USER").orElseThrow(() -> new RoleNotFoundException("USER"))));
//        Set<Role> roles = new HashSet<>();
//        Role role = new Role();
//        role.setId(1L);
//        role.setName("USER");
//        roles.add(role);
//        user.setRoles(roles);
        // need to encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println();
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

        if (userName != null && userName.length() > 0 && !Objects.equals(user.getUsername(), userName)) {
            user.setUsername(userName);
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> userEmail = userRepository.getUserByEmail(email);
            if (userEmail.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            user.setEmail(email);
        }
            if (password != null && password.length() > 0 && !Objects.equals(user.getPassword(), password)) {
                user.setPassword(password);
            }
        }

//    private Optional<User> getUserById(Long id) {
//        return userRepository.findById(id);
//    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
    }

    }

