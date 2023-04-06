package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.category.CategoryCreateDto;
import ru.practicum.explorewithme.dto.category.CategoryResponseDto;
import ru.practicum.explorewithme.model.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> getAll(int from, int size);

    CategoryResponseDto getPubCategoryById(Long categoryId);

    CategoryResponseDto create(CategoryCreateDto dto);

    void delete(Long categoryId);

    CategoryResponseDto update(Long categoryId, CategoryCreateDto dto);

    Category getCategoryById(Long categoryId);

}
