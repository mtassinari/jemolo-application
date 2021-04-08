package it.laziocrea.jemoloapp.repository;
import it.laziocrea.jemoloapp.domain.Curriculum;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Curriculum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
	
	@EntityGraph(attributePaths = "allegato")
	Optional<Curriculum> findOneById(Long id);
}
