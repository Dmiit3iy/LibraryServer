package com.dmiit3iy.server.Controllers;

import com.dmiit3iy.server.dto.ResponseResult;
import com.dmiit3iy.server.models.User;
import com.dmiit3iy.server.security.jwt.JwtTokenProvider;
import com.dmiit3iy.server.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    /**
     * Обрабатывает post-запрос на получение JWT-токена
     * @param userName
     * @param password
     * @return String
     */
    @PostMapping
    public ResponseEntity<ResponseResult<String>> login(@RequestParam String userName, @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
            User user = userService.findByLogin(userName);
            String token = jwtTokenProvider.createToken(userName, user.getClass().getSimpleName());
            logger.info("Успешная попытка аутентификации для пользователя с логином: " + userName);
            return new ResponseEntity<>(new ResponseResult<>(null, token), HttpStatus.OK);
        } catch (AuthenticationException e) {
            logger.warn("Неуспешная попытка аутентификации для пользователя с логином: " + userName);
            throw new BadCredentialsException("Неправильное имя пользователя или пароль");
        }
    }
}
