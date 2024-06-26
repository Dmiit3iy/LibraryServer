package com.dmiit3iy.server.services;

import com.dmiit3iy.server.models.User;
import com.dmiit3iy.server.models.UserDetailsImpl;
import com.dmiit3iy.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + login));

        return user.map(UserDetailsImpl::new).get();
    }
}
