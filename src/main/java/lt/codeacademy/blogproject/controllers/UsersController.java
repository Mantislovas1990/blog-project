package lt.codeacademy.blogproject.controllers;

import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/register")
    public void registerNewUser(User user) {
        userService.addNewUser(user);
    }

    @GetMapping(value="/{id}/delete")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping(value = "/{id}/update")
    public void updateUser(
            @PathVariable Long id,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {

        userService.updateUser(id, userName, email, password);
    }

}
