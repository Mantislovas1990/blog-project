package lt.codeacademy.blogproject.controllers;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.util.List;


@Controller
@Slf4j
@RequestMapping("/users")
public class UsersController {


    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/register")
    public String registerNewUser(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @GetMapping("/login")
    public String login(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            model.addAttribute("user", new User());
            return "user/login";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/register")
    public String registerNewUser(@Valid User user,
                                  BindingResult bindingResult,
                                  RedirectAttributes attributes) throws RoleNotFoundException {

        User userName = this.userService.getUserByUsername(user.getUsername());
        User email = this.userService.getUserByEmail(user.getEmail());

        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        if (userName != null) {
            bindingResult.rejectValue("username", "error.user", "User name already exists");
            return "user/register";
        }
        if (email != null) {
            bindingResult.rejectValue("email", "error.email", "Email already exists");
            return "user/register";
        }
        userService.addNewUser(user);
        return "user/login";
    }
}


