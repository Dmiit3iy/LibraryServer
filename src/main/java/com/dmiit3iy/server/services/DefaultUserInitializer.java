package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Librarian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer implements CommandLineRunner {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private LibrarianService librarianService;

    @Autowired
    public void setLibrarianService(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.findByLogin("librarianAdmin");
        } catch (IllegalArgumentException e) {
            System.out.println("Пользователь не найден");
            librarianService.add(new Librarian("librarianAdmin", "123", "Главный администратор", "Главный администратор",
                    "Главный администратор"));
        }
    }
}
