package it.laziocrea.jemoloapp.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import it.laziocrea.jemoloapp.service.AllegatoService;
import it.laziocrea.jemoloapp.service.dto.AllegatoDTO;
import it.laziocrea.jemoloapp.web.rest.errors.BadRequestAlertException;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link it.laziocrea.jemolorooster.domain.Allegato}.
 */
@RestController
@RequestMapping("/api")
public class AllegatoResource {

    private final Logger log = LoggerFactory.getLogger(AllegatoResource.class);

    private static final String ENTITY_NAME = "allegato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AllegatoService allegatoService;

    public AllegatoResource(AllegatoService allegatoService) {
        this.allegatoService = allegatoService;
    }

    /**
     * {@code POST  /allegatoes} : Create a new allegato.
     *
     * @param allegatoDTO the allegatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new allegatoDTO, or with status {@code 400 (Bad Request)} if the allegato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/allegatoes")
    public ResponseEntity<AllegatoDTO> createAllegato(@Valid @RequestBody AllegatoDTO allegatoDTO) throws URISyntaxException {
        log.debug("REST request to save Allegato : {}", allegatoDTO);
        if (allegatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new allegato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AllegatoDTO result = allegatoService.save(allegatoDTO);
        return ResponseEntity.created(new URI("/api/allegatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /allegatoes} : Updates an existing allegato.
     *
     * @param allegatoDTO the allegatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated allegatoDTO,
     * or with status {@code 400 (Bad Request)} if the allegatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the allegatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/allegatoes")
    public ResponseEntity<AllegatoDTO> updateAllegato(@Valid @RequestBody AllegatoDTO allegatoDTO) throws URISyntaxException {
        log.debug("REST request to update Allegato : {}", allegatoDTO);
        if (allegatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AllegatoDTO result = allegatoService.save(allegatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, allegatoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /allegatoes} : get all the allegatoes.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of allegatoes in body.
     */
    @GetMapping("/allegatoes")
    public List<AllegatoDTO> getAllAllegatoes(@RequestParam(required = false) String filter) {
        if ("curriculum-is-null".equals(filter)) {
            log.debug("REST request to get all Allegatos where curriculum is null");
            return allegatoService.findAllWhereCurriculumIsNull();
        }
        log.debug("REST request to get all Allegatoes");
        return allegatoService.findAll();
    }

    /**
     * {@code GET  /allegatoes/:id} : get the "id" allegato.
     *
     * @param id the id of the allegatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the allegatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/allegatoes/{id}")
    public ResponseEntity<AllegatoDTO> getAllegato(@PathVariable Long id) {
        log.debug("REST request to get Allegato : {}", id);
        Optional<AllegatoDTO> allegatoDTO = allegatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(allegatoDTO);
    }

    /**
     * {@code DELETE  /allegatoes/:id} : delete the "id" allegato.
     *
     * @param id the id of the allegatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/allegatoes/{id}")
    public ResponseEntity<Void> deleteAllegato(@PathVariable Long id) {
        log.debug("REST request to delete Allegato : {}", id);
        allegatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
