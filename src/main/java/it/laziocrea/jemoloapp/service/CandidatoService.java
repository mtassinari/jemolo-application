package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.domain.Candidato;
import it.laziocrea.jemoloapp.domain.User;
import it.laziocrea.jemoloapp.service.dto.CandidatoDTO;
import it.laziocrea.jemoloapp.service.dto.IscrittoDTO;
import it.laziocrea.jemoloapp.web.rest.vm.ManagedCandidatoVM;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Service Interface for managing {@link it.laziocrea.jemoloapp.domain.Candidato}.
 */
public interface CandidatoService {

    /**
     * Save a candidato.
     *
     * @param candidatoDTO the entity to save.
     * @return the persisted entity.
     */
    Candidato save(CandidatoDTO candidatoDTO);

    /**
     * Get all the candidatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidatoDTO> findAll(Pageable pageable);
    
    /**
     * Get all the CandidatoDTO where AnagraficaCandidato is {@code null}.
     *
     * @return the list of entities.
     */
    List<CandidatoDTO> findAllWhereAnagraficaCandidatoIsNull();

    /**
     * Get the "id" candidato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidatoDTO> findOne(Long id);

    /**
     * Delete the "id" candidato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * 
     * @param CandidatoDTO
     * @param password
     * @return the {@link Candidato} registered
     */
	Candidato registerCandidato(CandidatoDTO candidatoDTO, String password);
	
	/**
	 * 
	 * @param key the activation key
	 * @return Candidato th activated candidato
	 */
	Optional<Candidato> activateCandidatoRegistration(String key);
	
    /**
     * Update a candidato.
     *
     * @param candidatoDTO the entity to update.
     * @return the updated entity.
     */
	Optional<CandidatoDTO> update(CandidatoDTO candidatoDTO);
	
	/**
	 * Update basic information (nome, cognome, email, language) for the current candidato.
	 * 
	 * @param nome
	 * @param cognome
	 * @param email
	 * @param langKey
	 * @param imageUrl
	 */
	void updateUser(String nome, String cognome, String email, String langKey, String imageUrl);
	
	/**
	 * 
	 * @return Candidato con le Authority
	 */
	Optional<Candidato> getCandidatoWithAuthorities();
	
	/**
	 * 
	 * @param currentClearTextPassword
	 * @param newPassword
	 */
	void changePassword(String currentClearTextPassword, String newPassword);
	
	/**
	 * 
	 * @param newPassword
	 * @param key
	 * @return
	 */
	Optional<Candidato> completePasswordReset(String newPassword, String key);
	
	/**
	 * 
	 * @param mail
	 * @return
	 */
	Optional<Candidato> requestPasswordReset(String mail);
	
	/**
	 * 
	 * @param codiceFiscale
	 * @return
	 */
	Optional<Candidato> requestPasswordReset2(String codiceFiscale);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<IscrittoDTO> findAllIscritti(Pageable pageable);
	
	/**
	 * 
	 * @param iscrittoDTO
	 * @return
	 */
	Optional<CandidatoDTO> update(@Valid IscrittoDTO iscrittoDTO);
	
	/**
	 * 
	 * @param codiceFiscale
	 */
	void deleteAccount(String codiceFiscale);
	
	/**
	 * Carica tutti tutti gli scritti dal db in formato Excel
	 */
	public ByteArrayInputStream loadAlliscritti();

	/**
	 * Get all the actives.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<IscrittoDTO> findAllIscrittiAttivi(Pageable pageable);

	/**
	 * Get all the candidatoes.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<IscrittoDTO> publicFindAllIscrittiAttivi(Pageable pageable);

}
