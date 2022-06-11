package com.github.avenderov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer(UserDataFetcher userDataFetcher,
                                                    AddressDataFetcher addressDataFetcher) {
        return builder ->
            builder
                .type("Query", wiring ->
                    wiring
                        .dataFetcher("users", userDataFetcher::findAll)
                        .dataFetcher("user", userDataFetcher::findById))
                .type("User", wiring ->
                    wiring
                        .dataFetcher("address", addressDataFetcher::findByUserId));
    }
}
