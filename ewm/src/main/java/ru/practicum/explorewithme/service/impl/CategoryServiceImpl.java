package ru.practicum.explorewithme.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.CategoryCreateDto;
import ru.practicum.explorewithme.dto.CategoryResponseDto;
import ru.practicum.explorewithme.exceptions.CategoryNotFoundException;
import ru.practicum.explorewithme.exceptions.ConflictException;
import ru.practicum.explorewithme.mapper.CategoryMapper;
import ru.practicum.explorewithme.model.Category;
import ru.practicum.explorewithme.model.MainPage;
import ru.practicum.explorewithme.repository.CategoryRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository repository;
    CategoryMapper mapper;
    EventRepository eventRepository;

    @Override
    public List<CategoryResponseDto> getAll(int from, int size) {
        MainPage page = new MainPage(from, size, Sort.unsorted());
        return repository.findAll(page).stream()
                .map(mapper::toCategoryResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto getPubCategoryById(Long categoryId) {
        return mapper.toCategoryResponseDto(getCategoryById(categoryId));
    }

    @Override
    public CategoryResponseDto create(CategoryCreateDto dto) {
        Category forSave = mapper.toCategory(dto);
        Category saved = repository.save(forSave);
        return mapper.toCategoryResponseDto(saved);
    }

    @Override
    public void delete(Long categoryId) {
        if (!eventRepository.findAllByCategoryId(categoryId).isEmpty()) {
            throw new ConflictException(String.format("Категория с id = %d не пуста. Удаление запрещено", categoryId));
        }
        repository.deleteById(categoryId);
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        Optional<Category> category = repository.findById(categoryId);
        if (category.isEmpty()) {
            throw  new CategoryNotFoundException(String.format("Категория с id = %s не найдена", categoryId));
        }
        return category.get();
    }

    @Override
    public CategoryResponseDto update(Long categoryId, CategoryCreateDto dto) {
        Category forUpdate = getCategoryById(categoryId);
        if (forUpdate.getName().equalsIgnoreCase(dto.getName())) {
            throw new ConflictException(String.format("Изменение имени категории на уже существующее (%s = %s)",
                    forUpdate.getName(), dto.getName()));
        }
        forUpdate.setName(dto.getName());
        Category updated = repository.save(forUpdate);
        return mapper.toCategoryResponseDto(updated);
    }
}
