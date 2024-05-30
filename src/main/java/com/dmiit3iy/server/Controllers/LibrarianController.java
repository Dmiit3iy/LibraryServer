package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.dto.ResponseResult;
import com.dmiit3iy.server.models.Librarian;
import com.dmiit3iy.server.services.LibrarianService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {
    private static final Logger logger = LoggerFactory.getLogger(LibrarianController.class);
    private LibrarianService librarianService;

    @Autowired
    public void setLibrarianService(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    /**
     * Обрабатывает post-запрос на добавление библиотекаря
     *
     * @param librarian
     * @return библиотекарь
     */
    @PostMapping
    public ResponseEntity<ResponseResult<Librarian>> add(@Valid @RequestBody Librarian librarian) {
        try {
            librarianService.add(librarian);
            logger.info("Создан новый библиотекарь с логином: " + librarian.getLogin());
            return new ResponseEntity<>(new ResponseResult(null, librarian), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос на получение списка всех библиотекарей
     *
     * @return список библиотекарей
     */
    @GetMapping
    public ResponseEntity<ResponseResult<List<Librarian>>> get() {
        try {
            List<Librarian> librarianList = librarianService.get();
            return new ResponseEntity<>(new ResponseResult<>(null, librarianList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос на получение библиотекаря по его id
     *
     * @param id
     * @return библиотекраь
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<Librarian>> get(@PathVariable("id") long id) {
        try {
            Librarian librarian = librarianService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, librarian), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
