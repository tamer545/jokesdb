package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Loads a joke from the joke API
 */
@Service
public class RemoteJokesService {
    public JokesEntity fetchJoke() {
        final WebClient client;
        client = WebClient.create("https://v2.jokeapi.dev");
        return client.get().uri("/joke/Programming?type=single").retrieve().bodyToMono(JokesEntity.class).block();
    }


}