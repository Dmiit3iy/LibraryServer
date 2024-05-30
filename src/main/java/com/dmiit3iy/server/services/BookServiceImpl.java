package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Book;
import com.dmiit3iy.server.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public void add(Book book) {
        try {
            bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Такая книга уже добавлена!");
        }
    }


    @Override
    public Book get(long id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Книги с таким Id не существует"));
    }


    @Override
    public Book get(String author, String title) {
        Book book = bookRepository.findByAuthorAndTitle(author, title).
                orElseThrow(() -> new IllegalArgumentException("Такой книги не существует"));
        return book;
    }


    @Override
    public List<Book> get() {
        return bookRepository.findAll();
    }


    @Override
    public List<Book> getAllAvailable() {
        List<Book> listAvailableBooks = this.get().stream().filter(x -> x.isAvailable() == true).collect(Collectors.toList());
        return listAvailableBooks;
    }


    @Override
    public List<Book> getAllByAuthor(String author) {
        List<Book> bookList = this.get().stream().filter(x -> x.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
        return bookList;
    }


    @Override
    public List<Book> getAllByTitle(String title) {
        List<Book> listBooks = this.get().stream().filter(x -> x.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        return listBooks;
    }


    @Override
    public Book update(Book book) {
        Book baseBook = get(book.getId());
        baseBook.setAvailable(book.isAvailable());
        baseBook.setAuthor(book.getAuthor());
        baseBook.setTitle(book.getTitle());
        baseBook.setAvailable(book.isAvailable());
        return bookRepository.save(baseBook);
    }


    @Override
    public Book delete(long id) {
        Book book = get(id);
        bookRepository.delete(book);
        return book;
    }
}
