package ch.bbw.m151.jokesdb.service;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import ch.bbw.m151.jokesdb.repository.JokesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JokesService {

	private static final Logger log = LoggerFactory.getLogger(JokesService.class);

	private final JokesRepository jokesRepository;


	private static final int JOKES_COUNT = 3;

	public JokesService(JokesRepository jokesRepository) {
		this.jokesRepository = jokesRepository;
	}

	/**
	 * Loads jokes using the RemoteJokesService and saves them in the repository
	 * @return a JokeEntity
	 */
	@EventListener(ContextRefreshedEvent.class)
	public JokesEntity loadJokes() {
		ArrayList<JokesEntity> jokes= new ArrayList<JokesEntity>();
		if (jokesRepository.count() != 0) {
			log.info("database already contains data...");
			return null;
		}
		log.info("will load jokes from API...");
		RemoteJokesService rjs = new RemoteJokesService();
		for (int i = 0; i < JOKES_COUNT; i ++) {
			JokesEntity joke = rjs.fetchJoke();
			jokes.add(joke);
		}
		var jokeEntity = rjs.fetchJoke();
		jokesRepository.saveAll(jokes);
		return jokeEntity;
	}

	/**
	 * Adds a rating to the joke
	 * @param jokeId the id of the joke which will be rated
	 * @param rating the rating given to the joke
	 * @return nothing
	 */
	public Optional<Void> addRating(int jokeId, int rating) {
		JokesEntity jokeToBeRated = jokesRepository.findById(jokeId).get();
		jokeToBeRated.setTotalRatings(rating);
		this.jokesRepository.save(jokeToBeRated);
		return Optional.empty();
	}


}