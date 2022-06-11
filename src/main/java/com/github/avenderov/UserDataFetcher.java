package com.github.avenderov;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.UUID;

@Component
public class UserDataFetcher {

    private final UserService userService;

    public UserDataFetcher(UserService userService) {
        this.userService = userService;
    }

    public Collection<User> findAll(DataFetchingEnvironment env) {
        return userService.findAll();
    }

    @Nullable
    public User findById(DataFetchingEnvironment env) {
        final UUID userId = UUID.fromString(env.getArgument("id"));
        return userService.findById(userId).orElse(null);
    }
}
