package ru.otus.homework.testtask.i18n;

import org.springframework.context.MessageSource;

import java.util.Locale;

public class MessageSourceWrapper {

    private final Locale locale;
    private final MessageSource ms;

    public MessageSourceWrapper(MessageSource ms, Locale locale) {
        this.ms = ms;
        this.locale = locale;
    }

    public String getMsg(String messageName) {
        return ms.getMessage(messageName, null, locale);
    }

    public String getMsg(String messageName, String param) {
        return ms.getMessage(messageName, new String[]{param}, locale);
    }

    public String getMsg(String messageName, Object... params) {
        return ms.getMessage(messageName, params, locale);
    }

}
