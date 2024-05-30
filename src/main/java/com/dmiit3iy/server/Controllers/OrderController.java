package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.dto.ResponseResult;
import com.dmiit3iy.server.models.Order;
import com.dmiit3iy.server.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Обрабатывает post-запрос для добавления нового заказа книги с учётом аутентификации. Информация получаемая из JWT-токена
     * используется для проверки соответствия пользователя совершающего запрос и пользователя указанного в запросе
     *
     * @param authHeader
     * @param idUser
     * @param idBook
     * @return заказ книги
     */
    @PostMapping
    public ResponseEntity<ResponseResult<Order>> addByUserAnbBook(@RequestHeader("Authorization") String authHeader, @RequestParam long idUser,
                                                                  @RequestParam long idBook) {
        try {
            Order order = this.orderService.add(idUser, idBook, authHeader);
            return new ResponseEntity<>(new ResponseResult<>(null, order), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Обрабатывает get-запрос для получения списка всех заказов
     *
     * @return список заказов
     */

    @GetMapping
    public ResponseEntity<ResponseResult<List<Order>>> get() {
        try {
            List<Order> orderList = this.orderService.get();
            return new ResponseEntity<>(new ResponseResult<>(null, orderList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос для получения информации о заказе по его id
     *
     * @param id
     * @return заказ
     */

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<Order>> get(@PathVariable long id) {
        try {
            Order order = this.orderService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, order), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос для получения всех заказов конкретного пользователя по его id
     *
     * @param idUser
     * @return список заказов
     */
    @GetMapping("/user/{idUser}")
    public ResponseEntity<ResponseResult<List<Order>>> getByIdUser(@PathVariable long idUser) {
        try {
            List<Order> orderList = this.orderService.getByIdReader(idUser);
            return new ResponseEntity<>(new ResponseResult<>(null, orderList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает put-запрос для внесения изменения в ранее сформированный запрос, в части установки даты возврата книги.
     * Используется при возврате книги.
     *
     * @param orderId
     * @return
     */
    @PutMapping("/{orderId}/return")
    public ResponseEntity<ResponseResult<Order>> put(@PathVariable long orderId) {
        try {
            Order updateOrder = this.orderService.returnBook(orderId);
            return new ResponseEntity<>(new ResponseResult<>(null, updateOrder), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
