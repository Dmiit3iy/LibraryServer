package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.dto.ResponseResult;
import com.dmiit3iy.server.models.Book;
import com.dmiit3iy.server.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Обрабатывает post-запрос на добавление книги
     *
     * @param book
     * @return Book
     */
    @PostMapping
    public ResponseEntity<ResponseResult<Book>> add(@RequestBody Book book) {
        try {
            this.bookService.add(book);
            return new ResponseEntity<>(new ResponseResult<>(null, book), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос на получение списка всех книг
     *
     * @return список книг
     */
    @GetMapping
    public ResponseEntity<ResponseResult<List<Book>>> get() {
        try {
            List<Book> bookList = bookService.get();
            return new ResponseEntity<>(new ResponseResult<>(null, bookList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос на получение всех книг доступных к заказу
     *
     * @return список книг
     */
    @GetMapping("/available")
    public ResponseEntity<ResponseResult<List<Book>>> getAvailable() {
        try {
            List<Book> bookList = bookService.getAllAvailable();
            return new ResponseEntity<>(new ResponseResult<>(null, bookList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос на получение книги по ее id
     *
     * @param id
     * @return книга
     */

    @GetMapping("/find/{id}")
    public ResponseEntity<ResponseResult<Book>> getById(@PathVariable("id") long id) {
        try {
            Book book = bookService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, book), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос для получения книги по автору и названию
     *
     * @param author
     * @param title
     * @return книга
     */
    @GetMapping("/find")
    public ResponseEntity<ResponseResult<Book>> get(@RequestParam String author, @RequestParam String title) {
        try {
            Book book = this.bookService.get(author, title);
            return new ResponseEntity<>(new ResponseResult<>(null, book), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос для получения списка книг по названию, в том числе и по неполному названию книги
     *
     * @param title
     * @return список книг
     */
    @GetMapping("/find/title")
    public ResponseEntity<ResponseResult<List<Book>>> getByTitle(@RequestParam String title) {
        try {
            List<Book> bookList = this.bookService.getAllByTitle(title);
            return new ResponseEntity<>(new ResponseResult<>(null, bookList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос для получения списка книг по автору, в том числе по неполному описанию
     *
     * @param author
     * @return список книг
     */
    @GetMapping("/find/author")
    public ResponseEntity<ResponseResult<List<Book>>> getByAuthor(@RequestParam String author) {
        try {
            List<Book> bookList = this.bookService.getAllByAuthor(author);
            return new ResponseEntity<>(new ResponseResult<>(null, bookList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает delete-запрос на удаление книги по ее id
     *
     * @param id
     * @return удаленная книга
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult<Book>> delete(@PathVariable("id") long id) {
        try {
            Book book = bookService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, book), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
