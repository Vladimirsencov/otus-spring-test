package ru.otus.homework.testtask;

import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.homework.testtask.model.Author;
import ru.otus.homework.testtask.model.Book;
import ru.otus.homework.testtask.model.Genre;
import ru.otus.homework.testtask.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestApplicationTests {
    @Autowired
	BookRepository bookRepository;


    Long bookId;


    @Before
	public  void before(){
		Book book = new Book();
		book.setName("Test_book");
		book.setPubYear(2018);
		Author author = new Author();
		author.setName("Ivan Petrov");
		Genre genre = new Genre();
		genre.setName("Fantastic");
		book.setAuthors(Collections.singleton(author));
		book.setGenres(Collections.singleton(genre));

		bookId = bookRepository.save(book).getId();
	}

	@Test
    @Transactional
	public void findBookTest(){
		Book book = bookRepository.findById(bookId).orElse(null);
		Assert.assertNotNull("book present", book);
		Assert.assertEquals("Ivan Petrov", new ArrayList<>(book.getAuthors()).get(0).getName());
		Assert.assertEquals("Fantastic", new ArrayList<>(book.getGenres()).get(0).getName());
	}

	@Test
	@Transactional
	public void existBookTest(){
		Assert.assertTrue(bookRepository.existsBookById(bookId));
	}

	@Test
	@Transactional
	public void removeBookTest(){
		Assert.assertTrue(bookRepository.existsBookById(bookId));
		bookRepository.deleteBookById(bookId);
		Assert.assertFalse(bookRepository.existsBookById(bookId));
	}

}
