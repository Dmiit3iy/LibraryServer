package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Book;
import com.dmiit3iy.server.models.Order;
import com.dmiit3iy.server.models.Reader;
import com.dmiit3iy.server.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class OrderServiceImpTest {
    @Value("${library.date}")
    private long date;
    private Order order;
    private Book book;
    private Reader reader;

    @InjectMocks
    private OrderServiceImp orderService;
    @Mock
    private ReaderServiceImp readerService;
    @Mock
    private BookServiceImpl bookService;
    @Mock
    private OrderRepository orderRepository;


    @BeforeEach
    void setup() {
        book = new Book();
        book.setId(1);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setAvailable(true);
        reader = new Reader();
        reader.setId(1);
        reader.setName("John Doe");
        reader.setViolationCount(0);
        reader.setOrderList(new ArrayList<>());
        order = new Order(1L, reader, book, LocalDate.now(), null);
    }


    @Test
    public void testAddWithReaderViolationCount2() {
        long idUser = 1L;
        long idBook = 2L;

        Reader libraryReader = new Reader();
        libraryReader.setViolationCount(2);

        Mockito.when(readerService.get(idUser)).thenReturn(libraryReader);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.add(idUser, idBook);
        });

        Assertions.assertEquals("У читателя 2 нарушения, заказ книг заблокирован!", exception.getMessage());
    }

    @Test
    public void testAddWithBookNotAvailable() {
        long idUser = 1L;
        long idBook = 2L;

        Reader libraryReader = new Reader();
        libraryReader.setViolationCount(0);

        Book book = new Book();
        book.setAvailable(false);

        Mockito.when(readerService.get(idUser)).thenReturn(libraryReader);
        Mockito.when(bookService.get(idBook)).thenReturn(book);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.add(idUser, idBook);
        });

        Assertions.assertEquals("Книга не доступна к заказу!", exception.getMessage());
    }

    @Test
    public void testAddWithOrderListNotEmpty() {
        long idUser = 1L;
        long idBook = 2L;

        Reader libraryReader = new Reader();
        libraryReader.setViolationCount(0);

        Book book = new Book();
        book.setAvailable(true);

        Mockito.when(readerService.get(idUser)).thenReturn(libraryReader);
        Mockito.when(bookService.get(idBook)).thenReturn(book);
        Mockito.when(readerService.isOrderListIsEmpty(libraryReader.getId())).thenReturn(false);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.add(idUser, idBook);
        });

        Assertions.assertEquals("У читателя уже 3 книги на руках!", exception.getMessage());
    }

    @Test
    public void testAddSuccessfully() {
        long idUser = 1L;
        long idBook = 2L;

        Reader libraryReader = new Reader();
        libraryReader.setViolationCount(0);
        libraryReader.setOrderList(new ArrayList<>());

        Book book = new Book();
        book.setAvailable(true);

        Order order = new Order(libraryReader, book);

        Mockito.when(readerService.get(idUser)).thenReturn(libraryReader);
        Mockito.when(bookService.get(idBook)).thenReturn(book);
        Mockito.when(readerService.isOrderListIsEmpty(libraryReader.getId())).thenReturn(true);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);
        Order savedOrder = orderService.add(idUser, idBook);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertEquals(libraryReader, savedOrder.getReader());
        Assertions.assertEquals(book, savedOrder.getBook());
    }

    @Test
    void testReturnBook_Success() {

        Mockito.when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Mockito.lenient().when(bookService.update(book)).thenReturn(book);
        Mockito.when(readerService.update(reader)).thenReturn(reader);

        Order returnedOrder = orderService.returnBook(1L);

        Assertions.assertEquals(order.getId(), returnedOrder.getId());
        Assertions.assertNotNull(returnedOrder.getReturnDate());
        Assertions.assertTrue(book.isAvailable());
        Assertions.assertEquals(0, reader.getViolationCount());
    }

    @Test
    void testReturnBook_BookAlreadyReturned() {
        order.setReturnDate(LocalDate.now());
        Mockito.when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        Assertions.assertThrows(IllegalArgumentException.class, () -> orderService.returnBook(1L));
    }

    @Test
    void testReturnBook_ReaderViolation() {
        order.setOrderDate(LocalDate.now().minusDays(date + 1));
        Mockito.when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Mockito.lenient().when(bookService.update(book)).thenReturn(book);
        Mockito.when(readerService.update(reader)).thenReturn(reader);
        Order returnedOrder = orderService.returnBook(1L);
        Assertions.assertEquals(order.getId(), returnedOrder.getId());
        Assertions.assertNotNull(returnedOrder.getReturnDate());
        Assertions.assertTrue(book.isAvailable());
        Assertions.assertEquals(1, reader.getViolationCount());
    }

}