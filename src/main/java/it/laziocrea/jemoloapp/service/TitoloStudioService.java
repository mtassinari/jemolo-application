package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.service.dto.TitoloStudioDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.laziocrea.jemolorooster.domain.TitoloStudio}.
 */
public interface TitoloStudioService {

    /**
     * Save a titoloStudio.
     *
     * @param titoloStudioDTO the entity to save.
     * @return the persisted entity.
     */
    TitoloStudioDTO save(TitoloStudioDTO titoloStudioDTO);

    /**
     * Get all the titoloStudios.
     *
     * @return the list of entities.
     */
    List<TitoloStudioDTO> findAll();


    /**
     * Get the "id" titoloStudio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TitoloStudioDTO> findOne(Long id);

    /**
     * Delete the "id" titoloStudio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
