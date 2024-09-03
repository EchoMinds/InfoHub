package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.UserConvertor;
import ru.echominds.infohub.domain.Role;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserConvertor userConvertor;

    public UserDTO getUser(Long id) {
        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userConvertor.convertUserToUserDTO(userFound);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userConvertor::convertUserToUserDTO).collect(Collectors.toList());
    }

    public void create(UserDTO createdUser) {
        userRepository.save(
                userConvertor.convertUserDTOToUser(createdUser)
        );
    }

    public void updateUser(Long id, UserDTO userDto) {
        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userFound.setName(userDto.name() != null ? userDto.name() : userFound.getName());
        userFound.setEmail(userDto.email() != null ? userDto.email() : userFound.getEmail());
        userFound.setAvatar(userDto.avatar() != null ? userDto.avatar() : userFound.getAvatar());

        userRepository.save(userFound);
    }

    public void deleteUser(Long id) {
        // check account
        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        userRepository.delete(userFound);
    }
}
