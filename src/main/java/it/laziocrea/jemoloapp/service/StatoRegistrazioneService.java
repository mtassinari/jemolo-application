package it.laziocrea.jemoloapp.service;

import it.laziocrea.jemoloapp.domain.StatoRegistrazione;
import it.laziocrea.jemoloapp.repository.StatoRegistrazioneRepository;
import it.laziocrea.jemoloapp.service.dto.StatoRegistrazioneDTO;
import it.laziocrea.jemoloapp.service.mapper.StatoRegistrazioneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link StatoRegistrazione}.
 */
@Service
@Transactional
public class StatoRegistrazioneService {

    private final Logger log = LoggerFactory.getLogger(StatoRegistrazioneService.class);

    private final StatoRegistrazioneRepository statoRegistrazioneRepository;

    private final StatoRegistrazioneMapper statoRegistrazioneMapper;

    public StatoRegistrazioneService(StatoRegistrazioneRepository statoRegistrazioneRepository, StatoRegistrazioneMapper statoRegistrazioneMapper) {
        this.statoRegistrazioneRepository = statoRegistrazioneRepository;
        this.statoRegistrazioneMapper = statoRegistrazioneMapper;
    }

    /**
     * Save a statoRegistrazione.
     *
     * @param statoRegistrazioneDTO the entity to save.
     * @return the persisted entity.
     */
    public StatoRegistrazioneDTO save(StatoRegistrazioneDTO statoRegistrazioneDTO) {
        log.debug("Request to save StatoRegistrazione : {}", statoRegistrazioneDTO);
        StatoRegistrazione statoRegistrazione = statoRegistrazioneMapper.toEntity(statoRegistrazioneDTO);
        statoRegistrazione = statoRegistrazioneRepository.save(statoRegistrazione);
        return statoRegistrazioneMapper.toDto(statoRegistrazione);
    }

    /**
     * Get all the statoRegistraziones.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StatoRegistrazioneDTO> findAll() {
        log.debug("Request to get all StatoRegistraziones");
        return statoRegistrazioneRepository.findAll().stream()
            .map(statoRegistrazioneMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one statoRegistrazione by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatoRegistrazioneDTO> findOne(Long id) {
        log.debug("Request to get StatoRegistrazione : {}", id);
        return statoRegistrazioneRepository.findById(id)
            .map(statoRegistrazioneMapper::toDto);
    }

    /**
     * Delete the statoRegistrazione by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StatoRegistrazione : {}", id);
        statoRegistrazioneRepository.deleteById(id);
    }
}
