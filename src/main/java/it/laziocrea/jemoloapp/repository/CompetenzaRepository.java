package it.laziocrea.jemoloapp.repository;

import it.laziocrea.jemoloapp.domain.Competenza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Spring Data  repository for the Competenza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenzaRepository extends JpaRepository<Competenza, Long> {
	
	@Query("SELECT c FROM Competenza c WHERE c.ambitoComp.id = :idAmbito and c.anagrafica.id = :idCandidato")
	Optional<Competenza> findOneByCandidato(@Param(value = "idAmbito") Long idAmbito, @Param(value = "idCandidato") Long idCandidato);
}
