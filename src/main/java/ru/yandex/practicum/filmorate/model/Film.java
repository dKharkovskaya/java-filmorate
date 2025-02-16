package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class Film {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private String genre;
    @NonNull
    private String rating;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    private long duration;
    private long like;

}
