package it.laziocrea.jemoloapp.service.impl;

import it.laziocrea.jemoloapp.service.CurriculumService;
import it.laziocrea.jemoloapp.domain.Curriculum;
import it.laziocrea.jemoloapp.repository.CurriculumRepository;
import it.laziocrea.jemoloapp.service.dto.CurriculumDTO;
import it.laziocrea.jemoloapp.service.mapper.CurriculumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Curriculum}.
 */
@Service
@Transactional
public class CurriculumServiceImpl implements CurriculumService {

    private final Logger log = LoggerFactory.getLogger(CurriculumServiceImpl.class);

    private final CurriculumRepository curriculumRepository;

    private final CurriculumMapper curriculumMapper;

    public CurriculumServiceImpl(CurriculumRepository curriculumRepository, CurriculumMapper curriculumMapper) {
        this.curriculumRepository = curriculumRepository;
        this.curriculumMapper = curriculumMapper;
    }
    
    /**
     * Save a curriculum.
     *
     * @param curriculumDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurriculumDTO save(CurriculumDTO curriculumDTO) {
        log.debug("Request to save Curriculum : {}", curriculumDTO);
        Curriculum curriculum = curriculumMapper.toEntity(curriculumDTO);
        curriculum = curriculumRepository.save(curriculum);
        return curriculumMapper.toDto(curriculum);
    }

    /**
     * Get all the curricula.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CurriculumDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Curricula");
        return curriculumRepository.findAll(pageable)
            .map(curriculumMapper::toDto);
    }


    /**
     * Get one curriculum by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurriculumDTO> findOne(Long id) {
        log.debug("Request to get Curriculum : {}", id);
        return curriculumRepository.findById(id)
            .map(curriculumMapper::toDto);
    }
    
    /**
     * Get one curriculum by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Curriculum> findOneById(Long id) {
        log.debug("Request to get Curriculum : {}", id);
        return curriculumRepository.findOneById(id);
    }

    /**
     * Delete the curriculum by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Curriculum : {}", id);
        curriculumRepository.deleteById(id);
    }
}
