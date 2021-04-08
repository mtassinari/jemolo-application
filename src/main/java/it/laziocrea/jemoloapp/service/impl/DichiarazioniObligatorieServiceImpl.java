package it.laziocrea.jemoloapp.service.impl;

import it.laziocrea.jemoloapp.service.DichiarazioniObligatorieService;
import it.laziocrea.jemoloapp.domain.DichiarazioniObligatorie;
import it.laziocrea.jemoloapp.repository.DichiarazioniObligatorieRepository;
import it.laziocrea.jemoloapp.service.dto.DichiarazioniObligatorieDTO;
import it.laziocrea.jemoloapp.service.mapper.DichiarazioniObligatorieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DichiarazioniObligatorie}.
 */
@Service
@Transactional
public class DichiarazioniObligatorieServiceImpl implements DichiarazioniObligatorieService {

    private final Logger log = LoggerFactory.getLogger(DichiarazioniObligatorieServiceImpl.class);

    private final DichiarazioniObligatorieRepository dichiarazioniObligatorieRepository;

    private final DichiarazioniObligatorieMapper dichiarazioniObligatorieMapper;

    public DichiarazioniObligatorieServiceImpl(DichiarazioniObligatorieRepository dichiarazioniObligatorieRepository, DichiarazioniObligatorieMapper dichiarazioniObligatorieMapper) {
        this.dichiarazioniObligatorieRepository = dichiarazioniObligatorieRepository;
        this.dichiarazioniObligatorieMapper = dichiarazioniObligatorieMapper;
    }

    /**
     * Save a dichiarazioniObligatorie.
     *
     * @param dichiarazioniObligatorieDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DichiarazioniObligatorieDTO save(DichiarazioniObligatorieDTO dichiarazioniObligatorieDTO) {
        log.debug("Request to save DichiarazioniObligatorie : {}", dichiarazioniObligatorieDTO);
        DichiarazioniObligatorie dichiarazioniObligatorie = dichiarazioniObligatorieMapper.toEntity(dichiarazioniObligatorieDTO);
        dichiarazioniObligatorie = dichiarazioniObligatorieRepository.save(dichiarazioniObligatorie);
        return dichiarazioniObligatorieMapper.toDto(dichiarazioniObligatorie);
    }

    /**
     * Get all the dichiarazioniObligatories.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DichiarazioniObligatorieDTO> findAll() {
        log.debug("Request to get all DichiarazioniObligatories");
        return dichiarazioniObligatorieRepository.findAll().stream()
            .map(dichiarazioniObligatorieMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dichiarazioniObligatorie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DichiarazioniObligatorieDTO> findOne(Long id) {
        log.debug("Request to get DichiarazioniObligatorie : {}", id);
        return dichiarazioniObligatorieRepository.findById(id)
            .map(dichiarazioniObligatorieMapper::toDto);
    }

    /**
     * Delete the dichiarazioniObligatorie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DichiarazioniObligatorie : {}", id);
        dichiarazioniObligatorieRepository.deleteById(id);
    }
}
