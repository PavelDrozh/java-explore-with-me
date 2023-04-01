package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.UserResponseDto;
import ru.practicum.explorewithme.dto.UserShortResponseDto;
import ru.practicum.explorewithme.dto.UsersCreateDto;
import ru.practicum.explorewithme.model.User;

@Component
public class UserMapper {

    public User toUser(UsersCreateDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    public UserResponseDto toResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public UserShortResponseDto toShortResponseDto(User user) {
        return UserShortResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
