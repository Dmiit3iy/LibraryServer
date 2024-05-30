package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.dto.ResponseResult;
import com.dmiit3iy.server.models.Reader;
import com.dmiit3iy.server.services.ReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/readers")
public class ReaderController {
    private static final Logger logger = LoggerFactory.getLogger(ReaderController.class);
    private ReaderService readerService;

    @Autowired
    public void setReaderService(ReaderService readerService) {
        this.readerService = readerService;
    }

    /**
     * Обрабатывает post-зарос на добавление читателя
     *
     * @param reader
     * @return читатель
     */
    @PostMapping
    public ResponseEntity<ResponseResult<Reader>> add(@RequestBody Reader reader) {
        try {
            readerService.add(reader);
            logger.info("Создан новый читатель с логином: " + reader.getLogin());
            return new ResponseEntity<>(new ResponseResult(null, reader), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-зарос на получение списка всех читателей
     *
     * @return список читателей
     */
    @GetMapping
    public ResponseEntity<ResponseResult<List<Reader>>> get() {
        try {
            List<Reader> readerList = readerService.get();
            return new ResponseEntity<>(new ResponseResult<>(null, readerList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Обрабатывает get-запрос на получение читателя по его id
     *
     * @param id
     * @return читатель
     */

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<Reader>> get(@PathVariable("id") long id) {
        try {
            Reader reader = readerService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, reader), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

}
