package ru.otus.homework.testtask.shell;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.homework.testtask.model.Author;
import ru.otus.homework.testtask.model.Genre;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class ShellParametersConverter {

    Set<Author> authorsString2List(String authorsStr) {
        if (authorsStr == null) {
            return null;
        }
        return Arrays.stream(authorsStr.split(","))
                .filter(s -> !s.replaceAll(" ", "").isEmpty())
                .map(s -> new Author(null, s)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    Set<Genre> genresString2List(String genresStr) {
        if (genresStr == null) {
            return null;
        }
        return Arrays.stream(genresStr.split(",")).filter(s -> !s.replaceAll(" ", "").isEmpty())
                .map(s -> new Genre(null, s))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
