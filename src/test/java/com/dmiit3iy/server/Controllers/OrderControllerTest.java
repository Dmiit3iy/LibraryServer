package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.dto.ResponseResult;
import com.dmiit3iy.server.models.Order;
import com.dmiit3iy.server.services.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Test
    void testGetOrders() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order());
        orderList.add(new Order());
        when(orderService.get()).thenReturn(orderList);
        ResponseEntity<ResponseResult<List<Order>>> response = orderController.get();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderList, response.getBody().getData());

    }

    @Test
    void testGetOrdersWithException() {
        when(orderService.get()).thenThrow(new IllegalArgumentException("Error"));
        ResponseEntity<ResponseResult<List<Order>>> response = orderController.get();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error", response.getBody().getMessage());

    }

    @Test
    void testGetByIdUser_Success() {
        long idUser = 1L;
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        when(orderService.getByIdReader(idUser)).thenReturn(orders);
        ResponseEntity<ResponseResult<List<Order>>> response = orderController.getByIdUser(idUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody().getData());

    }

    @Test
    void testGetByIdUser_IllegalArgumentException() {
        long idUser = 1L;
        when(orderService.getByIdReader(idUser)).thenThrow(new IllegalArgumentException("Invalid user ID"));
        ResponseEntity<ResponseResult<List<Order>>> response = orderController.getByIdUser(idUser);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid user ID", response.getBody().getMessage());

    }

    @Test
    void testPut_Success() {
        long orderId = 1L;
        Order updatedOrder = new Order();
        when(orderService.returnBook(orderId)).thenReturn(updatedOrder);
        ResponseEntity<ResponseResult<Order>> response = orderController.put(orderId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedOrder, response.getBody().getData());

    }

    @Test
    void testPut_IllegalArgumentException() {
        long orderId = 1L;
        when(orderService.returnBook(orderId)).thenThrow(new IllegalArgumentException("Invalid order ID"));
        ResponseEntity<ResponseResult<Order>> response = orderController.put(orderId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid order ID", response.getBody().getMessage());
    }

}