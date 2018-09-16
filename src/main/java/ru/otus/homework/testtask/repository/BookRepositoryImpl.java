package ru.otus.homework.testtask.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import ru.otus.homework.testtask.model.Author;
import ru.otus.homework.testtask.model.Book;
import ru.otus.homework.testtask.model.Genre;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@Slf4j
public class BookRepositoryImpl implements BookCustomRepository {

    private static final String SQL =
            "select  b.id as b_id, b.name as b_name, b.description as b_des, b.pub_year as b_y, " +
                    "a.id as a_id, a.name a_name, " +
                    "gen.id as g_id , gen.name as g_name " +
                    "from books b  " +
                    "left join books_authors b_a on b_a.book_id = b.id " +
                    "left join authors a on a.id = b_a.author_id " +
                    "left join books_genres b_g on b.id = b_g.book_id " +
                    "left join genres gen  on b_g.genre_id = gen.id order by b_name, b_y";

    private final DataSource dataSource;
    private final NamedParameterJdbcOperations template;
    private final TransactionTemplate txManager;


    @Autowired
    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        template = new NamedParameterJdbcTemplate(dataSource);
        txManager = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
    }

    @Override
    public List<Book> findAllCustomMethod() {
     return   txManager.execute(status -> template.query(SQL, emptyMap(), rs -> {
            Map<Long, Book> accumulator = new LinkedHashMap<>();
            while (rs.next()) {
                long bookId = rs.getLong("b_id");

                accumulator.computeIfAbsent(bookId, id -> {
                    try {
                        Book book = new Book();
                        book.setId(id);
                        book.setName(rs.getString("b_name"));
                        book.setPubYear(rs.getInt("b_y"));
                        book.setDescription(rs.getString("b_des"));
                        return book;
                    } catch (SQLException ex) {
                        log.error("", ex);
                        throw new RuntimeException(ex);
                    }
                });

                accumulator.computeIfPresent(bookId, (id, book) -> {
                    try {
                        BigDecimal authorId = rs.getBigDecimal("a_id");
                        if (authorId != null) {
                            Author author = new Author(authorId.longValue(), rs.getString("a_name"));
                            book.getAuthors().add(author);
                        }
                        BigDecimal genreId = rs.getBigDecimal("g_id");
                        if (genreId != null) {
                            Genre genre = new Genre(genreId.longValue(), rs.getString("g_name"));
                            book.getGenres().add(genre);
                        }
                        return book;
                    } catch (SQLException ex) {
                        log.error("", ex);
                        throw new RuntimeException(ex);
                    }
                });
            }
            return new ArrayList<>(accumulator.values());
        }));

    }
}
