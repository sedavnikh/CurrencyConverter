package org.converter.service;

import org.converter.domain.UserCredentials;
import org.converter.exceptions.UserExistsException;
import org.converter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.converter.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User systemUser = this.userRepository.findByEmail(s);
        if(systemUser != null) {
            return new UserCredentials(systemUser.getId(), systemUser.getEmail(), systemUser.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")));
        }

        return null;
    }

    @Transactional
    public void save(User user) throws UserExistsException {
        User systemUser = this.userRepository.findByEmail(user.getEmail());
        if(systemUser != null) {
            throw new UserExistsException("User already exists");
        }

        this.userRepository.save(user);
    }

}
