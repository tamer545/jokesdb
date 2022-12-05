package ch.bbw.m151.jokesdb.repository;

import ch.bbw.m151.jokesdb.datamodel.JokesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository to save all the jokes in
 */
@Repository
public interface JokesRepository extends JpaRepository<JokesEntity, Integer> {

}
