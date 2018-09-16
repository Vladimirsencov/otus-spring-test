package ru.otus.homework.testtask.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.homework.testtask.model.Author;
@Repository
public interface AuthorRepository extends CrudRepository<Author,  Long> {
}
