package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.service.dto.DichiarazioniDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.laziocrea.jemoloapp.domain.Dichiarazioni}.
 */
public interface DichiarazioniService {

    /**
     * Save a dichiarazioni.
     *
     * @param dichiarazioniDTO the entity to save.
     * @return the persisted entity.
     */
    DichiarazioniDTO save(DichiarazioniDTO dichiarazioniDTO);

    /**
     * Get all the dichiarazionis.
     *
     * @return the list of entities.
     */
    List<DichiarazioniDTO> findAll();

    /**
     * Get the "id" dichiarazioni.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DichiarazioniDTO> findOne(Long id);

    /**
     * Delete the "id" dichiarazioni.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
