package lt.codeacademy.blogproject.controllers;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.blogproject.entities.User;
import lt.codeacademy.blogproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
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
                                  RedirectAttributes redirectAttributes,
     HttpServletRequest request) throws ServletException, RoleNotFoundException {
        if (bindingResult.hasErrors()) {
            return "user/register";
        } else {
            userService.addNewUser(user);
            redirectAttributes.addFlashAttribute("errors", "Failed to create person.");
            log.info("User {} has registered successfully", user);
            request.login(user.getUsername(), user.getPassword());
            return "redirect:/";
        }
    }

    @GetMapping(value = "/{id}/delete")
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
