package ru.otus.homework.testtask.sevice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.homework.testtask.model.Book;
import ru.otus.homework.testtask.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void saveBook(Book book) {
        if (book.getId() == null || !bookRepository.existsBookById(book.getId())) {
            Book b = bookRepository.save(book);
            log.debug("book was saved: {}", b);
        } else {
            throw new IllegalArgumentException("book already created");
        }
    }

    public Optional<Book> getBookById(long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.ifPresent(b -> log.debug("book {} was found", b));
        if (!book.isPresent()) {
            log.debug("book not found by {}", bookId);
        }
        return book;
    }

    public Optional<Book> updateBook(Book book) {
        if (book.getId() != null && bookRepository.existsBookById(book.getId())) {
            Book b = bookRepository.save(book);
            log.debug("book was updated: {}", b);
            return Optional.of(b);
        } else {
            throw new IllegalArgumentException("book not found for update");
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllWithAuthorsAndGenres();
    }

    public void removeBook(long bookId) {
        bookRepository.deleteBookById(bookId);
    }
}
