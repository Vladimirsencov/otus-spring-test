package ru.otus.homework.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.homework.testtask.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
