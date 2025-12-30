package com.guibedan.ecommerce.controller;

import com.guibedan.ecommerce.controller.dto.CreateUserDto;
import com.guibedan.ecommerce.controller.dto.UserDetailDto;
import com.guibedan.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserControllerV1 {

    private final UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(userId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDto> getUserDetailById(@PathVariable("userId") UUID userId) {
        var user = userService.getUserDetailById(userId);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

}
