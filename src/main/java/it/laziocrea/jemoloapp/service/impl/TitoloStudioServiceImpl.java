package it.laziocrea.jemoloapp.service.impl;

import it.laziocrea.jemoloapp.service.TitoloStudioService;
import it.laziocrea.jemoloapp.domain.TitoloStudio;
import it.laziocrea.jemoloapp.repository.TitoloStudioRepository;
import it.laziocrea.jemoloapp.service.dto.TitoloStudioDTO;
import it.laziocrea.jemoloapp.service.mapper.TitoloStudioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TitoloStudio}.
 */
@Service
@Transactional
public class TitoloStudioServiceImpl implements TitoloStudioService {

    private final Logger log = LoggerFactory.getLogger(TitoloStudioServiceImpl.class);

    private final TitoloStudioRepository titoloStudioRepository;

    private final TitoloStudioMapper titoloStudioMapper;

    public TitoloStudioServiceImpl(TitoloStudioRepository titoloStudioRepository, TitoloStudioMapper titoloStudioMapper) {
        this.titoloStudioRepository = titoloStudioRepository;
        this.titoloStudioMapper = titoloStudioMapper;
    }

    /**
     * Save a titoloStudio.
     *
     * @param titoloStudioDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TitoloStudioDTO save(TitoloStudioDTO titoloStudioDTO) {
        log.debug("Request to save TitoloStudio : {}", titoloStudioDTO);
        TitoloStudio titoloStudio = titoloStudioMapper.toEntity(titoloStudioDTO);
        titoloStudio = titoloStudioRepository.save(titoloStudio);
        return titoloStudioMapper.toDto(titoloStudio);
    }

    /**
     * Get all the titoloStudios.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TitoloStudioDTO> findAll() {
        log.debug("Request to get all TitoloStudios");
        return titoloStudioRepository.findAll().stream()
            .map(titoloStudioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one titoloStudio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TitoloStudioDTO> findOne(Long id) {
        log.debug("Request to get TitoloStudio : {}", id);
        return titoloStudioRepository.findById(id)
            .map(titoloStudioMapper::toDto);
    }

    /**
     * Delete the titoloStudio by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TitoloStudio : {}", id);
        titoloStudioRepository.deleteById(id);
    }
}
