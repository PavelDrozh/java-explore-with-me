package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.user.UserResponseDto;
import ru.practicum.explorewithme.dto.user.UsersCreateDto;
import ru.practicum.explorewithme.model.User;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getALl(List<Long> ids, int from, int size);

    UserResponseDto create(UsersCreateDto dto);

    void delete(Long userId);

    User getUserById(Long userId);
}
