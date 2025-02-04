package com.ubik.formation.userservice.service;

import com.ubik.formation.userservice.converter.UserConverter;
import com.ubik.formation.userservice.dto.UserDto;
import com.ubik.formation.userservice.entity.User;
import com.ubik.formation.userservice.exception.ResourceNotFoundException;
import com.ubik.formation.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final KafkaProducerService kafkaProducerService;

    public UserServiceImpl(UserRepository userRepository, KafkaProducerService kafkaProducerService) {
        this.userRepository = userRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserConverter.toDto(user);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        // TODO get nombre d'emprunt pour check si locked ou non et update
        return UserConverter.toDto(userRepository.save(UserConverter.toEntity(userDto)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            kafkaProducerService.sendUserDeletedMessage(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return UserConverter.toDto(user.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserConverter::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void lockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        user.setLocked(true);
        userRepository.save(user);
    }

}
