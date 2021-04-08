package it.laziocrea.jemoloapp.web.rest;

import it.laziocrea.jemoloapp.service.CompetenzeLngService;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;
import it.laziocrea.jemoloapp.service.dto.CompetenzeLngDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.CompetenzeLng}.
 */
@RestController
@RequestMapping("/api")
public class CompetenzeLngResource {

    private final Logger log = LoggerFactory.getLogger(CompetenzeLngResource.class);

    private static final String ENTITY_NAME = "competenzeLng";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompetenzeLngService competenzeLngService;

    public CompetenzeLngResource(CompetenzeLngService competenzeLngService) {
        this.competenzeLngService = competenzeLngService;
    }

    /**
     * {@code POST  /competenze-lngs} : Create a new competenzeLng.
     *
     * @param competenzeLngDTO the competenzeLngDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competenzeLngDTO, or with status {@code 400 (Bad Request)} if the competenzeLng has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/competenze-lngs")
    public ResponseEntity<CompetenzeLngDTO> createCompetenzeLng(@Valid @RequestBody CompetenzeLngDTO competenzeLngDTO) throws URISyntaxException {
        log.debug("REST request to save CompetenzeLng : {}", competenzeLngDTO);
        if (competenzeLngDTO.getId() != null) {
            throw new BadRequestAlertException("A new competenzeLng cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompetenzeLngDTO result = competenzeLngService.save(competenzeLngDTO);
        return ResponseEntity.created(new URI("/api/competenze-lngs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /competenze-lngs} : Updates an existing competenzeLng.
     *
     * @param competenzeLngDTO the competenzeLngDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competenzeLngDTO,
     * or with status {@code 400 (Bad Request)} if the competenzeLngDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competenzeLngDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/competenze-lngs")
    public ResponseEntity<CompetenzeLngDTO> updateCompetenzeLng(@Valid @RequestBody CompetenzeLngDTO competenzeLngDTO) throws URISyntaxException {
        log.debug("REST request to update CompetenzeLng : {}", competenzeLngDTO);
        if (competenzeLngDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompetenzeLngDTO result = competenzeLngService.save(competenzeLngDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competenzeLngDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /competenze-lngs} : get all the competenzeLngs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competenzeLngs in body.
     */
    @GetMapping("/competenze-lngs")
    public List<CompetenzeLngDTO> getAllCompetenzeLngs() {
        log.debug("REST request to get all CompetenzeLngs");
        return competenzeLngService.findAll();
    }

    /**
     * {@code GET  /competenze-lngs/:id} : get the "id" competenzeLng.
     *
     * @param id the id of the competenzeLngDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competenzeLngDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competenze-lngs/{id}")
    public ResponseEntity<CompetenzeLngDTO> getCompetenzeLng(@PathVariable Long id) {
        log.debug("REST request to get CompetenzeLng : {}", id);
        Optional<CompetenzeLngDTO> competenzeLngDTO = competenzeLngService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competenzeLngDTO);
    }

    /**
     * {@code DELETE  /competenze-lngs/:id} : delete the "id" competenzeLng.
     *
     * @param id the id of the competenzeLngDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competenze-lngs/{id}")
    public ResponseEntity<Void> deleteCompetenzeLng(@PathVariable Long id) {
        log.debug("REST request to delete CompetenzeLng : {}", id);
        competenzeLngService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
