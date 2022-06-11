package com.github.avenderov;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class AddressDataFetcher {

    private final UserService userService;

    public AddressDataFetcher(UserService userService) {
        this.userService = userService;
    }

    @Nullable
    public Address findByUserId(DataFetchingEnvironment env) {
        final User user = env.getSource();
        return userService.findAddressByUserId(user.id());
    }
}
