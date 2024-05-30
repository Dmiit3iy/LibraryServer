package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.dto.ResponseResult;
import com.dmiit3iy.server.models.User;
import com.dmiit3iy.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Обрабатывает get-зарос на получение списка всех пользователей
     *
     * @return список пользователей
     */
    @GetMapping(path = "/all")
    public ResponseEntity<ResponseResult<List<User>>> get() {
        try {
            List<User> trainerList = this.userService.get();
            return new ResponseEntity<>(new ResponseResult(null, trainerList), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}