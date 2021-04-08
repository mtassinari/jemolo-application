package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.domain.SearchParam;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoCriteria;
import it.laziocrea.jemoloapp.service.dto.AnagraficaCandidatoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link it.laziocrea.jemoloapp.domain.AnagraficaCandidato}.
 */
public interface AnagraficaCandidatoService {

    /**
     * Save a anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    AnagraficaCandidatoDTO save(AnagraficaCandidatoDTO anagraficaCandidatoDTO);

    /**
     * Get all the anagraficaCandidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnagraficaCandidatoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" anagraficaCandidato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnagraficaCandidatoDTO> findOne(Long id);

    /**
     * Delete the "id" anagraficaCandidato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	AnagraficaCandidatoDTO save(AnagraficaCandidatoDTO anagraficaCandidatoDTO, List<MultipartFile> files);

	Page<AnagraficaCandidatoDTO> findByNome(String nome, String cognome, Pageable pageable);

	Page<AnagraficaCandidatoDTO> ricercaIscritti(String nome, String cognome, Pageable pageable);

	Page<AnagraficaCandidatoDTO> findByCriteria(SearchParam criteria, Pageable pageable);
	
	ByteArrayInputStream findByCriteriaToExcel(AnagraficaCandidatoCriteria params);

	/**
	 * Get one anagraficaCandidato by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<AnagraficaCandidatoDTO> findOne2(Long id);
}
