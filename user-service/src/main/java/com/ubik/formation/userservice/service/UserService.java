package com.ubik.formation.userservice.service;

import com.ubik.formation.userservice.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findUserByEmail(String email);
    UserDto save(UserDto userDto);
    void delete(Long id);
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    void lockUser(Long userId);

}
