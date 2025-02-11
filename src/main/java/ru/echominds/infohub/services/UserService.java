package ru.echominds.infohub.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.echominds.infohub.convertors.UserConvertor;
import ru.echominds.infohub.domain.AccountSuspension;
import ru.echominds.infohub.domain.User;
import ru.echominds.infohub.dtos.BanUserInformationDTO;
import ru.echominds.infohub.dtos.UpdatedUserDTO;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.exceptions.AccountNotBannedException;
import ru.echominds.infohub.exceptions.UserNotFoundException;
import ru.echominds.infohub.repositories.AccountSuspensionRepository;
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
    private final AccountSuspensionRepository accountSuspensionRepository;

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

    public void banUser(Long id, BanUserInformationDTO banUserInformationDTO) {
        securityAuthorizationManager.checkAdminRole();

        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        AccountSuspension accountSuspension = accountSuspensionRepository.findByUser(userFound)
                .orElse(new AccountSuspension());

        if (banUserInformationDTO.banTime().isEmpty()) {
            accountSuspension.setIsPermanentBan(Boolean.TRUE);
        } else {
            accountSuspension.setIsPermanentBan(Boolean.FALSE);
            accountSuspension.setBanTime(banUserInformationDTO.banTime().get());
        }

        accountSuspension.setReasonBan(banUserInformationDTO.reasonBan());
        accountSuspension.setUser(userFound);
        accountSuspension.setAdmin(securityAuthorizationManager.getCurrentUser());
        accountSuspension.setIsBanned(Boolean.TRUE);

        accountSuspensionRepository.save(accountSuspension);
    }

    public void unbanUser(Long id) {
        securityAuthorizationManager.checkAdminRole();

        User userFound = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        AccountSuspension accountSuspension = accountSuspensionRepository.findByUser(userFound)
                .orElseThrow(AccountNotBannedException::new);

        accountSuspensionRepository.delete(accountSuspension);
    }
}
