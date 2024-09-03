package ru.echominds.infohub.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.services.UserService;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // для поиска по id
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.FOUND);
    }

    // сделаем через Principal если надо будет
    @GetMapping
    public ResponseEntity<?> getUser() {
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {
        userService.create(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDto, @PathVariable Long id) {

        userService.updateUser(id,userDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
