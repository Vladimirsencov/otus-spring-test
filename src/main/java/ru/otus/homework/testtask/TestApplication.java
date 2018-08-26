package ru.otus.homework.testtask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.homework.testtask.i18n.MessageSourceWrapper;

import java.util.Locale;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public MessageSourceWrapper messageSourceWrapper(@Value("${spring.mvc.locale}") String locale) {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return new MessageSourceWrapper(ms, Locale.forLanguageTag(locale));
    }
}
