package it.laziocrea.jemoloapp.service.impl;

import it.laziocrea.jemoloapp.service.DichiarazioniService;
import it.laziocrea.jemoloapp.domain.Dichiarazioni;
import it.laziocrea.jemoloapp.repository.DichiarazioniRepository;
import it.laziocrea.jemoloapp.service.dto.DichiarazioniDTO;
import it.laziocrea.jemoloapp.service.mapper.DichiarazioniMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Dichiarazioni}.
 */
@Service
@Transactional
public class DichiarazioniServiceImpl implements DichiarazioniService {

    private final Logger log = LoggerFactory.getLogger(DichiarazioniServiceImpl.class);

    private final DichiarazioniRepository dichiarazioniRepository;

    private final DichiarazioniMapper dichiarazioniMapper;

    public DichiarazioniServiceImpl(DichiarazioniRepository dichiarazioniRepository, DichiarazioniMapper dichiarazioniMapper) {
        this.dichiarazioniRepository = dichiarazioniRepository;
        this.dichiarazioniMapper = dichiarazioniMapper;
    }

    /**
     * Save a dichiarazioni.
     *
     * @param dichiarazioniDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DichiarazioniDTO save(DichiarazioniDTO dichiarazioniDTO) {
        log.debug("Request to save Dichiarazioni : {}", dichiarazioniDTO);
        Dichiarazioni dichiarazioni = dichiarazioniMapper.toEntity(dichiarazioniDTO);
        dichiarazioni = dichiarazioniRepository.save(dichiarazioni);
        return dichiarazioniMapper.toDto(dichiarazioni);
    }

    /**
     * Get all the dichiarazionis.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DichiarazioniDTO> findAll() {
        log.debug("Request to get all Dichiarazionis");
        return dichiarazioniRepository.findAllByOrderById().stream()
            .map(dichiarazioniMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dichiarazioni by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DichiarazioniDTO> findOne(Long id) {
        log.debug("Request to get Dichiarazioni : {}", id);
        return dichiarazioniRepository.findById(id)
            .map(dichiarazioniMapper::toDto);
    }

    /**
     * Delete the dichiarazioni by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dichiarazioni : {}", id);
        dichiarazioniRepository.deleteById(id);
    }
}
