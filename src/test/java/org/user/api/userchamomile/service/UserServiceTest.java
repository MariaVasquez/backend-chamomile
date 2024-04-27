package org.user.api.userchamomile.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.user.api.userchamomile.entities.User;
import org.user.api.userchamomile.repositories.RoleRepository;
import org.user.api.userchamomile.repositories.UserRepository;
import org.user.api.userchamomile.services.impl.UserServiceImpl;
import org.user.api.userchamomile.util.DataMock;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImp;

    @Test
    @DisplayName("should success list customer")
    void shouldSuccess_ListCustomer(){
        when(userRepository.findAll()).thenReturn(DataMock.newUserAdmin());

        List<User> userList = userServiceImp.findAll();

        Assertions.assertEquals(userList.size(), 1);

    }
}
