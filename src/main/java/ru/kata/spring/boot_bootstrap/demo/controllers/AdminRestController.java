package ru.kata.spring.boot_bootstrap.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_bootstrap.demo.models.User;
import ru.kata.spring.boot_bootstrap.demo.services.UserService;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private static final Logger logger = LoggerFactory.logger(AdminRestController.class);
    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.listUsers();
        logger.info("The users have been successfully found");
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User newUser) {
        String role = newUser.getRoles().toString().contains("ROLE_ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";
        userService.create(newUser, role);
        logger.info("The user has been successfully created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/read")
    public ResponseEntity<User> getUserById(@RequestParam("id") Long id) {
        User userById = userService.getUserById(id);
        logger.info("The user has been successfully found");
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit")
    public ResponseEntity<HttpStatus> editUser(@Valid @RequestBody User editedUser) {
        String role = editedUser.getRoles().toString().contains("ROLE_ADMIN") ? "ROLE_ADMIN" : "ROLE_USER";
        userService.update(editedUser, role);
        logger.info("The user has been successfully updated");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser(@Valid @RequestBody User deletedUser) {
        userService.delete(deletedUser);
        logger.info("The user was successfully deleted");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
