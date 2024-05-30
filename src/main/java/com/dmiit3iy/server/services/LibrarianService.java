package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Librarian;

import java.util.List;

public interface LibrarianService {
    /**
     * Метод для добавления библиотекаря
     *
     * @param librarian
     */
    void add(Librarian librarian);

    /**
     * Метод для поиска библиотекаря по Id
     *
     * @param id
     * @return библиотекарь
     */
    Librarian get(long id);

    /**
     * Метод для получения списка всех библиотекарей
     *
     * @return список библиотекарей
     */
    List<Librarian> get();

    /**
     * Метод для удаления библиотекаря по ID
     * @param id
     * @return удаленный библиотекарь
     */
    Librarian delete(long id);
}
