package ru.yandex.practicum.filmorate.model;

import lombok.Data;


@Data
public class Genre {
    private Integer id;
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre() {
    }

}
