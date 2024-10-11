package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.UserConvertor;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.UpdatedUserDTO;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.UserRepository;
import ru.echominds.infohub.security.SecurityAuthorizationManager;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConvertor userConvertor;
    private final SecurityAuthorizationManager securityAuthorizationManager;

    public ResponseEntity<?> getUser() {
        Object currentUserOrAnonymous = securityAuthorizationManager.getCurrentUserOrAnonymous();

        if (currentUserOrAnonymous instanceof String) {
            return new ResponseEntity<>(
                    "Anonymous, please registration with your google account!", HttpStatus.OK);
        }

        User currentUser = (User) currentUserOrAnonymous;

        return new ResponseEntity<>(userConvertor.convertUserToUserDTO(currentUser), HttpStatus.OK);
    }

    public UserDTO getUser(Long id) {
        User userFound = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return userConvertor.convertUserToUserDTO(userFound);
    }

    public List<UserDTO> getAllUsers(PageRequest pageRequest) {
        securityAuthorizationManager.checkAdminRole();

        return userRepository.findAll(pageRequest).stream().map(userConvertor::convertUserToUserDTO)
                .collect(Collectors.toList());
    }

    public void updateUser(Long id, UpdatedUserDTO userDto) {
        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        updatedUserFields(userFound, userDto);
    }

    public void updateUser(UpdatedUserDTO userDto) {
        User currentUser = securityAuthorizationManager.getCurrentUser();

        updatedUserFields(currentUser, userDto);
    }

    private void updatedUserFields(User currentUser, UpdatedUserDTO userDto) {
        currentUser.setName(userDto.name() != null ? userDto.name() : currentUser.getName());
        currentUser.setEmail(userDto.email() != null ? userDto.email() : currentUser.getEmail());
        currentUser.setAvatar(userDto.avatar() != null ? userDto.avatar() : currentUser.getAvatar());

        userRepository.save(currentUser);
    }

    public void banUser(Long id) {
        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        securityAuthorizationManager.checkAdminRole();

        userFound.setIs_banned(Boolean.TRUE);

        userRepository.save(userFound);
    }

    public void unbanUser(Long id) {
        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        securityAuthorizationManager.checkAdminRole();

        userFound.setIs_banned(Boolean.FALSE);

        userRepository.save(userFound);
    }
}
