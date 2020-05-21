package com.infoshareacademy.dreamteam.storage;

import com.infoshareacademy.dreamteam.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDb {

    private static List<User> userRepository = new ArrayList<>();

    public static List<User> getRepository() {
        if (userRepository.size() == 0) {
            fillRepositoryWithDefaults();
        }
        return userRepository;
    }


    private static void fillRepositoryWithDefaults() {

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Jan");
        user1.setEmail("Jan@gmail.com");
        user1.setRole("user");
        userRepository.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Adam");
        user2.setEmail("Adam@gmail.com");
        user2.setRole("user");
        userRepository.add(user2);

        User user3 = new User();
        user3.setId(3L);
        user3.setName("Anna");
        user3.setEmail("Anna@gmail.com");
        user3.setRole("user");
        userRepository.add(user3);
    }

    public static boolean contains(User user) {
        List<User> repository = getRepository();
        for (User userFromList : repository) {
            if (userFromList.getId().equals(user.getId())) {
                return true;
            }
        }
        return false;
    }

}