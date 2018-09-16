package ru.otus.homework.testtask.repository;

import ru.otus.homework.testtask.model.Book;

import java.util.List;

public interface BookCustomRepository {


    List<Book> findAllCustomMethod();
}
