package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.domain.Competenza;
import it.laziocrea.jemoloapp.service.dto.CompetenzaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.laziocrea.jemoloapp.domain.Competenza}.
 */
public interface CompetenzaService {

    /**
     * Save a competenza.
     *
     * @param competenzaDTO the entity to save.
     * @return the persisted entity.
     */
    CompetenzaDTO save(CompetenzaDTO competenzaDTO);

    /**
     * Get all the competenzas.
     *
     * @return the list of entities.
     */
    List<CompetenzaDTO> findAll();

    /**
     * Get the "id" competenza.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompetenzaDTO> findOne(Long id);

    /**
     * Delete the "id" competenza.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Optional<CompetenzaDTO> findOneByCandidato(Long idAmbito, Long idCandidato);

	Optional<CompetenzaDTO> findOneByCandidato(Competenza competenza);
}
