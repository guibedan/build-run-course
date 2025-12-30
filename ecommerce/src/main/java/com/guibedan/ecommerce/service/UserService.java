package com.guibedan.ecommerce.service;

import com.guibedan.ecommerce.controller.dto.CreateUserDto;
import com.guibedan.ecommerce.controller.dto.UserDetailDto;
import com.guibedan.ecommerce.entity.BillingAddress;
import com.guibedan.ecommerce.entity.User;
import com.guibedan.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID createUser(CreateUserDto createUserDto) {
        BillingAddress billingAddress = BillingAddress.builder()
                .address(createUserDto.address())
                .number(createUserDto.number())
                .complement(createUserDto.complement())
                .build();

        User user = User.builder()
                .fullName(createUserDto.fullName())
                .billingAddress(billingAddress)
                .build();

        return userRepository.save(user).getId();
    }

    public UserDetailDto getUserDetailById(UUID userId) {
        return UserDetailDto.fromEntity(getUserById(userId));
    }

    public void deleteUserById(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(userId);
    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }

}
