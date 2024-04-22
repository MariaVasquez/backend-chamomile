package org.user.api.userchamomile.repositories;

import org.springframework.data.repository.CrudRepository;
import org.user.api.userchamomile.entities.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
