package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.domain.Curriculum;
import it.laziocrea.jemoloapp.security.SecurityUtils;
import it.laziocrea.jemoloapp.service.CurriculumService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.web.rest.errors.CurriculumNotFoundException;
import it.laziocrea.jemoloapp.service.dto.CurriculumDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.Curriculum}.
 */
@RestController
@RequestMapping("/api")
public class CurriculumOwnResource {

    private final Logger log = LoggerFactory.getLogger(CurriculumOwnResource.class);

    private static final String ENTITY_NAME = "curriculum";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurriculumService curriculumService;

    public CurriculumOwnResource(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    /**
     * {@code POST  /own/curricula} : Create a new curriculum.
     *
     * @param curriculumDTO the curriculumDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curriculumDTO, or with status {@code 400 (Bad Request)} if the curriculum has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/own/curricula")
    public ResponseEntity<CurriculumDTO> createCurriculum(@Valid @RequestBody CurriculumDTO curriculumDTO) throws URISyntaxException {
        log.debug("REST request to save Curriculum : {}", curriculumDTO);
        if (curriculumDTO.getId() != null) {
            throw new BadRequestAlertException("A new curriculum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurriculumDTO result = curriculumService.save(curriculumDTO);
        return ResponseEntity.created(new URI("/api/curricula/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    /**
     * {@code PUT  /own/curricula} : Updates an existing curriculum.
     *
     * @param curriculumDTO the curriculumDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curriculumDTO,
     * or with status {@code 400 (Bad Request)} if the curriculumDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curriculumDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/own/curricula")
    public ResponseEntity<CurriculumDTO> updateCurriculum(@Valid @RequestBody CurriculumDTO curriculumDTO) throws URISyntaxException {
        log.debug("REST request to update Curriculum : {}", curriculumDTO);
        if (curriculumDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurriculumDTO result = curriculumService.save(curriculumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curriculumDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /own/curricula} : get all the curricula.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curricula in body.
     */
    @GetMapping("/own/curricula")
    public ResponseEntity<List<CurriculumDTO>> getAllCurricula(Pageable pageable) {
        log.debug("REST request to get a page of Curricula");
        Page<CurriculumDTO> page = curriculumService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /own/curricula/:id} : get the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curriculumDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/own/curricula/{id}")
    public ResponseEntity<CurriculumDTO> getCurriculum(@PathVariable Long id) {
        log.debug("REST request to get Curriculum : {}", id);
        Optional<CurriculumDTO> curriculumDTO = curriculumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(curriculumDTO);
    }
    
    /**
     * {@code GET  /own/curricula/{id}/$allegato} : get the "id" allegato.
     * 
     * @param id l'id dell'allegato da restituire
     * @return l'{@link ResponseEntity} con staus {@code 200 (OK)} con l'allegato nel body, o status {@code 404 (Not Found)}.
     */
    @GetMapping("/own/curricula/{id}/$content")
    @Timed
    public ResponseEntity<byte[]> getCurriculumFile(@PathVariable Long id) {
    	log.debug("REST request to get CurriculumFile : {}", id);
       Curriculum curriculum = curriculumService.findOneById(id)
           .orElseThrow(CurriculumNotFoundException::new);
       Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
       
       if (userLogin.isPresent() &&
           userLogin.get().equals(curriculum.getAnagrafica().getCandidato().getLogin())) {

	       return ResponseEntity.ok()
	           .contentType(MediaType.parseMediaType(curriculum.getMimeType()))
	           .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + curriculum.getCv() + "\"")
	           .body(curriculum.retrieveAllegato());
       } else {
           return ResponseEntity.notFound().build();
           // throw new BadRequestAlertException2("Not authorized to view this enthity", ENTITY_NAME, "403");
       }
    }

    /**
     * {@code DELETE  /own/curricula/:id} : delete the "id" curriculum.
     *
     * @param id the id of the curriculumDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/own/curricula/{id}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long id) {
        log.debug("REST request to delete Curriculum : {}", id);
        curriculumService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
