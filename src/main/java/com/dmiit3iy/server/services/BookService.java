package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Book;

import java.util.List;

public interface BookService {
    /**
     * Метод добавления новой книги
     *
     * @param book
     */
    void add(Book book);

    /**
     * Метод для поиска книги по ее Id
     *
     * @param id
     * @return книга
     */
    Book get(long id);

    /**
     * Метод для поиска книги по автору и названию книги
     *
     * @param author
     * @param title
     * @return книга
     */
    Book get(String author, String title);

    /**
     * Метод для поиска всех книг в библиотеке
     *
     * @return список книг
     */
    List<Book> get();

    /**
     * Метод для получения всех книг доступных к заказу
     *
     * @return список книг
     */
    List<Book> getAllAvailable();

    /**
     * Метод для поиска всех книг конкретного автора
     *
     * @param author
     * @return список книг
     */
    List<Book> getAllByAuthor(String author);

    /**
     * Метод для поиска книг по заголовку книги
     *
     * @param title
     * @return список книг
     */
    List<Book> getAllByTitle(String title);

    /**
     * Метод для обновления информации о книге
     *
     * @param book
     * @return книга с обновленными параметрами
     */
    Book update(Book book);

    /**
     * Метод для удаления книги
     *
     * @param id
     * @return удаленная книга
     */
    Book delete(long id);
}
