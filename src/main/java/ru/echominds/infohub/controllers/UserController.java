package ru.echominds.infohub.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.services.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // получаем юзера через Секур
    @GetMapping
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getCurrentUsername(authentication);
    }

    // для поиска по id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(   @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return new ResponseEntity<>(userService.getAllUsers(PageRequest.of(offset, limit)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        userService.create(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto, @PathVariable Long id) {

        userService.updateUser(id,userDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Учел че сказал черны кит
    @Deprecated
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
