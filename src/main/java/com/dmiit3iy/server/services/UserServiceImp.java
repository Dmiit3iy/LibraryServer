package com.dmiit3iy.server.services;
import com.dmiit3iy.server.models.User;
import com.dmiit3iy.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Пользователь уже добавлен!");
        }
    }

    @Override
    public User get(long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Такого пользователя нет"));
    }

    @Override
    public List<User> get() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String username) {
        return userRepository.findByLogin(username).orElseThrow(() -> new IllegalArgumentException("Такого пользователя нет"));
    }
}
