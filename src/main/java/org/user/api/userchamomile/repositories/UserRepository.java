package org.user.api.userchamomile.repositories;

import org.springframework.data.repository.CrudRepository;
import org.user.api.userchamomile.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsernameEquals(String username);

    Optional<User> findByUsername(String username);
}
