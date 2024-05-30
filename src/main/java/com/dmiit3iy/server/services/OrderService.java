package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Order;

import java.util.List;

public interface OrderService {

    /**
     * Метод для добавления заказа. Осуществляет контроль за счетчиком нарушений пользователя.
     * Если у читателя 2 нарушения, то заказть книгу не получиться.
     *
     * @param idReader
     * @param idBook
     * @return заказ
     */
    Order add(long idReader, long idBook);

    /**
     * Метод для добавления заказа с учетом JWT-токена (для исключения возможности заказа другому пользователю)
     *
     * @param idReader
     * @param idBook
     * @param authHeader
     * @return
     */
    Order add(long idReader, long idBook, String authHeader);

    /**
     * Метод для поиска заказа по Id
     *
     * @param id
     * @return заказ
     */
    Order get(long id);

    /**
     * Метод для получения списка всех существующих заказов
     *
     * @return список заказов
     */
    List<Order> get();

    /**
     * Метод для получения списка заказов конкретного читателя
     *
     * @param idReader
     * @return список заказов
     */
    List<Order> getByIdReader(long idReader);

    /**
     * Метод для обновления параметров заказа
     *
     * @param order
     * @return заказ с обновленными параметрами
     */
    Order update(Order order);

    /**
     * Метод для обновления параметра даты возврата книги в заказе (при сдаче книги читателем)
     * и контроля за сроками возврата книги, если книга возращена с нарущением срок, то счетчик нарушений пользовался
     * увеличивется на 1.
     *
     * @param idOrder
     * @return заказ с обновленным параметром даты возврата книги
     */
    Order returnBook(long idOrder);
}
