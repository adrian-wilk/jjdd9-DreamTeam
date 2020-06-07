package com.infoshareacademy.dreamteam.repository;


import com.infoshareacademy.dreamteam.domain.entity.User;

import javax.ejb.Local;
import java.util.List;
import java.util.Optional;

@Local
public interface UserRepository {

    void save(User user);

    public void update(User user);

    Optional<User> findUserByEmail(String email);

    List<User> findAll();

}