package ru.echominds.infohub.controllers.adminControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.echominds.infohub.dtos.UserDTO;
import ru.echominds.infohub.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                     @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return userService.getAllUsers(PageRequest.of(offset, limit));
    }

    @PatchMapping("/ban/{userId}")
    public HttpStatus banUser(@PathVariable("userId") Long userId) {
        userService.banUser(userId);

        return HttpStatus.OK;
    }

    @PatchMapping("/unban/{userId}")
    public HttpStatus unbanUser(@PathVariable("userId") Long userId) {
        userService.unbanUser(userId);

        return HttpStatus.OK;
    }
}
