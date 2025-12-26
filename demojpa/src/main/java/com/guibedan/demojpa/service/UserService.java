package com.guibedan.demojpa.service;

import com.guibedan.demojpa.controller.dto.CreateUserDto;
import com.guibedan.demojpa.controller.dto.UpdateUserDto;
import com.guibedan.demojpa.controller.dto.UserDetailDto;
import com.guibedan.demojpa.entity.UserEntity;
import com.guibedan.demojpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long createUser(CreateUserDto createUserDto) {
        var user = new UserEntity();
        user.setUsername(createUserDto.username());
        user.setAge(createUserDto.age());

        return userRepository.save(user).getUserId();
    }

    public void updateUser(UpdateUserDto updateUserDto, long userId) {
        var user = getUserEntity(userId);
        user.setUsername(updateUserDto.username());
        user.setAge(updateUserDto.age());

        userRepository.save(user);
    }

    public void deleteUser(long userId) {
        var user = getUserEntity(userId);
        userRepository.delete(user);
    }

    public UserDetailDto getUser(long userId) {
        var user = getUserEntity(userId);
        return new UserDetailDto(user.getUsername(), user.getAge(), user.getCreatedAt());
    }

    public Page<UserDetailDto> listUsers(Integer page, Integer pageSize, String orderBy, String username, Integer age) {
        var pageRequest = getPageRequest(page, pageSize, orderBy, username, age);
        return findWithFilter(username, age, pageRequest);
    }

    private Page<UserDetailDto> findWithFilter(String username, Integer age, PageRequest pageRequest) {
        if (!Objects.isNull(age) && StringUtils.hasText(username))
            return userRepository.findByUsernameAndAgeGreaterThanEqual(username, age, pageRequest).map(UserDetailDto::fromEntity);

        if (StringUtils.hasText(username))
            return userRepository.findByUsername(username, pageRequest).map(UserDetailDto::fromEntity);

        if (!Objects.isNull(age))
            return userRepository.findByAgeGreaterThanEqual(age, pageRequest).map(UserDetailDto::fromEntity);

        return userRepository.findAll(pageRequest).map(UserDetailDto::fromEntity);
    }

    private static PageRequest getPageRequest(Integer page, Integer pageSize, String orderBy, String username, Integer age) {
        var direction = Sort.Direction.DESC;
        if (orderBy.equalsIgnoreCase("asc"))
            direction = Sort.Direction.ASC;

        return PageRequest.of(page, pageSize, direction, "createdAt");
    }

    private UserEntity getUserEntity(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
