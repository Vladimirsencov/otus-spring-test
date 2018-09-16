package ru.otus.homework.testtask.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.homework.testtask.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long>, BookCustomRepository {
    boolean existsBookById(Long id);

    void deleteBookById(Long id);

    @Query(value = "select distinct b from Book b left join fetch b.genres left join fetch b.authors")
    List<Book> findAllQueryMethod();


    @EntityGraph(value = "loadBookWithAuthorAndGenre", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findAll();
}
