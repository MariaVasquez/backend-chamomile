package org.user.api.userchamomile.services;

import org.user.api.userchamomile.entities.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);

    User findByUsername(String username);
}
