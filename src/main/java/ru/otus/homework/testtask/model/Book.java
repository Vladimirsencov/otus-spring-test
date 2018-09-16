package ru.otus.homework.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Table(name = "books")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(name = "loadBookWithAuthorAndGenre",
                  attributeNodes = {
                                    @NamedAttributeNode(value = "authors") ,
                                    @NamedAttributeNode(value = "genres")})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", unique = true)
    String name;
    @Column(name = "description")
    String description;
    @Column(name = "pub_year")
    int pubYear;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    Set<Author> authors = new LinkedHashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    Set<Genre> genres = new LinkedHashSet<>();
}
