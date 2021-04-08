package it.laziocrea.jemoloapp.service.impl;

import it.laziocrea.jemoloapp.service.AnagraficaService;
import it.laziocrea.jemoloapp.domain.Anagrafica;
import it.laziocrea.jemoloapp.domain.AnagraficaCandidato;
import it.laziocrea.jemoloapp.repository.AnagraficaCandidatoRepository;
import it.laziocrea.jemoloapp.service.dto.AnagraficaDTO;
import it.laziocrea.jemoloapp.service.mapper.AnagraficaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnagraficaCandidato}.
 */
@Service
@Transactional
public class AnagraficaServiceImpl implements AnagraficaService {

    private final Logger log = LoggerFactory.getLogger(AnagraficaServiceImpl.class);

    private final AnagraficaCandidatoRepository anagraficaCandidatoRepository;

    private final AnagraficaMapper anagraficaMapper;
    
    public AnagraficaServiceImpl(AnagraficaCandidatoRepository anagraficaCandidatoRepository, AnagraficaMapper anagraficaMapper) {
        this.anagraficaCandidatoRepository = anagraficaCandidatoRepository;
        this.anagraficaMapper = anagraficaMapper;
    }
    
    /**
     * Save a anagraficaCandidato.
     *
     * @param anagraficaCandidatoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnagraficaDTO save(AnagraficaDTO anagraficaDTO) {
        log.debug("Request to save AnagraficaCandidato : {}", anagraficaDTO);
        Anagrafica anagrafica = anagraficaMapper.toEntity(anagraficaDTO);
        anagrafica = anagraficaCandidatoRepository.save(anagrafica);
        return anagraficaMapper.toDto(anagrafica);
    }
    
}
