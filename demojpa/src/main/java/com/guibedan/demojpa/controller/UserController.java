package com.guibedan.demojpa.controller;

import com.guibedan.demojpa.controller.dto.CreateUserDto;
import com.guibedan.demojpa.controller.dto.UpdateUserDto;
import com.guibedan.demojpa.controller.dto.UserDetailDto;
import com.guibedan.demojpa.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        long userId = userService.createUser(createUserDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDto> getUser(@PathVariable long userId) {
        var user = userService.getUser(userId);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<Page<UserDetailDto>> listUsers(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy,
                                                         @RequestParam(name = "username", required = false) String username,
                                                         @RequestParam(name = "age", required = false) Integer age) {
        var users = userService.listUsers(page, pageSize, orderBy, username, age);
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateUserDto updateUserDto, @PathVariable long userId) {
        userService.updateUser(updateUserDto, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
