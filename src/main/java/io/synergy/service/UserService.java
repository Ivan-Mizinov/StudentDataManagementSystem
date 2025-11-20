package io.synergy.service;

import io.synergy.dto.UserDto;
import io.synergy.dto.UserResponseDto;
import io.synergy.entity.UserEntity;
import io.synergy.exception.UsernameAlreadyExistsException;
import io.synergy.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(
            isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED
    )
    public UserResponseDto saveUser(UserDto user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameAlreadyExistsException("Username '" + user.getUsername() + "' уже используется");
        }

        UserEntity entity = mapToEntity(user);
        userRepository.save(entity);
        return this.mapToResponseDto(entity);
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.SUPPORTS,
            readOnly = true
    )
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto mapToResponseDto(UserEntity entity) {
        return new UserResponseDto(
                entity.getUsername(),
                entity.getRole()
        );
    }

    public UserEntity mapToEntity(UserDto dto) {
        return new UserEntity(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getRole()
        );
    }
}
