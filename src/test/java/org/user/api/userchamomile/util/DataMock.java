package org.user.api.userchamomile.util;

import org.user.api.userchamomile.entities.Role;
import org.user.api.userchamomile.entities.User;

import java.util.List;

public class DataMock {

    public static Role newRoleAdmin(){
        return Role.builder()
                .id(1L)
                .name("ROLE_ADMIN").build();
    }

    public static Role newRoleUser(){
        return Role.builder()
                .id(1L)
                .name("ROLE_ADMIN").build();
    }

    public static List<User> newUserAdmin(){
        return List.of(User.builder()
                .username("Maria")
                .password("123")
                .roles(List.of(newRoleUser(), newRoleAdmin()))
                .admin(true)
                .enabled(true).build());
    }
}
