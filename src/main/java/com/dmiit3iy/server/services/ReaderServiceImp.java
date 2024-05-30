package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.Reader;
import com.dmiit3iy.server.models.Order;
import com.dmiit3iy.server.repositories.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderServiceImp implements ReaderService {
    private ReaderRepository readerRepository;

    @Autowired
    public void setReaderRepository(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Reader reader) {
        try {
            reader.setPassword(passwordEncoder.encode(reader.getPassword()));
            readerRepository.save(reader);
        } catch (
                DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Такой читатель уже добавлен!");
        }
    }

    @Override
    public Reader get(long id) {
        Reader libraryReader = readerRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Читателя с таким ID нет"));
        return libraryReader;
    }

    @Override
    public List<Reader> get() {
        return readerRepository.findAll();
    }

    @Override
    public Reader delete(long id) {
        Reader libraryReader = get(id);
        readerRepository.delete(libraryReader);
        return libraryReader;
    }

    @Override
    public Reader update(Reader reader) {
        Reader baseReader = get(reader.getId());
        baseReader.setName(reader.getName());
        baseReader.setLogin(reader.getLogin());
        baseReader.setPassword(passwordEncoder.encode(reader.getPassword()));
        baseReader.setPatronymic(reader.getPatronymic());
        baseReader.setViolationCount(reader.getViolationCount());
        baseReader.setOrderList(reader.getOrderList());
        return readerRepository.save(baseReader);
    }


    @Override
    public boolean isOrderListIsEmpty(long id) {
        boolean result = false;
        Reader libraryReader = get(id);
        List<Order> orderList = libraryReader.getOrderList();

        if (orderList.size() <= 2) {
            result = true;
        }
        return result;
    }
}
