package com.dmiit3iy.server.repositories;

import com.dmiit3iy.server.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByAuthorAndTitle(String author, String title);

}
