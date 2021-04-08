package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.service.dto.DichiarazioniObligatorieDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.laziocrea.jemoloapp.domain.DichiarazioniObligatorie}.
 */
public interface DichiarazioniObligatorieService {

    /**
     * Save a dichiarazioniObligatorie.
     *
     * @param dichiarazioniObligatorieDTO the entity to save.
     * @return the persisted entity.
     */
    DichiarazioniObligatorieDTO save(DichiarazioniObligatorieDTO dichiarazioniObligatorieDTO);

    /**
     * Get all the dichiarazioniObligatories.
     *
     * @return the list of entities.
     */
    List<DichiarazioniObligatorieDTO> findAll();

    /**
     * Get the "id" dichiarazioniObligatorie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DichiarazioniObligatorieDTO> findOne(Long id);

    /**
     * Delete the "id" dichiarazioniObligatorie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
