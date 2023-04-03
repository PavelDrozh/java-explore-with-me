package ru.practicum.explorewithme.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class MainPage extends PageRequest {

    private final int from;

    public MainPage(int from, int size, Sort sort) {
        super(from / size, size, sort);
        this.from = from;
    }

    @Override
    public long getOffset() {
        return from;
    }
}
