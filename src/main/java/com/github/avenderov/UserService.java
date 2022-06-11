package com.github.avenderov;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserService {

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

    public Collection<User> findAll() {
        LOG.info("#findAll()");

        return users;
    }

    public Address findAddressByUserId(UUID userId) {
        LOG.info("#findAddressByUserId({})", userId);

        return addresses.get(userId);
    }

    public Optional<User> findById(UUID userId) {
        LOG.info("#findById({})", userId);

        return users
            .stream()
            .filter(u -> u.id().equals(userId))
            .findFirst();
    }
}
