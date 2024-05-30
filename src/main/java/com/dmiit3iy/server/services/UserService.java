package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.User;

import java.util.List;

public interface UserService {
    /**
     * Метод для добавления пользователя
     *
     * @param user
     */
    void add(User user);

    /**
     * Метод для получения пользователя по id
     *
     * @param id
     * @return пользователь
     */
    User get(long id);

    /**
     * Метод для получения списка всех пользователей
     *
     * @return список пользователей
     */
    List<User> get();

    /**
     * Метод для поиска пользователя по его логину
     *
     * @param login
     * @return пользователь
     */
    User findByLogin(String login);

}
