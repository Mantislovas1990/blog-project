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
                                  BindingResult bindingResult
  ) throws  RoleNotFoundException {
        if (bindingResult.hasErrors()) {
            return "user/register";
        } else {
            userService.addNewUser(user);
            return "redirect:/";
        }
    }

}
