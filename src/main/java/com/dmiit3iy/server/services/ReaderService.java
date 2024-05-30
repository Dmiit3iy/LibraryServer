package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Reader;

import java.util.List;

public interface ReaderService {
    /**
     * Метод для добавления читателя
     *
     * @param libraryReader
     */
    void add(Reader libraryReader);

    /**
     * Метод для получения читателя по его Id
     *
     * @param id
     * @return читатель
     */
    Reader get(long id);

    /**
     * Метод для получения списка всех читателей
     *
     * @return список читателей
     */
    List<Reader> get();

    /**
     * Метод для удаления читателя по его Id
     *
     * @param id
     * @return удаленный читатель
     */
    Reader delete(long id);

    /**
     * Метод для обновления параметров читателя
     *
     * @param reader
     * @return читатель с обновленными параметрами
     */
    Reader update(Reader reader);

    /**
     * Метод для проверки наличия свободного места в листе заказов читателя.
     * Производит подсчет общего количества имеющихся книг в заказе читателя и возвращает true если еще можно добавить
     * книгу в заказ, иначе false
     *
     * @param id
     * @return true or false
     */

    boolean isOrderListIsEmpty(long id);
}
