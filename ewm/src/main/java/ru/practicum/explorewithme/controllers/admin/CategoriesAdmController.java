package ru.practicum.explorewithme.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.category.CategoryCreateDto;
import ru.practicum.explorewithme.dto.category.CategoryResponseDto;
import ru.practicum.explorewithme.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoriesAdmController {

    public static final String CAT_PATH = "/{catId}";
    CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto create(@RequestBody @Valid CategoryCreateDto dto) {
        return service.create(dto);
    }

    @DeleteMapping(CAT_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long catId) {
        service.delete(catId);
    }

    @PatchMapping(CAT_PATH)
    public CategoryResponseDto update(@PathVariable Long catId, @RequestBody @Valid CategoryCreateDto dto) {
        return service.update(catId, dto);
    }
}
