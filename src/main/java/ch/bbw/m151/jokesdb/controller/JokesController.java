	package ch.bbw.m151.jokesdb.controller;

	import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
	import ch.bbw.m151.jokesdb.repository.JokesRepository;
	import ch.bbw.m151.jokesdb.service.JokesService;
	import lombok.RequiredArgsConstructor;
	import org.springframework.data.domain.Pageable;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;
	import java.util.stream.Collectors;

	/**
	 * The Controller used for handling the jokes
	 */
	@RestController
	@RequiredArgsConstructor
	public class JokesController {
	
	
		private JokesRepository jokesRepository;
		private JokesService jokesService;
	
		public JokesController(JokesRepository jokesRepository, JokesService jokesService) {
			this.jokesRepository = jokesRepository;
			this.jokesService = jokesService;
		}
	
		/**
		 * Gets jokes from the api
		 * @param pageable to be called with params `?page=3&size=5`
		 * @return hilarious content
		 */
		@GetMapping("/jokes")
		public List<JokesEntity> getJokes(Pageable pageable) {
			return jokesRepository.findAll(pageable)
					.getContent().stream()
					.distinct()
					.collect(Collectors.toList());
		}

		/**
		 * Adds a rating to a joke
		 * @param rating the rating given to the joke
		 * @param jokeId the id of the joke which needs to be rated
		 * @return
		 */
		@PostMapping("/joke/{jokeId}/rate")
		public ResponseEntity<String> rateJoke(@RequestParam int rating, @PathVariable int jokeId) {
			if (rating < 0 || rating > 5) {
				return ResponseEntity.badRequest().body("Rating must be in range 0-5");
			}
	
			if (this.jokesService.addRating(jokeId, rating).isEmpty()) {
				return ResponseEntity.notFound().build();
			}
	
			return ResponseEntity.ok("Successfully rated joke");
		}
	
	}
