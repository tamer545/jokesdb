package ch.bbw.m151.jokesdb;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import ch.bbw.m151.jokesdb.service.RemoteJokesService;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class JokesDbApplicationTest implements WithAssertions {
	@Autowired
	JokesRepository jokesRepository;

	@Autowired
	private WebTestClient webTestClient;

	private final RemoteJokesService rjs = new RemoteJokesService();

    /**
     * Checks if jokes are loaded at the start of the application
     */
	@Test
	void jokesAreLoadedAtStartup() {
		var jokes = jokesRepository.findAll();
		assertThat(jokes).hasSizeGreaterThan(100)
				.allSatisfy(x -> assertThat(x).isNotNull());
	}

    /**
     * Checks for retrieving jokes via http get method
     */
	@Test
	void jokesCanBeRetrievedViaHttpGet() {
		var pageSize = 5;
		webTestClient.get()
				.uri("/jokes?page={page}&size={size}", 1, pageSize)
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBodyList(JokesEntity.class)
				.hasSize(pageSize);
	}

    /**
     * Tests if a joke is properly fetched
     */
    @Test
    void fetchOneJoke() {
        var joke = rjs.fetchJoke();
        assertThat(joke).isNotNull();
    }

    /**
     * Tests if the creationTimestamp of the joke is properly filled
     */
	@Test
	void jokesCreationTimestamp(){
		JokesEntity joke = rjs.fetchJoke();
		assertThat(joke.getCreatedOn()).isEqualTo(LocalDateTime.now());
	}

    /**
     * Tests if the updateTimestamp of the joke is properly filled
     */
    @Test
    void jokesUpdatedTimestamp(){
        JokesEntity joke = rjs.fetchJoke();
        assertThat(joke.getUpdatedOn()).isNotNull();
    }

    /**
     * Tests if the category of the joke is properly filled
     */
    @Test
    void jokesCategory(){
        JokesEntity joke = rjs.fetchJoke();
        assertThat(joke.getCategory()).isNotNull();
    }

    /**
     * Tests if the language of the joke is properly filled
     */
    @Test
    void jokesLanguage(){
        JokesEntity joke = rjs.fetchJoke();
        assertThat(joke.getLang()).isNotNull();
    }


}
