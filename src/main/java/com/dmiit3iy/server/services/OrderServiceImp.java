package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Book;
import com.dmiit3iy.server.models.Reader;
import com.dmiit3iy.server.models.Order;
import com.dmiit3iy.server.models.User;
import com.dmiit3iy.server.repositories.OrderRepository;
import com.dmiit3iy.server.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImp implements OrderService {
    @Value("${library.date}")
    private long date;
    @Value("${jwt.token.secret}")
    private String secret;

    private OrderRepository orderRepository;
    private BookService bookService;
    private ReaderService readerService;
    private UserService userService;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setReaderService(ReaderService readerService) {
        this.readerService = readerService;
    }


    @Override
    public Order add(long idReader, long idBook) {
        Reader libraryReader = readerService.get(idReader);
        Book book = bookService.get(idBook);
        if (libraryReader.getViolationCount() == 2) {
            throw new IllegalArgumentException("У читателя 2 нарушения, заказ книг заблокирован!");
        }
        if (!book.isAvailable()) {
            throw new IllegalArgumentException("Книга не доступна к заказу!");
        }
        if (!readerService.isOrderListIsEmpty(libraryReader.getId())) {
            throw new IllegalArgumentException("У читателя уже 3 книги на руках!");
        }

        book.setAvailable(false);
        Order order = new Order(libraryReader, book);
        libraryReader.addOrder(order);

        bookService.update(book);
        readerService.update(libraryReader);
        return orderRepository.save(order);
    }

    @Override
    public Order add(long idReader, long idBook, String authHeader) {
        String token = authHeader.substring(7);
        String s = Base64.getEncoder().encodeToString(secret.getBytes());
        Jws<Claims> claims = Jwts.parser().setSigningKey(s).parseClaimsJws(token);
        String login = (String) claims.getBody().get("sub");
        User user = this.userService.findByLogin(login);
        if (user.getId() == idReader) {
            return this.add(idReader, idBook);
        } else {
            throw new IllegalArgumentException("Попытка заказать книгу другому читателю");
        }
    }


    @Override
    public Order get(long id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Заказ с таким Id отсутствует"));
    }

    @Override
    public List<Order> get() {
        return this.orderRepository.findAll();
    }

    @Override
    public List<Order> getByIdReader(long idReader) {
        return get().stream().filter(x -> x.getReader().getId() == idReader).collect(Collectors.toList());
    }

    @Override
    public Order update(Order order) {
        Order base = this.get(order.getId());
        base.setReturnDate(order.getReturnDate());
        this.orderRepository.save(base);
        return base;
    }

    @Override
    public Order returnBook(long idOrder) {
        Order order = this.get(idOrder);
        if (order.getReturnDate() == null) {
            order.setReturnDate(LocalDate.now());
            Book book = order.getBook();
            book.setAvailable(true);
            Reader reader = order.getReader();
            if (order.getOrderDate().plusDays(date).isBefore(LocalDate.now())) {
                reader.setViolationCount(reader.getViolationCount() + 1);
            }
            this.update(order);
            List<Order> list = reader.getOrderList();
            list.remove(order);
            reader.setOrderList(list);
            this.readerService.update(reader);
            return order;
        } else {
            throw new IllegalArgumentException("Заказ с таким ID уже был возвращен!");
        }
    }
}
