package ru.echominds.infohub.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.echominds.infohub.FakeDomain;
import ru.echominds.infohub.convertors.UserConvertor;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.UpdatedUserDTO;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.UserRepository;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageRequest;
import ru.echominds.infohub.security.SecurityAuthorizationManager;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConvertor userConvertor;

    @Mock
    private SecurityAuthorizationManager securityAuthorizationManager;

    @InjectMocks
    private UserService userService;

    User fakeUser;
    UserDTO fakeUserDTO;
    UpdatedUserDTO fakeUpdatedUserDTO;

    @BeforeEach
    void setUp() {
        fakeUser = FakeDomain.createFakeUser();
        fakeUserDTO = FakeDomain.createFakeUserDTO();
        fakeUpdatedUserDTO = FakeDomain.createFakeUpdatedUserDTO();
    }

    @Test
    void testGetUser_Anonymous() {
        when(securityAuthorizationManager.getCurrentUserOrAnonymous()).thenReturn("Anonymous");

        ResponseEntity<?> response = userService.getUser();

        assertEquals("Anonymous, please registration with your google account!", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUser_Authenticated() {
        when(securityAuthorizationManager.getCurrentUserOrAnonymous()).thenReturn(fakeUser);
        when(userConvertor.convertUserToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        ResponseEntity<?> response = userService.getUser();

        assertEquals(fakeUserDTO, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetUserById_Found() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(fakeUser));
        when(userConvertor.convertUserToUserDTO(fakeUser)).thenReturn(fakeUserDTO);

        UserDTO result = userService.getUser(userId);

        assertEquals(fakeUserDTO, result);
    }

    @Test
    void testGetUserById_NotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
    }

    @Test
    void testGetAllUsers() {
        List<User> mockUsers = List.of(fakeUser, fakeUser);

        when(userRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(mockUsers));
        when(userConvertor.convertUserToUserDTO(any(User.class))).thenReturn(fakeUserDTO);

        List<UserDTO> mockUserDTOs = List.of(fakeUserDTO, fakeUserDTO);
        List<UserDTO> result = userService.getAllUsers(PageRequest.of(0, 10));

        assertEquals(mockUserDTOs.size(), result.size());
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(fakeUser));

        userService.updateUser(userId, fakeUpdatedUserDTO);

        assertEquals("New Name", fakeUser.getName());
        assertEquals("new@example.com", fakeUser.getEmail());
        assertEquals("avatarUrl", fakeUser.getAvatar());

        verify(userRepository).save(fakeUser);
    }

    @Test
    void testUpdateCurrentUser() {
        when(securityAuthorizationManager.getCurrentUser()).thenReturn(fakeUser);

        userService.updateUser(fakeUpdatedUserDTO);

        assertEquals("New Name", fakeUser.getName());
        assertEquals("new@example.com", fakeUser.getEmail());
        assertEquals("avatarUrl", fakeUser.getAvatar());

        verify(userRepository).save(fakeUser);
    }
}


