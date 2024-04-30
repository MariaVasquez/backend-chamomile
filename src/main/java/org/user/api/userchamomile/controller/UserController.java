package org.user.api.userchamomile.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.user.api.userchamomile.dto.GenericResponseDto;
import org.user.api.userchamomile.entities.User;
import org.user.api.userchamomile.services.UserService;
import org.user.api.userchamomile.util.ResponseCode;
import org.user.api.userchamomile.util.Util;

import java.util.List;
import java.util.Objects;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/by-username")
    public GenericResponseDto<User> getUser(@RequestParam String username) {
        return new GenericResponseDto<>(ResponseCode.LCO001, ResponseCode.LCO001.getHtmlMessage(),service.findByUsername(username));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public GenericResponseDto<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            logger.error("Validation error: {}", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
            return Util.validation(result);
        }
        return new GenericResponseDto<>(ResponseCode.LCO001, ResponseCode.LCO001.getHtmlMessage(), service.save(user));

    }

    @PostMapping("/register")
    public GenericResponseDto<?> register(@Valid @RequestBody User user, BindingResult result) {
        user.setAdmin(false);
        return create(user, result);
    }

}
