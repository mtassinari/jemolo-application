package it.laziocrea.jemoloapp.repository;
import it.laziocrea.jemoloapp.domain.AmbitoCompetenza;
import it.laziocrea.jemoloapp.service.dto.AmbitoCompetenzaDTO;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AmbitoCompetenza entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmbitoCompetenzaRepository extends JpaRepository<AmbitoCompetenza, Long> {
	
	@Query("SELECT a FROM AmbitoCompetenza a WHERE a.tipo = :type order by a.id ASC")
	List<AmbitoCompetenza> findAllParent(@Param(value = "type") String type);
	
	@Query("SELECT a FROM AmbitoCompetenza a WHERE a.ambito.id = null order by a.id ASC")
	List<AmbitoCompetenza> findAllAmbiti(@Param(value = "type") String type);
	
	List<AmbitoCompetenza> findAllByAmbitoId(Long id);
	
	List<AmbitoCompetenza> findAllByStatoIsTrueAndAmbitoIdIsNullOrderByIdAsc();
}
