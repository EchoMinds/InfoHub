package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.UserConvertor;
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
        User userFound = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userConvertor.convertUserToUserDTO(userFound);
    }

    public List<UserDTO> getAllUsers(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).stream().map(userConvertor::convertUserToUserDTO)
                .collect(Collectors.toList());
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

    public String getCurrentUsername(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        // ГУГЛ такое возвращает
        if (principal instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) principal;
            return oauth2User.getAttribute("name");
        } else if (principal instanceof UserDetails) { // по сути юзердетейлс должны другие вернуть
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        } else { // анонимус
            return "anonymous";
        }
    }
}
