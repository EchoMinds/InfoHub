package ru.echominds.infohub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.echominds.infohub.FakeDomain;
import ru.echominds.infohub.controllers.UserController;
import ru.echominds.infohub.convertors.UserConvertor;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConvertor userConvertor;

    private User fakeUser;

    @BeforeEach
    void init() {
        fakeUser = FakeDomain.createFakeUser();

    }

    @Test
    void test_getUserById_shouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(fakeUser));
        when(userConvertor.convertUserToUserDTO(any(User.class))).thenReturn(FakeDomain.createFakeUserDTO());

        assertEquals(FakeDomain.createFakeUserDTO(), userService.getUser(1L));
    }

    @Test
    void test_getUserById_shouldReturnError() {
        assertThrows(UserNotFoundException.class, () -> userService.getUser(2L));

    }
}