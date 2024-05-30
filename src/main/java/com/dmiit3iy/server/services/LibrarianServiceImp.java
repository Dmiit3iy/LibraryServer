package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Librarian;
import com.dmiit3iy.server.repositories.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianServiceImp implements LibrarianService {
    private LibrarianRepository librarianRepository;

    @Autowired
    public void setLibrarianRepository(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Librarian librarian) {
        try {
            librarian.setPassword(passwordEncoder.encode(librarian.getPassword()));
            librarianRepository.save(librarian);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Пользователь уже добавлен!");
        }
    }

    @Override
    public Librarian get(long id) {
        return librarianRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Такого пользователя нет"));
    }

    @Override
    public List<Librarian> get() {
        return librarianRepository.findAll();
    }

    @Override
    public Librarian delete(long id) {
        Librarian librarian = this.get(id);
        librarianRepository.delete(librarian);
        return librarian;
    }

}
