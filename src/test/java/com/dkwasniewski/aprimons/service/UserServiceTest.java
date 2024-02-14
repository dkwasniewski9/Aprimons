package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import util.Role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();

        userService.saveUser(user);

        verify(userRepository).save(user);
    }

    @Test
    public void testFindUserByUsername() {
        String username = "exampleUsername";
        User expectedUser = new User(username, "example@example.com", "abc", Role.USER);

        when(userRepository.findUserByUsername(username)).thenReturn(expectedUser);

        User actualUser = userService.findUserByUsername(username);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "exampleUsername";
        User userEntity = new User(username, "example@example.com", "abc", Role.USER);

        when(userRepository.findUserByUsername(username)).thenReturn(userEntity);

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
        assertEquals(userEntity.getRole(), Role.USER);
    }

    @Test
    public void testGetCurrentUser() {
        String username = "exampleUsername";
        User expectedUser = new User(username, "example@example.com", "abc", Role.USER);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(username);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findUserByUsername(username)).thenReturn(expectedUser);

        User actualUser = userService.getCurrentUser();

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testActivateUser() {
        User user = new User("exampleUsername", "example@example.com", "abc", Role.USER);

        userService.activateUser(user);

        assertTrue(user.isActive());
        verify(userRepository).save(user);
    }
}