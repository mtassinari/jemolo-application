package it.laziocrea.jemoloapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.laziocrea.jemoloapp.domain.Allegato;
import it.laziocrea.jemoloapp.repository.AllegatoRepository;
import it.laziocrea.jemoloapp.service.dto.AllegatoDTO;
import it.laziocrea.jemoloapp.service.mapper.AllegatoMapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Allegato}.
 */
@Service
@Transactional
public class AllegatoService {

    private final Logger log = LoggerFactory.getLogger(AllegatoService.class);

    private final AllegatoRepository allegatoRepository;

    private final AllegatoMapper allegatoMapper;

    public AllegatoService(AllegatoRepository allegatoRepository, AllegatoMapper allegatoMapper) {
        this.allegatoRepository = allegatoRepository;
        this.allegatoMapper = allegatoMapper;
    }

    /**
     * Save a allegato.
     *
     * @param allegatoDTO the entity to save.
     * @return the persisted entity.
     */
    public AllegatoDTO save(AllegatoDTO allegatoDTO) {
        log.debug("Request to save Allegato : {}", allegatoDTO);
        Allegato allegato = allegatoMapper.toEntity(allegatoDTO);
        allegato = allegatoRepository.save(allegato);
        return allegatoMapper.toDto(allegato);
    }

    /**
     * Get all the allegatoes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AllegatoDTO> findAll() {
        log.debug("Request to get all Allegatoes");
        return allegatoRepository.findAll().stream()
            .map(allegatoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
    *  Get all the allegatoes where Curriculum is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<AllegatoDTO> findAllWhereCurriculumIsNull() {
        log.debug("Request to get all allegatoes where Curriculum is null");
        return StreamSupport
            .stream(allegatoRepository.findAll().spliterator(), false)
            .filter(allegato -> allegato.getCurriculum() == null)
            .map(allegatoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one allegato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AllegatoDTO> findOne(Long id) {
        log.debug("Request to get Allegato : {}", id);
        return allegatoRepository.findById(id)
            .map(allegatoMapper::toDto);
    }

    /**
     * Delete the allegato by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Allegato : {}", id);
        allegatoRepository.deleteById(id);
    }
}
