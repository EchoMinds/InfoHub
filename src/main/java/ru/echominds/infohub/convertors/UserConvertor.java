package ru.echominds.infohub.convertors;

import org.springframework.stereotype.Component;
import ru.echominds.infohub.domain.Role;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.UserDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserConvertor {

    public UserDTO convertUserToUserDTO(User userFound) {
        return new UserDTO(
                userFound.getName(),
                userFound.getEmail(),
                userFound.getAvatar(),
                userFound.getRoles().stream().map(Enum::name).collect(Collectors.toSet())
        );
    }

    public User convertUserDTOToUser(UserDTO userDTO) {
        return new User(
                userDTO.name(),
                userDTO.avatar(),
                userDTO.email(),
                new ArrayList<>(),
                userDTO.roles().stream().map(Role::valueOf).collect(Collectors.toSet())
        );
    }
}
