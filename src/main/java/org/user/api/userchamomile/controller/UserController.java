package org.user.api.userchamomile.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.user.api.userchamomile.dto.GenericResponseDto;
import org.user.api.userchamomile.entities.User;
import org.user.api.userchamomile.services.UserService;
import org.user.api.userchamomile.util.ResponseCode;
import org.user.api.userchamomile.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public GenericResponseDto<?> create(@Valid @RequestBody User user, BindingResult result) {
        try {
            if (result.hasFieldErrors()) {
                logger.error("Validation error: {}", Objects.requireNonNull(result.getFieldError()).getDefaultMessage());
                return Util.validation(result);
            }
            return new GenericResponseDto<>(ResponseCode.LCO001, ResponseCode.LCO001.getHtmlMessage(), service.save(user));
        }catch (AccessDeniedException e) {
            return new GenericResponseDto<>(ResponseCode.LCO000, ResponseCode.LCO000.getHtmlMessage(), service.save(user));
        }

    }

    @PostMapping("/register")
    public GenericResponseDto<?> register(@Valid @RequestBody User user, BindingResult result) {
        user.setAdmin(false);
        return create(user, result);
    }

}
