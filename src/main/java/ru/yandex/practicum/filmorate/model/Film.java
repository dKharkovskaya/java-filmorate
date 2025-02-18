package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @NonNull
    private Set<Genre> genres = new HashSet<>();
    @NonNull
    private Mpa mpa;
    @NonNull
    private Set<Integer> likes = new HashSet<>();

}
