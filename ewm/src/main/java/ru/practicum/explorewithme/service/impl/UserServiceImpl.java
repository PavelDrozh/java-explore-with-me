package ru.practicum.explorewithme.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.UserResponseDto;
import ru.practicum.explorewithme.dto.UsersCreateDto;
import ru.practicum.explorewithme.exceptions.UserNotFoundException;
import ru.practicum.explorewithme.mapper.UserMapper;
import ru.practicum.explorewithme.model.MainPage;
import ru.practicum.explorewithme.model.User;
import ru.practicum.explorewithme.repository.UserRepository;
import ru.practicum.explorewithme.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository repository;
    UserMapper mapper;

    @Override
    public List<UserResponseDto> getALl(List<Long> ids, int from, int size) {
        MainPage page = new MainPage(from, size, Sort.unsorted());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
        return repository.findAllByIdIn(ids, page).stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto create(UsersCreateDto dto) {
        User user = mapper.toUser(dto);
        log.info(String.format("%s %s",user.getEmail(), user.getName()));
        User created = repository.save(user);
        return mapper.toResponseDto(created);
    }

    @Override
    public void delete(Long userId) {
        getUserById(userId);
        repository.deleteById(userId);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> user = repository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("Пользователь с id = %s не найден", userId));
        }
        return user.get();
    }
}
