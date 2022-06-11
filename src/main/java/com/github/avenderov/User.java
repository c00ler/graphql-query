package com.github.avenderov;

import java.util.UUID;

public record User(UUID id, String firstName, String lastName) {
}
