package ru.otus.homework.testtask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.testtask.model.Book;
import ru.otus.homework.testtask.repository.AuthorRepository;
import ru.otus.homework.testtask.repository.BookRepository;
import ru.otus.homework.testtask.repository.GenreRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql(scripts = "/test.sql")
public class TestApplicationTests {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    AuthorRepository authorRepository;

    Long bookId = 1L;

    @Test
    @Transactional
    public void findBookTest() {
        Book book = bookRepository.findById(bookId).orElse(null);
        assertNotNull("book present", book);
        assertEquals("Ivan Petrov", new ArrayList<>(book.getAuthors()).get(0).getName());
        assertEquals("Fantastic", new ArrayList<>(book.getGenres()).get(0).getName());
    }

    @Test
    @Transactional
    public void findAllBooksGraphMethod() {

        Book book = bookRepository.findAll().get(0);
        assertNotNull("book present", book);
        assertEquals("Ivan Petrov", new ArrayList<>(book.getAuthors()).get(0).getName());
        assertEquals("Fantastic", new ArrayList<>(book.getGenres()).get(0).getName());

    }

    @Test
    @Transactional
    public void findAllBooksQueryMethod() {

        Book book = bookRepository.findAllQueryMethod().get(0);
        assertNotNull("book present", book);
        assertEquals("Ivan Petrov", new ArrayList<>(book.getAuthors()).get(0).getName());
        assertEquals("Fantastic", new ArrayList<>(book.getGenres()).get(0).getName());

    }

    @Test
    @Transactional
    public void findAllBooksCustomMethod() {
        Book book = bookRepository.findAllCustomMethod().get(0);
        assertNotNull("book present", book);
        assertEquals("Ivan Petrov", new ArrayList<>(book.getAuthors()).get(0).getName());
        assertEquals("Fantastic", new ArrayList<>(book.getGenres()).get(0).getName());
    }


    @Test
    @Transactional
    public void existBookTest() {
        assertTrue(bookRepository.existsBookById(bookId));
    }

    @Test
    @Transactional
    public void removeBookTest() {
        assertTrue(bookRepository.existsBookById(bookId));
        bookRepository.deleteBookById(bookId);
        assertFalse(bookRepository.existsBookById(bookId));
    }

}
