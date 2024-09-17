package ru.echominds.infohub.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.services.UserService;

import java.util.List;

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
    public List<UserDTO> getAllUsers(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                     @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return userService.getAllUsers(PageRequest.of(offset, limit));
    }

    @PostMapping
    public HttpStatus createUser(@RequestBody UserDTO user) {
        userService.create(user);

        return HttpStatus.CREATED;
    }

    @PatchMapping("/{id}")
    public HttpStatus updateUser(@RequestBody UserDTO userDto, @PathVariable Long id) {
        userService.updateUser(id, userDto);

        return HttpStatus.NO_CONTENT;
    }
}
