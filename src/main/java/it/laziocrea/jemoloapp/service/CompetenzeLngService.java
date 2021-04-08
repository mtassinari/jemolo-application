package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.domain.CompetenzeLng;
import it.laziocrea.jemoloapp.repository.CompetenzeLngRepository;
import it.laziocrea.jemoloapp.service.dto.CompetenzeLngDTO;
import it.laziocrea.jemoloapp.service.mapper.CompetenzeLngMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CompetenzeLng}.
 */
@Service
@Transactional
public class CompetenzeLngService {

    private final Logger log = LoggerFactory.getLogger(CompetenzeLngService.class);

    private final CompetenzeLngRepository competenzeLngRepository;

    private final CompetenzeLngMapper competenzeLngMapper;

    public CompetenzeLngService(CompetenzeLngRepository competenzeLngRepository, CompetenzeLngMapper competenzeLngMapper) {
        this.competenzeLngRepository = competenzeLngRepository;
        this.competenzeLngMapper = competenzeLngMapper;
    }

    /**
     * Save a competenzeLng.
     *
     * @param competenzeLngDTO the entity to save.
     * @return the persisted entity.
     */
    public CompetenzeLngDTO save(CompetenzeLngDTO competenzeLngDTO) {
        log.debug("Request to save CompetenzeLng : {}", competenzeLngDTO);
        CompetenzeLng competenzeLng = competenzeLngMapper.toEntity(competenzeLngDTO);
        competenzeLng = competenzeLngRepository.save(competenzeLng);
        return competenzeLngMapper.toDto(competenzeLng);
    }

    /**
     * Get all the competenzeLngs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompetenzeLngDTO> findAll() {
        log.debug("Request to get all CompetenzeLngs");
        return competenzeLngRepository.findAll().stream()
            .map(competenzeLngMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one competenzeLng by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CompetenzeLngDTO> findOne(Long id) {
        log.debug("Request to get CompetenzeLng : {}", id);
        return competenzeLngRepository.findById(id)
            .map(competenzeLngMapper::toDto);
    }

    /**
     * Delete the competenzeLng by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CompetenzeLng : {}", id);
        competenzeLngRepository.deleteById(id);
    }
}
