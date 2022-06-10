package com.github.avenderov;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer(UserService userService) {
        return builder ->
            builder
                .type("Query", wiring ->
                    wiring
                        .dataFetcher("users", __ -> userService.findAll())
                        .dataFetcher("user", e -> {
                            final UUID userId = UUID.fromString(e.getArgument("id"));
                            return userService.findById(userId).orElse(null);
                        }))
                .type("User", wiring ->
                    wiring
                        .dataFetcher("address", e -> {
                            final User user = e.getSource();
                            return userService.findAddressByUserId(user.id());
                        }));
    }
}

record Address(String addressLine, String city, String zipCode) {
}

record User(UUID id, String firstName, String lastName) {
}

@Service
class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final Collection<User> users;
    private final Map<UUID, Address> addresses;

    public UserService() {
        final var faker = Faker.instance();

        final var address = faker.address();
        final var name = faker.name();

        users =
            IntStream.range(0, 10)
                .mapToObj(a -> new User(UUID.randomUUID(), name.firstName(), name.lastName()))
                .toList();

        addresses =
            users
                .stream()
                .collect(
                    Collectors.toMap(
                        User::id,
                        __ -> new Address(address.streetName() + " " + address.streetAddressNumber(), address.city(), address.zipCode())));
    }

    Collection<User> findAll() {
        LOG.info("#findAll()");

        return users;
    }

    Address findAddressByUserId(UUID userId) {
        LOG.info("#findAddressByUserId({})", userId);

        return addresses.get(userId);
    }

    Optional<User> findById(UUID userId) {
        LOG.info("#findById({})", userId);

        return users
            .stream()
            .filter(u -> u.id().equals(userId))
            .findFirst();
    }
}
